package com.craigbooker.java1week3demo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class Main extends Activity {

	Context _context;
	LinearLayout _appLayout;
	SearchForm _search;
	StockDisplay _stock;
	
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
				Log.i("CLICK HANDLER",_search.getField().getText().toString());
			}
		});
		
		// Add stock display
		_stock = new StockDisplay(_context);
		
		//Add views to main layout
		_appLayout.addView(_search);
		_appLayout.addView(_stock);
		
		_appLayout.setOrientation(LinearLayout.VERTICAL);
		
		setContentView(_appLayout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
