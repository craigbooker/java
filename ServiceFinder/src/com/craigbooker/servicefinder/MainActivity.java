package com.craigbooker.servicefinder;

import java.net.URL;
import java.util.HashMap;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.craigbooker.FormStuff.FormBorder;
import com.craigbooker.FormStuff.PlacesDisplay;
import com.craigbooker.external.yelp.v2.Business;
import com.craigbooker.external.yelp.v2.YelpV2API;
import com.craigbooker.external.yelp.v2.YelpSearchResult;
import com.craigbooker.lib.FileStuff;
import com.craigbooker.lib.MyLocation;
import com.craigbooker.lib.WebStuff;
import com.craigbooker.lib.MyLocation.LocationResult;
import com.craigbooker.servicefinder.FormFragment;
import com.google.gson.Gson;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class MainActivity extends Activity implements FormFragment.FormListener {

	Context _context;
	HashMap<String, String> _storedHistory;
	HashMap<String, String> _history;
	Boolean _connected = false;	
	String _log;
	static final int REQUEST_CODE = 0;
	
	String strloc = "";
	Location loc;
	MyLocation myLocation = new MyLocation();
	
	OAuthService service;
	Token accessToken;
	String consumerKey = "8hYTZBUuiTxOwmTjQtFnTw";
	String consumerSecret = "2mTa_1uggZVU2aWoIWQ8VViSC6s";
	String token = "BAr8f7RjszQjh3_4A8VrCI1TjDLw5uMt";
	String tokenSecret = "mPgSRUOXlheC1c5Zr8I7-I3dVj0";
	String baseURL = "http://api.yelp.com/v2/search"; 
	
	// Some example values to pass into the Yelp search service.
	String lat = "35.667196";
	String lng = "-97.407243";
	String searchTerm = "auto";
	String category = "auto";
	YelpSearchResult places;
	int totalNumPlaces;
	String rawData = "";
	
	
	
	private Handler searchHandler = new Handler(){
		public void handleMessage(Message message){
			Object path = message.obj;
			if(message.arg1 == RESULT_OK && path != null){
				
			}
		}
		
	};
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form);
		
		// Call function to find current location and Retrieve any history stored
		findCurrentLocation();
		_history = getHistory();
		Log.i("HISTORY READ", _history.toString());
		_connected = WebStuff.getConnectionStatus(_context);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void getSearch(String searchTerm){
		
	}
	/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	FORM FRAGMENT METHODS
	
++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */		
	
	@Override
	public void onSearch(String searchTerm) {
		getSearch(searchTerm);
	}

	@Override
	public void onGoToSearchLog() {
		Intent i = new Intent(_context, SearchLog.class);
		startActivityForResult(i, REQUEST_CODE);
	}

	@Override
	public void onAddToLog() {
		String currentSearchTerm = ((TextView) findViewById(R.id.searchField)).getText().toString();
			if(currentSearchTerm != null){
				if(_log.length() > 0){
					_log = _log.concat("," +currentSearchTerm);
				} else {
					_log = currentSearchTerm;
				}
				FileStuff.storeStringFile(_context, "history", _log, true);
			}
	};

	
	/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	displayResults version 1.0
	
++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */		
	private void displayResults (Context context){
		
		String _dispBizName;
		String _dispBizAddress;
		String _dispBizCity;
		String _dispBizURL;
		
		// Log out raw results
		Log.i("RAW DATA INSIDE DISPLAY RESULTS:", rawData);
		
		// Log out some of the results formatted
		System.out.println("Your search found " + places.getTotal() + " results.");
		System.out.println("Yelp returned " + places.getBusinesses().size() + " businesses in this request.");
		System.out.println();
		
		// The biz loop
		for(Business biz : places.getBusinesses()) {
			
			LinearLayout resultLayoutLeft = new LinearLayout(_context);
			LayoutParams resultsLP1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			//LayoutParams resultsLP2 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			resultLayoutLeft.setLayoutParams(resultsLP1);
			
			
			PlacesDisplay myPlacesLL = new PlacesDisplay(_context);
			myPlacesLL.setLayoutParams(resultsLP1);
			
			TextView myBizName = (TextView) resultLayoutLeft.findViewById(1);
			TextView myBizAddress = (TextView) resultLayoutLeft.findViewById(2);
			TextView myBizCity = (TextView) resultLayoutLeft.findViewById(3);
			TextView myBizURL = (TextView) resultLayoutLeft.findViewById(4);
			
			
			
			System.out.println(biz.getName());
			_dispBizName = biz.getName().toString();
			System.out.println(_dispBizName);
			//Log.i("HERE WE GO:" _dispBizName);
			myBizName.setText(_dispBizName);
				for(String address : biz.getLocation().getAddress()) {	
					myBizAddress.setText(address);
					System.out.println("  " + address);
				}
			
				// Log out the city
			System.out.print("  " + biz.getLocation().getCity());
			
			// Set the city value
			myBizCity.setText("  " + biz.getLocation().getCity());
			
			// Log out the URL
			System.out.println(biz.getUrl());
			
			// Set the URL value
			myBizURL.setText(biz.getUrl());
			
			System.out.println();
			
			
			FormBorder fb1 = new FormBorder(_context, 10);
			fb1.setId(50);
			resultLayoutLeft.setId(51);
			
			resultLayoutLeft.addView(myPlacesLL);
			//ll.addView(resultLayoutLeft);
			Log.i("I am in the biz loop", "Loop IT");
		}
	}
	
	
		
	/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	findCurrentLocation

	++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */	
		private void findCurrentLocation() {
	        myLocation.getLocation(_context, locationResult);
	    }

/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	getStoredHistory
	
++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */	
	
	@SuppressWarnings({ "unchecked" })
	private HashMap<String, String> getHistory() {
		Object stored = FileStuff.readObjectFile(_context, "searchHistoryObj", false);
		
		HashMap<String, String> myStoredHistory;
			if(stored == null){
				Log.i("HISTORY", "NO HISTORY FILE FOUND");
				myStoredHistory = new HashMap<String, String>();
			} else {
				myStoredHistory = (HashMap<String, String>) stored;
			}
		return myStoredHistory;
	}		

	
/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	SearchRequest
	
++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */	
	private class SearchRequest extends AsyncTask<URL, Void, String>{	
		@Override
		protected String doInBackground(URL... urls){
			for(URL url: urls){
				// Execute a signed call to the Yelp service.  
				OAuthService service = new ServiceBuilder().provider(YelpV2API.class).apiKey(consumerKey).apiSecret(consumerSecret).build();
				Token accessToken = new Token(token, tokenSecret);
				OAuthRequest oaRequest = new OAuthRequest(Verb.GET, url.toString());
				service.signRequest(accessToken, oaRequest);
				Response response = oaRequest.send();
				rawData = response.getBody();
			}
			//Log.i("RAW DATA - IN SEARCH REQUEST:", rawData);
			return rawData;
		}		
/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++
END OF 	SearchRequest
		
++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */	
		
/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++
onPostExecute
		
++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */	
	protected void onPostExecute(String rawData){
		Log.i("URL RESPONSE - IN POST EXECUTE", rawData);
		try {
			//YelpSearchResult places = new Gson().fromJson(rawData, YelpSearchResult.class);
			
				places = new Gson().fromJson(rawData, YelpSearchResult.class);
				totalNumPlaces = places.getTotal();
				Log.i("Total Number Places:", Integer.toString(totalNumPlaces));
				if(places == null){
						Log.i("GSON GET OBJECT", "INVALID");
						Toast toast = Toast.makeText(_context, "GET GSON FAILED!", Toast.LENGTH_SHORT);
						toast.show();
				}else{
				
						Toast toast = Toast.makeText(_context, "GET GSON SUCCESS!", Toast.LENGTH_SHORT);
						toast.show();
						
						displayResults(_context);
						_history.put("historyCache", rawData.toString());
						FileStuff.storeObjectFile(_context, "searchHistoryObj", _history, false);
				}
		}catch(Exception e){
			System.out.println("Error, could not parse returned data!");
			System.out.println(rawData);
		}
	}
}	
	
/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	END OF onPostExecute
	
++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */		
	
/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	LocationResult
	
++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */
	public LocationResult locationResult = new LocationResult() {

	        @Override
	        public Location gotLocation(Location location) {
	            if (location != null) {
	                strloc  = location.getLatitude() + "," + location.getLongitude();
	                loc = location;
	                Log.i("MY LOCATION", strloc);
	            }
		        return loc;
		    }
	};
	
/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	End of - LocationResult
	
++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */	
	
}

