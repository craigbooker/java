package com.craigbooker.java1demowk3;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class FavDisplay extends LinearLayout {

	Button _add;
	Button _remove;
	Spinner _list;
	Context _context;
	ArrayList<String> _stocks = new ArrayList<String>();
	
	public FavDisplay(Context context){
		super(context);
		_context = context;
		
		LayoutParams lp;
		
		_stocks.add("Select Favorite");
		_list = new Spinner(context);
		lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f);
		_list.setLayoutParams(lp);
		ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(_context, android.R.layout.simple_spinner_item, _stocks);
		listAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		_list.setAdapter(listAdapter);
		_list.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
        	public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
				Log.i("FAVORITE SELECTED", parent.getItemAtPosition(pos).toString());
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
				Log.i("FAVORITE SELECTED", "NONE");
            }
			
		});
		updateFavorites();
		
		_add = new Button(_context);
		_add.setText("+");
		_remove = new Button(_context);
		_remove.setText("-");
		
		this.addView(_list);
		this.addView(_add);
		this.addView(_remove);
		
		lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		this.setLayoutParams(lp);
	}
	
	private void updateFavorites(){
		_stocks.add("AAPL");
		_stocks.add("GOOG");
		_stocks.add("MSFT");
		
		
	}
}
	
