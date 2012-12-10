package com.craigbooker.java1projectwk3;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.craigbooker.lib.FileStuff;

import com.craigbooker.external.yelp.YelpV2API;
import com.craigbooker.external.yelp.v2.Business;
import com.craigbooker.external.yelp.v2.YelpSearchResult;
import com.craigbooker.external.yelp.Yelp;

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
	LinearLayout _appLayout;
	SearchForm _search;
	SearchResults _results;
	PlacesDisplay _places; // Was the stock display.
	FavDisplay _favorites;
	Boolean _connected = false;	
	HashMap<String, String> _history; 
	
	String strloc = "";
	MyLocation myLocation = new MyLocation();
	OAuthService service;
	Token accessToken;
	 String consumerKey = "8hYTZBUuiTxOwmTjQtFnTw";
	 String consumerSecret = "2mTa_1uggZVU2aWoIWQ8VViSC6s";
	 String token = "BAr8f7RjszQjh3_4A8VrCI1TjDLw5uMt";
	 String tokenSecret = "mPgSRUOXlheC1c5Zr8I7-I3dVj0";
	  
	
	// Some example values to pass into the Yelp search service.
	String lat = "35.667196";
	String lng = "-97.407243";
	String category = "auto";
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_context = this;
		
		// Call function to find current location
		findCurrentLocation();
		
		// Setup main app layout
		_appLayout = new LinearLayout(this);
		_appLayout.setOrientation(LinearLayout.VERTICAL);
		_history = new HashMap<String, String>();
		
		// Add a search form
		_search = new SearchForm(_context, "Enter search term", "Go");
		_appLayout.addView(_search);
		
		// Add search handler
		Button searchButton = _search.getButton();
		
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Log.i("CLICK HANDLER",_search.getField().getText().toString());
				//getPlaces(_search.getField().getText().toString());
				String searchTerm = _search.getField().getText().toString();
				if (searchTerm.length() == 0) {
					Toast toast = Toast.makeText(_context,  "Please enter something", Toast.LENGTH_SHORT);
					toast.show();
					
				}
			}
		});
		
		// Detect Network Connection
		_connected = WebStuff.getConnectionStatus(_context);
		if(_connected){
			Log.i("NETWORK CONNECTION", WebStuff.getConnectionType(_context));	
		}
		
		// Sign Yelp URL
		Yelp yelp = new Yelp(consumerKey, consumerSecret, token, tokenSecret);
		
		
		// Add places display
		_places = new PlacesDisplay(_context);
		
		// Add Favorites display
		_favorites = new FavDisplay(_context);
		
		//Add views to main layout
		_appLayout.addView(_places);
		_appLayout.addView(_favorites);
		
		
		setContentView(_appLayout);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	private void findCurrentLocation() {
        myLocation.getLocation(this, locationResult);
    }

	public LocationResult locationResult = new LocationResult() {

        @Override
        public String gotLocation(Location location) {
            if (location != null) {
                strloc  = location.getLatitude() + ","
                        + location.getLongitude();
                Log.i("MY LOCATION", strloc);
            }
            return strloc;
        }
    };

	 /**
	  * Search with term and location.
	  *
	  * @param term Search term
	  * @param latitude Latitude
	  * @param longitude Longitude
	  * @return JSON string response
	  */
	 public String search(String term, double latitude, double longitude) {
	   OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");
	   request.addQuerystringParameter("term", term);
	   request.addQuerystringParameter("ll", latitude + "," + longitude);
	   this.service.signRequest(this.accessToken, request);
	   Response response = request.send();
	   return response.getBody();
	 }
	 
	 private class YelpQuery {
         public Location location;
         public String term;
         public String categoryFilter;
	 }
	 
	private void getCategory(String term){
		Log.i("CLICK", term);
		String baseURL = "http://api.yelp.com/v2/search";
		String qs = "";

		try{
			qs = URLEncoder.encode(term, "UTF-8");
			
		} catch (Exception e){
			Log.e("BAD URL", "ENCODING PROBLEM");
			//qs = "";
		}
		URL finalURL;
		try{
			finalURL = new URL(baseURL + "?term" + qs + "&ll=" + strloc);
			SearchRequest sr = new SearchRequest();
			sr.execute(finalURL);
		} catch (MalformedURLException e){
			Log.e("BAD URL", "MALFORMED URL");
			finalURL = null;
		}
	}

	private class SearchRequest extends AsyncTask<URL, Void, String>{
		@Override
		protected String doInBackground(URL... urls){
			String response = "";
			for(URL url: urls){
				response = WebStuff.getURLStringResponse(url);
			}
			return response;
	}
			
	@Override
	protected void onPostExecute(String result){
		Log.i("URL RESPONSE", result);
		try{
			JSONObject json = new JSONObject(result);
			JSONObject results = json.getJSONObject("query").getJSONObject("results").getJSONObject("row");
			if(results.getString("col1").compareTo("N/A")==0){
				Toast toast = Toast.makeText(_context, "Invalid SOMETHING", Toast.LENGTH_SHORT);
				toast.show();
			} else {
				Toast toast = Toast.makeText(_context, "Valid SOMETHING" + results.getString("symbol"), Toast.LENGTH_SHORT);
				toast.show();
				_history.put(results.getString("symbol"), results.toString());
				FileStuff.storeObjectFile(_context,  "history", _history, false);
				FileStuff.storeStringFile(_context,  "temp",  results.toString(), true);
			}
		} catch (JSONException e){
			Log.e("JSON", "JSON OBJECT EXCEPTION");
		}
		// Need to show the results.	 
	}
		}

}
