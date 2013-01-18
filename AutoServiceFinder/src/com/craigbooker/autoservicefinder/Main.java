package com.craigbooker.autoservicefinder;

import java.net.URL;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.craigbooker.external.yelp.v2.YelpV2API;
import com.craigbooker.external.yelp.v2.YelpSearchResult;
import com.craigbooker.lib.FileStuff;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//OAuth and Scribe
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;


public class Main extends Activity implements FormFragment.FormListener {

	Context _context;
	HashMap<String, String> _history;
	String _searchLog;
	static final int REQUEST_CODE = 0;
	
	// OAuth Stuff
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
	String rawData = "";
	
	
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
	};


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formfrag);
		
		_context = this;
		_searchLog = FileStuff.readStringFile(this, "searchLog", true);
		_history = getHistory();
		Log.i("HISTORY READ", _history.toString());
		
		public void updateData(JSONObject data){
			try{
				((TextView) findViewById(R.id.data_searchTerm)).setText(data.getString("searchTerm"));
				((TextView) findViewById(R.id.data_numberResults)).setText(data.getString("numberResults"));
				((TextView) findViewById(R.id.data_searchRadius)).setText(data.getString("searchRadius"));
				((TextView) findViewById(R.id.data_longitude)).setText(data.getString("longitude"));
				((TextView) findViewById(R.id.data_latitude)).setText(data.getString("latitude"));
			} catch(JSONException e){
				Log.e("JSON ERROR", e.toString());
			}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	

	
	Button historyButton = (Button) findViewById(R.id.historyButton);
	historyButton.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent i = new Intent(_context, SearchHistory.class);
			startActivityForResult(i, REQUEST_CODE);
		}
	});
	

	}
	@SuppressWarnings("unchecked")
	private HashMap<String, String> getHistory(){
		Object stored = FileStuff.readObjectFile(_context, "storedHistory", false);
		
		HashMap<String, String> history;
		if(stored == null){
			Log.i("HISTORY", "NO HISTORY FILE FOUND");
			history = new HashMap<String, String>();
		} else {
			history = (HashMap<String, String>) stored;
		}
		return history;
	}
	
	private void getSearch(String searchTerm){
		Messenger messenger = new Messenger(searchHandler);
		Intent intent = new Intent(_context, GetYelpSearch.class);
		intent.putExtra("searchTerm", searchTerm);
		intent.putExtra("messenger", messenger);
		startService(intent);
	}

		
	@Override
	protected void onActivityResult(){
		if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
			if(data.hasExtra("searchTerm")){
				String sTerm = data.getExtras().getString("searchTerm");
				((EditText) findViewById(R.id.searchTermField)).setText(sTerm);
				getSearch(sTerm);
			}
		}
	}

	//FORM FRAGMENT METHODS
	@Override
	public void onSearch(String searchTerm) {
		getSearch(searchTerm);
	}

	@Override
	public void onSearchLogList() {
		Intent i = new Intent(_context, SearchLog.class);
		startActivityForResult(i, REQUEST_CODE);
	}

	@Override
	public void onAddToSearchLog() {
		String currentSearchTerm = ((TextView) findViewById(R.id.searchTermField)).getText().toString();
		if(currentSearchTerm != null){
			if(_searchLog.length() > 0){
				_searchLog = _searchLog.concat("," +currentSearchTerm);
			} else {
				_searchLog = currentSearchTerm;
			}
			FileStuff.storeStringFile(_context, "storedHistory", _searchLog, true);
		}
	};
	
}
