package com.craigbooker.servicefinder;

import java.util.HashMap;

import com.craigbooker.lib.FileStuff;
import com.craigbooker.servicefinder.FormFragment;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity implements FormFragment.FormListener {

	Context _context;
	HashMap<String, String> _storedHistory;
	String _log;
	final static int REQUEST_CODE = 0;
	
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

	private void getSearch(String searchTerm){
		
	}
	
	// FORM FRAGMENT METHODS
	
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
		String currentSearchTerm = ((TextView) findViewById(R.id.searchTermField)).getText().toString();
			if(currentSearchTerm != null){
				if(_log.length() > 0){
					_log = _log.concat("," +currentSearchTerm);
				} else {
					_log = currentSearchTerm;
				}
				FileStuff.storeStringFile(_context, "storedHistory", _log, true);
			}
	};

}
