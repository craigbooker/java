package com.craigbooker.j2project1;

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
		_context = context;
		
		LayoutParams lp;
		lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f);
		_listView = new ListView(_context);
		_listView.setLayoutParams(lp);
		
		ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(_context, android.R.layout.simple_list_item_1, _results);
		listAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
		_listView.setAdapter(listAdapter);
		
		this.addView(_listView);
	}
	
	public ListView getResultList() {
		return _listView;
	}
	
	public void addRow(String txt){
		_results.add(txt);
		_listView.invalidateViews();
	}
	
	public void reset() {
		_results.clear();
		_listView.invalidateViews();
		
	}
}
