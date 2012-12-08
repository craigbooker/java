package com.craigbooker.java1projectwk3;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


import com.craigbooker.lib.MyLocation;
import com.craigbooker.lib.MyLocation.LocationResult;
import com.craigbooker.lib.WebStuff;



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


import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.craigbooker.yelp.Business;
import com.craigbooker.yelp.Yelp;
import com.craigbooker.yelp.YelpSearchResult;
import com.craigbooker.yelp.YelpV2API;

public class MainActivity extends Activity {

	Context _context;
	LinearLayout _appLayout;
	SearchForm _search;
	SearchResults _results;
	PlacesDisplay _places; // Was the stock display.
	FavDisplay _favorites;
	Boolean _connected = false;	
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
		
		// Add a search form
		_search = new SearchForm(_context, "Search", "Go");
		_appLayout.addView(_search);
		
		// Add search handler
		Button searchButton = _search.getButton();
		
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Log.i("CLICK HANDLER",_search.getField().getText().toString());
				getPlaces(_search.getField().getText().toString());
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
	
	 /**
	  * Setup the Yelp API OAuth credentials.
	  *
	  * OAuth credentials are available from the developer site, under Manage API access (version 2 API).
	  *
	  * @param consumerKey Consumer key
	  * @param consumerSecret Consumer secret
	  * @param token Token
	  * @param tokenSecret Token secret
	  */
	 public Yelp(String consumerKey, String consumerSecret, String token, String tokenSecret) {
	   this.service = new ServiceBuilder().provider(YelpV2API.class).apiKey(consumerKey).apiSecret(consumerSecret).build();
	   this.accessToken = new Token(token, tokenSecret);
	 }
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
	private void SearchForPlaces(String radius){
		
		Log.i("CLICK", radius);
		String baseURL = 
		
		URL finalURL;
		try{
			finalURL = new URL();
		} catch(MalformedURLException e){
			Log.e("BAD URL", "MALFORMED URL");
			finalURL = null;
		}
	}
	
	private class SearchPlacesRequest extends AsyncTask<URL, Void, String>{
		@Override
		protected String doInBackground(URL... urls){
			String response = "";
			for(URL url: urls){
				response = WebStuff.getURLStringResponse(url);
				response = yelp.search("burritos", 30.361471, -87.164326);
			}
			return response;
		}
		
		@Override
		protected void onPostExecute(String result){
			Log.i("URL RESPONSE", result);
			 
		}
	}
	private void findCurrentLocation() {
        myLocation.getLocation(this, locationResult);
    }

	public LocationResult locationResult = new LocationResult() {

        @Override
        public void gotLocation(Location location) {
            if (location != null) {
                String strloc  = location.getLatitude() + ","
                        + location.getLongitude();
                Log.i("MY LOCATION", strloc);
            }
        }
    };
}
