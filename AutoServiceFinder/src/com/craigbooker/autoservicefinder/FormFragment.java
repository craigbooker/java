package com.craigbooker.autoservicefinder;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class FormFragment extends Fragment {

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.form, container, false);
		
		Button searchButton = (Button) findViewById(R.id.searchButton);
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText field = (EditText) findViewById(R.id.searchField);
				String radius = field.getText().toString();
				field.setText(radius);
				InputMethodManager imm = (InputMethodManager)getSystemSErvice(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(field.getWindowToken(), 0);
				doSearch(radius);
			}
		});
		
		Button favButton = (Button) findViewById(R.id.favButton);
		favButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(_context, Favorites.class);
				startActivityForResult(i, REQUEST_CODE);
			}
		});
		
		return view;
	}
}
