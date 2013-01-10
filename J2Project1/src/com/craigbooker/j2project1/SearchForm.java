package com.craigbooker.j2project1;

import android.content.Context;
import android.content.res.Configuration;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class SearchForm extends LinearLayout {
	
	EditText _searchField;
	Button _searchButton;
	
	
	public SearchForm(Context context, String hint, String buttonText) {
		super(context);
		
		LayoutParams lp;
		
		_searchField = new EditText(context);
		lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f);
		_searchField.setLayoutParams(lp);
		_searchField.setRawInputType(Configuration.KEYBOARD_12KEY);
		_searchField.setHint(hint);

		_searchButton = new Button(context);
		_searchButton.setText(buttonText);
		
		this.addView(_searchField);
		this.addView(_searchButton);
		
	}
	
	public EditText getField(){
		return _searchField;
		
	}
	
	public  Button getButton(){
		return _searchButton;
		
	}
}