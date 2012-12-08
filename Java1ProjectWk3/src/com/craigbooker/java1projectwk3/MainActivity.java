package com.craigbooker.java1projectwk3;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


import com.craigbooker.lib.MyLocation;
import com.craigbooker.lib.MyLocation.LocationResult;
import com.craigbooker.lib.WebStuff;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import com.craigbooker.yelp.YelpV2API;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.craigbooker.yelp.Business;
import com.craigbooker.yelp.YelpSearchResult;

public class MainActivity extends Activity {

	Context _context;
	LinearLayout _appLayout;
	SearchForm _search;
	PlacesDisplay _places; // Was the stock display.
	FavDisplay _favorites;
	Boolean _connected = false;	
	MyLocation myLocation = new MyLocation();
	
	// Define your keys, tokens and secrets.  These are available from the Yelp website.  
	String CONSUMER_KEY = "8hYTZBUuiTxOwmTjQtFnTw";
	String CONSUMER_SECRET = "2mTa_1uggZVU2aWoIWQ8VViSC6s";
	String TOKEN = "BAr8f7RjszQjh3_4A8VrCI1TjDLw5uMt";
	String TOKEN_SECRET = "mPgSRUOXlheC1c5Zr8I7-I3dVj0";
	
	// Some example values to pass into the Yelp search service.
	String lat = "35.667196";
	String lng = "-97.407243";
	String category = "auto";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		findCurrentLocation();
		_context = this;
		_appLayout = new LinearLayout(this);
		
		_search = new SearchForm(_context, "Enter Radius", "GO");
		
		
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
		
		// Add places display
		_places = new PlacesDisplay(_context);
		
		// Add Favorites display
		_favorites = new FavDisplay(_context);
		
		//Add views to main layout
		_appLayout.addView(_search);
		_appLayout.addView(_places);
		_appLayout.addView(_favorites);
		
		_appLayout.setOrientation(LinearLayout.VERTICAL);
		
		setContentView(_appLayout);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	private void getPlaces(String radius){
		
		Log.i("CLICK", radius);
		
		// Execute a signed call to the Yelp service.  
		OAuthService service = new ServiceBuilder().provider(YelpV2API.class).apiKey(CONSUMER_KEY).apiSecret(CONSUMER_SECRET).build();
		Token accessToken = new Token(TOKEN, TOKEN_SECRET);
		OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");
		request.addQuerystringParameter("ll", lat + "," + lng);
		request.addQuerystringParameter("category", category);
		service.signRequest(accessToken, request);
		Response response = request.send();
		String rawData = response.getBody();
		
		try {
			YelpSearchResult places = new Gson().fromJson(rawData, YelpSearchResult.class);
			
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
			
			
		} catch(Exception e) {
			System.out.println("Error, could not parse returned data!");
			System.out.println(rawData);			
		}
	}
	
	private class PlacesRequest extends AsyncTask<URL, Void, String>{
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
			 
		}
	}
	private void findCurrentLocation() {
        myLocation.getLocation(this, locationResult);
    }

	public LocationResult locationResult = new LocationResult() {

        @Override
        public void gotLocation(Location location) {
            // TODO Auto-generated method stub
            if (location != null) {
                String strloc  = location.getLatitude() + ","
                        + location.getLongitude();
                Log.i("MY LOCATION", strloc);
            }
        }
    };
}
