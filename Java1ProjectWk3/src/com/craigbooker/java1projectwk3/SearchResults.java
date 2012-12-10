package com.craigbooker.java1projectwk3;

import java.util.ArrayList;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;


public class SearchResults extends LinearLayout {

	Context _context;
	ListView _listView;
	ArrayList<String> _results = new ArrayList<String>();
	
	public SearchResults(Context context) {
		super(context);
		
		LayoutParams lp;
		lp = new LayoutParams();
		_listView = new ListView(_context);
		_listView.setLayoutParams(lp);
		
		ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(_context, android.R.layout.simple_list_item_1, _results);
		listAdapter.setDropDownViewResource(android.R.layout.simple_list_item1);
		_listView.setAdapter(listAdapter);
	}
	
	public ListView getResultList() {
		return _listView;
	}
	
	public void addRow(String ){
		_results.add(object);
		_listView.invalidateViews();
	}
	
	public void reset() {
		_results.clear();
		_listView.invalidateViews();
		
	}
}
