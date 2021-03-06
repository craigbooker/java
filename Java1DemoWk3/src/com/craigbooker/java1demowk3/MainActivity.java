package com.craigbooker.java1demowk3;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.craigbooker.lib.WebStuff;

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

public class MainActivity extends Activity {

	Context _context;
	LinearLayout _appLayout;
	SearchForm _search;
	StockDisplay _stock;
	FavDisplay _favorites;
	Boolean _connected = false;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		_context = this;
		_appLayout = new LinearLayout(this);
		
		_search = new SearchForm(_context, "Enter Text Symbol", "GO");
		
		// Add search handler
		Button searchButton = _search.getButton();
		
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Log.i("CLICK HANDLER",_search.getField().getText().toString());
				getQuote(_search.getField().getText().toString());
			}
		});
		
		// Detect Network Connection
		_connected = WebStuff.getConnectionStatus(_context);
		if(_connected){
			Log.i("NETWORK CONNECTION", WebStuff.getConnectionType(_context));
			
			
		}
		
		// Add stock display
		_stock = new StockDisplay(_context);
		
		// Add Favorites display
		_favorites = new FavDisplay(_context);
		
		//Add views to main layout
		_appLayout.addView(_search);
		_appLayout.addView(_stock);
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
	private void getQuote(String symbol){
		Log.i("CLICK", symbol);
		String baseURL = "http://query.yahooapis.com/public/yql";
		//String yql = "select * from csv where url='http://download.finance.yahoo.com/d/quotes.csv?s=" + symbol + "&f=sl1d1t1c1ohgv&e=.csv' and  col
		String yql = "http://query.yahooapis.com";
		String qs;
		try{
			qs = URLEncoder.encode(yql, "UTF-8");
			
		} catch (Exception e){
			Log.e("BAD URL", "ENCODING PROBLEM");
			qs = "";
		}
		URL finalURL;
		try{
			finalURL = new URL(baseURL + "?g=" + qs + "&format=json");
			QuoteRequest qr = new QuoteRequest();
			qr.execute(finalURL);
		} catch (MalformedURLException e){
			Log.e("BAD URL", "MALFORMED URL");
			finalURL = null;
		}
	}
	
	private class QuoteRequest extends AsyncTask<URL, Void, String>{
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
}
