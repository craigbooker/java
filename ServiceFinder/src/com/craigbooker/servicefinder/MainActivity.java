package com.craigbooker.servicefinder;

import java.util.HashMap;

import com.craigbooker.servicefinder.FormFragment;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;

public class MainActivity extends Activity implements FormFragment.FormListener {

	Context _context;
	HashMap<String, String> _storedHistory;
	String _searchLog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formfrag);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onSearch(String searchTerm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSearchLog() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAddToLog() {
		// TODO Auto-generated method stub
		
	}

}
