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

import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.craigbooker.lib.FileStuff;

import com.craigbooker.external.yelp.YelpV2API;
import com.craigbooker.external.yelp.v2.Business;
import com.craigbooker.external.yelp.v2.YelpSearchResult;
import com.craigbooker.external.yelp.Yelp;

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
	
	String selectedTroubleCode;
	String selectedSensor;
	String correspondingSolution;
	String[ ] pidArray;
	String[ ] sensorsArray;
	String[ ] solutionsArray;
	int selectedIndex;
	
	
	
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
		
		// Read in resources data
		 pidArray = getResources().getStringArray(R.array.pid_string_array);
	     sensorsArray = getResources().getStringArray(R.array.sensor_string_array);
	     solutionsArray = getResources().getStringArray(R.array.solution_string_array); 
		
		
		
		// Setup main app layout
		_sv = new ScrollView(_context);
		_appLayout = new LinearLayout(_context);
		_appLayout.setOrientation(LinearLayout.VERTICAL);
		
		_history = getHistory();
		Log.i("HISTORY READ", _history.toString());
		_lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		_appLayout.setLayoutParams(_lp);
		
		_txtView = new TextView(_context);
		_txtView.setText("AutoMetrx : Find Help");
		_txtView.setTextSize(20);
		_txtView.setGravity(Gravity.CENTER_HORIZONTAL);
		
		
		
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
		        		/*
		            	Toast.makeText(getBaseContext(),
		                        "You have selected item : " + sensorsArray[index],
		                        Toast.LENGTH_LONG).show();
		                        */
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
					getRadiusListing(searchTerm, radius, strloc);
				}
			}
		});
		
		// Sign Yelp URL
		Yelp yelp = new Yelp(consumerKey, consumerSecret, token, tokenSecret);
			
			
			
			
		// If No Internet connection found.	
		}else{
			Log.i("NETWORK CONNECTION:", "NO INTERNET CONNECTION FOUND! LOOKING THROUGH HISTORY");
			File file = new File("history");
			
			if(file == null){
				Log.i("SEARCHED FOR FILE:", " NONE FOUND.");
				TextView tv = new TextView(_context);
				tv.setText("\r\n" + "No Internet Connection" + "No History Found" + "\r\n");
				_appLayout.addView(tv);
				
			}else {
				TextView tv = new TextView(_context);
				tv.setText("\r\n" + "No Internet Connection" + "\r\n");
				_appLayout.addView(tv);
				
			}
			
		}
		

		
		
		// Add places display
		//_places = new PlacesDisplay(_context);
		
		// Add Favorites display
		_favorites = new FavDisplay(_context);
		
		//Add views to main layout
		//_appLayout.addView(_places);
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

	 
	 private class YelpQuery {
         public Location location;
         public String term;
         //public String categoryFilter;
	 }
	 
	private void getRadiusListing(String term, String radius, String strloc){
		Log.i("CLICK", radius);
		double radiusDouble = Double.parseDouble(radius);
		// Need to add function to convert miles to meters.
		double radiusInMeters = Convert.metersToMiles(radiusDouble);
		String baseURL = "http://api.yelp.com/v2/search";
		String builtURL = (baseURL + "?term=" + term + "&ll=" + strloc + "&radius_filter=" + radiusInMeters);
		Log.i("BUILT URL:", builtURL);
		String qs = "";

		try{
			qs = URLEncoder.encode(builtURL, "UTF-8");
			
		} catch (Exception e){
			Log.e("BAD URL", "ENCODING PROBLEM");
			//qs = "";
		}
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
			//Log.i("JSON DATA", json);
			JSONObject results = json.getJSONObject("query").getJSONObject("results").getJSONObject("row");
			if(results.getString("col1").compareTo("N/A")==0){
				Toast toast = Toast.makeText(_context, "Invalid SOMETHING", Toast.LENGTH_SHORT);
				toast.show();
			} else {
				Toast toast = Toast.makeText(_context, "Valid SOMETHING" + results.getString("symbol"), Toast.LENGTH_SHORT);
				toast.show();
				_history.put(results.getString("radius"), results.toString());
				FileStuff.storeObjectFile(_context,  "history", _history, false);
				FileStuff.storeStringFile(_context,  "temp",  results.toString(), true);
			}
		} catch (JSONException e){
			Log.e("JSON", "JSON OBJECT EXCEPTION");
		}
		 
	}
		}
	
	
	@SuppressWarnings("unchecked")
	private HashMap<String, String> getHistory() {
		Object stored = FileStuff.readObjectFile(_context, "history", false);
		
		HashMap<String, String> history;
		if(stored == null){
			Log.i("HISTORY", "NO HISTORY FILE FOUND");
			history = new HashMap<String, String>();
		} else {
			history = (HashMap<String, String>) stored;
		}
		return history;
	}
}
