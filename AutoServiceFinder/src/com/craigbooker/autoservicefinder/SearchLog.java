package com.craigbooker.autoservicefinder;

import java.util.ArrayList;
import java.util.Arrays;

import com.craigbooker.lib.FileStuff;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class SearchLog extends Activity {

	Context _context;
	ArrayList<String> _searchTerms = new ArrayList<String>();
	String _selected;
	
	@Override
	public void onCreate(Bundle savedInstance){
		super.onCreate(savedInstance);
		setContextView(R.layout.searchloglist);
		
		_context = this;
		
		//GET TERMS FROM FILE
		String searchLogString = FileStuff.readStringFile(this, "searchLog", true);
		String[] searchLogArray = searchLogString.split(",");
		_searchTerms = new ArrayList<String>Arrays.asList(searchLogArray);
		
		//CREATE LISTVIEW
		ListView list = (ListView) findViewById(R.id.searchloglist);
		ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(_context, android.R.layout.simple_expandable_list_item_1, _searchTerms);
		listAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
		list.setAdapter(listAdapter);
		
		//LIST INTERACTION
		list.setOnItemClickListener(new AdapterView.onItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
				_selected = ((TextView) v).getText().toString();
				finish();
			};
		});
		
		// Cancel Button
		Button cancel = (Button) findViewById(R.id.cancelButton);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				_selected = null;
				finish();
			}	
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_searchhistory, menu);
		return true;
	}
	
	@Override
	public void finish(){
		Intent data = new Intent();
		data.putExtra("term", _selected);
		setResult(RESULT_OK, data);
		super.finish();	
	}
}
	
