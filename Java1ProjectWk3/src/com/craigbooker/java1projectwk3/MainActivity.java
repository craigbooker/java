package com.craigbooker.java1projectwk3;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.os.AsyncTask;

import android.content.Context;
import android.location.Location;
import android.view.Gravity;


import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.craigbooker.lib.FileStuff;

import com.craigbooker.external.yelp.YelpV2API;
import com.craigbooker.external.yelp.v2.Business;
import com.craigbooker.external.yelp.v2.YelpSearchResult;
import com.craigbooker.external.yelp.Yelp;
import com.craigbooker.external.yelp.YelpInfo;

import com.craigbooker.lib.Convert;
import com.craigbooker.lib.MyLocation;
import com.craigbooker.lib.MyLocation.LocationResult;
import com.craigbooker.lib.WebStuff;



import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;



public class MainActivity extends Activity {

	Context _context;
	ScrollView _sv;
	LinearLayout _appLayout;
	LinearLayout.LayoutParams _lp;
	TextView _txtView;
	TextView _spinnerLabel;
	Spinner _main_spinner;
	SearchForm _search;
	String searchTerm = "auto";
	TextView _tcResult;
	SearchResults _results;
	PlacesDisplay _places; // Was the stock display.
	FavDisplay _favorites;
	Boolean _connected = false;	
	HashMap<String, String> _history; 
	JSONArray resultsArrayP;
	
	String selectedTroubleCode;
	String selectedSensor;
	String correspondingSolution;
	String[ ] pidArray;
	String[ ] sensorsArray;
	String[ ] solutionsArray;
	int selectedIndex;
	
	

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
	String category = "auto";
	YelpSearchResult places;
	String rawData = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_context = this;
		
		// Call function to find current location
		findCurrentLocation();
		
		// Retrieve any history stored
		_history = getStoredHistory();
		Log.i("HISTORY READ", _history.toString());
		
		// Read in resources data
		 pidArray = getResources().getStringArray(R.array.pid_string_array);
	     sensorsArray = getResources().getStringArray(R.array.sensor_string_array);
	     solutionsArray = getResources().getStringArray(R.array.solution_string_array); 
		
		
		
		// Setup main app layout
		_sv = new ScrollView(_context);
		_appLayout = new LinearLayout(_context);
		_appLayout.setOrientation(LinearLayout.VERTICAL);
		

		_lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		_appLayout.setLayoutParams(_lp);
		
		_txtView = new TextView(_context);
		_txtView.setText("AutoMetrx : Find Help");
		_txtView.setTextSize(20);
		
		
		
		// Detect Network Connection
		_connected = WebStuff.getConnectionStatus(_context);
		if(_connected == true){
			Log.i("NETWORK CONNECTION:", "WE ARE CONNECTED TO THE WEB.");
			Log.i("NETWORK CONNECTION:", WebStuff.getConnectionType(_context));
			
		       TextView tv = new TextView(this);
		        tv.setText("Translate trouble code to something useful.");
		        tv.setTextSize(14);
		        
		        _spinnerLabel = new TextView(this);
		        _spinnerLabel.setText("Select a trouble code.");
		        
		        _appLayout.addView(tv);
		        LinearLayout form = new LinearLayout(this);
		        form.setOrientation(LinearLayout.HORIZONTAL);
		        _lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		        form.setLayoutParams(_lp);
		        form.addView(_spinnerLabel);
		        _main_spinner = new Spinner(this);
		      
		        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.pid_string_array, android.R.layout.simple_spinner_item);
		        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		        
		        
		        _main_spinner.setAdapter(adapter);
		        _main_spinner.setOnItemSelectedListener (new OnItemSelectedListener() {
			        	@Override
			        	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			        		Log.i("I made it to:", " Start of onItemSelected.");
			        		int index = arg0.getSelectedItemPosition();
			        		selectedIndex = index;
			        		selectedTroubleCode = pidArray[selectedIndex];
			        		selectedSensor = sensorsArray[selectedIndex];
			        		correspondingSolution = solutionsArray[selectedIndex];
			        		
			    			if(selectedTroubleCode == null){
			    				Toast.makeText(getBaseContext(),
			                            "You need to select an item....null ",
			                            Toast.LENGTH_LONG).show();   				
			    				
			    			} else if (selectedTroubleCode == "00") {
			    				Toast.makeText(getBaseContext(),
			                            "You need to select an item..00 ",
			                            Toast.LENGTH_LONG).show();
			    				
			    			} else {
			     				_tcResult.setText("Trouble Code: " + selectedTroubleCode + "\r\n" + 
			    						"Sensor Type: " + selectedSensor + "\r\n" + 
			    						"Possible solution: " + correspondingSolution + "\r\n"
			    						);
	
			    	 		}
			            }
	
						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							Toast.makeText(getBaseContext(),
			                        "You need to select an item. ",
			                        Toast.LENGTH_LONG).show();
			            }
			
		        });
		 
		        form.addView(_main_spinner);
		        _appLayout.addView(form);
		        _tcResult = new TextView(_context);
		        _appLayout.addView(_tcResult);
		        
				// Add a search form
				_search = new SearchForm(_context, "Enter search radius", "Go");
				_appLayout.addView(_search);
				
		// Add search handler
		_search.getButton().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Log.i("CLICK HANDLER",_search.getField().getText().toString());
				String radius = _search.getField().getText().toString();
				
				if (searchTerm.length() == 0) {
					Toast toast = Toast.makeText(_context,  "Please enter something", Toast.LENGTH_SHORT);
					toast.show();
					
				}else{
					// Clear Out the results
					//_results.reset();
					getRadiusListing(searchTerm, radius, loc, baseURL);
				}
			}
		});
			
			
// ------------------  If No Internet connection found. ---------------------------------------------------  	
		}else{
			Log.i("NETWORK CONNECTION:", "NO INTERNET CONNECTION FOUND! LOOKING THROUGH HISTORY");
			File file = new File("historyCache");
			
			if(file == null){
				Log.i("SEARCHED FOR FILE:", " NONE FOUND.");
				TextView tv = new TextView(_context);
				tv.setText("\r\n" + "No Internet Connection" + "No History Found" + "\r\n");
				_appLayout.addView(tv);
				
			}else {
				TextView tv = new TextView(_context);
				tv.setText("\r\n" + "No Internet Connection" + "\r\n");
				_appLayout.addView(tv);
				Button histBtn = new Button(_context);
				histBtn.setText("Use Cached Data");
				histBtn.setId(10);
				_appLayout.addView(histBtn);
				
				histBtn.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View V) {
						// TODO Auto-generated method stub
						String histString = _history.get(searchHistoryObj);
					}
				});
				
			}
			
		}
		
		// Add places display
		//_places = new PlacesDisplay(_context);
		
		// Add Favorites display
		//_favorites = new FavDisplay(_context);
		
		//Add views to main layout
		//_appLayout.addView(_places);
		//_appLayout.addView(_favorites);
		
		
		setContentView(_appLayout);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	private void findCurrentLocation() {
        myLocation.getLocation(_context, locationResult);
    }

	public LocationResult locationResult = new LocationResult() {

        @Override
        public Location gotLocation(Location location) {
            if (location != null) {
                strloc  = location.getLatitude() + ","
                		+ location.getLongitude();
                        //+ location.getLongitude() + "," + location.getTime();
                loc = location;
                Log.i("MY LOCATION", strloc);
                //Log.e("Loc VAR", loc);
            }
            //return strloc;
            //return loc;
        
        return loc;
    };

	 
	private void getRadiusListing(String term, String radius, Location loc, String baseURL){
		Log.i("CLICK", radius);
		//int myInt;
		double radiusDouble = 0.0;
		
		//String text = editText123.getText().toString();
		try {
		   radiusDouble = Double.parseDouble(radius);
		   Log.i("",radiusDouble+" is a number");
		} catch (NumberFormatException e) {
		   Log.i("",radius+"is not a number");
		}
		
		double radiusInMeters = Convert.milesToMeters(radiusDouble);
		String finalRadius = Double.toString(radiusInMeters);
		String qs = "";
		try{
			qs = URLEncoder.encode(finalRadius, "UTF-8");
			Log.i("Try QS URL:", qs);
		} catch (Exception e){
			Log.e("BAD URL", "ENCODING PROBLEM");
			qs = "";
		}
		
		//String baseURL = "http://api.yelp.com/v2/search";
		String _strLoc = (loc.getLatitude() + "," + loc.getLongitude());
		Log.i("STRLOC VALUE:", _strLoc);
		String builtURL = (baseURL + "?term=" + term + "&ll=" + strloc + "&radius_filter=" + qs);
		Log.i("BUILT URL:", builtURL);

		
		URL finalURL;
		try{
			finalURL = new URL(builtURL);
			SearchRequest sr = new SearchRequest();
			sr.execute(finalURL);
		} catch (MalformedURLException e){
			Log.e("BAD URL", "MALFORMED URL");
			finalURL = null;
		}
	}
	@SuppressWarnings({ "unchecked" })
	private HashMap<String, String> getStoredHistory() {
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
	
	private void displayResults (Context context){
		
		Log.i("RAW DATA FOR RESULTS:", rawData);
		System.out.println("Your search found " + places.getTotal() + " results.");
		System.out.println("Yelp returned " + places.getBusinesses().size() + " businesses in this request.");
		System.out.println();
		
		for(Business biz : places.getBusinesses()) {
			System.out.println(biz.getName());
			for(String address : biz.getLocation().getAddress()) {					
				System.out.println("  " + address);
			}
			System.out.print("  " + biz.getLocation().getCity());
			System.out.println(biz.getUrl());
			System.out.println();
			
		}
	}
	
	private class SearchRequest extends AsyncTask<URL, Void, String>{	
		@Override
		protected String doInBackground(URL... urls){
			for(URL url: urls){
			// Execute a signed call to the Yelp service.  
			OAuthService service = new ServiceBuilder().provider(YelpV2API.class).apiKey(consumerKey).apiSecret(consumerSecret).build();
			Token accessToken = new Token(token, tokenSecret);
			OAuthRequest oaRequest = new OAuthRequest(Verb.GET, url.toString());
			//OAuthRequest oaRequest = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");
			//oaRequest.addQuerystringParameter("ll", lat + "," + lng);
			//oaRequest.addQuerystringParameter("category", category);
			service.signRequest(accessToken, oaRequest);
			//service.signRequest(accessToken, oaRequest);
			Response response = oaRequest.send();
			rawData = response.getBody();
			}
			Log.i("RAW DATA:", rawData);
			return rawData;

	}	
	protected void onPostExecute(String rawData){
		Log.i("URL RESPONSE", rawData);
		// Sample of how to turn that text into Java objects.  
		try {
			places = new Gson().fromJson(rawData, YelpSearchResult.class);
			
			/*
			JSONObject json = new JSONObject(rawData);
			Log.i("GOT to check point", "1");
			JSONObject results = json.getJSONObject("coordinate");
			Log.i("GOT to check point", "2");
			resultsArrayP = results.getJSONArray("coordinate");
			Log.i("GOT to check point", "3");
			int rArrayLength = resultsArrayP.length();
			Log.i("ARRAY LENGTH", Integer.toString(rArrayLength));
			if(resultsArrayP == null){
				Log.i("JSON OBJECT", "INVALID");
			*/	
			//int numResults = places.getBusinesses().size();
			if(places == null){
				Log.i("GSON GET OBJECT", "INVALID");
				Toast toast = Toast.makeText(_context, "GET GSON FAILED!", Toast.LENGTH_SHORT);
				toast.show();
			}else{
				
				/*
				System.out.println("Your search found " + places.getTotal() + " results.");
				System.out.println("Yelp returned " + places.getBusinesses().size() + " businesses in this request.");
				System.out.println();
				
				for(Business biz : places.getBusinesses()) {
					System.out.println(biz.getName());
					for(String address : biz.getLocation().getAddress()) {					
						System.out.println("  " + address);
					}
					System.out.print("  " + biz.getLocation().getCity());
					System.out.println(biz.getUrl());
					System.out.println();
					
				}
				*/
				Toast toast = Toast.makeText(_context, "GET GSON SUCCESS!", Toast.LENGTH_SHORT);
				toast.show();
				
				displayResults(_context);
				_history.put("historyCache", rawData);
				FileStuff.storeObjectFile(_context, "searchHistoryObj", _history, false);
			}
			
		} catch(Exception e) {
			System.out.println("Error, could not parse returned data!");
			System.out.println(rawData);			
		}
	}
}
}


