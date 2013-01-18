package com.craigbooker.autoservicefinder;

import com.craigbooker.lib.FileStuff;

import android.app.Fragment;
import android.content.Context;
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
		
		//ADD SEARCH HANDLER
		Button searchButton = (Button) getActivity().findViewById(R.id.searchButton);
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText field = (EditText) getActivity().findViewById(R.id.searchField);
				String term = field.getText().toString();
				field.setText(term);
				InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(field.getWindowToken(), 0);
				getSearch(term);
			}
		});
		
		//GO TO FAVORITES BUTTON
		Button favButton = (Button) getActivity().findViewById(R.id.favButton);
		favButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(_context, Favorites.class);
				startActivityForResult(i, REQUEST_CODE);
			}
		});
		
		// ADD FAVORITE BUTTON
		Button addFav = (Button) getActivity().findViewById(R.id.addFavsButton);
		addFav.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v){
				String currentCategory = ((TextView) getActivity().findViewById(R.id.searchField)).getText().toString();
				if(currentCategory != null){
					if(_favorites.length() > 0){
						_favorites = _favorites.concat("," +currentCategory);
					} else {
						_favorites = currentCategory;
					}
					FileStuff.storeStringFile(_context, "favorites", _favorites, true);
				}
			}
		});
		
		return view;
	};
}
