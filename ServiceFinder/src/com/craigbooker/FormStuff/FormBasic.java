package com.craigbooker.FormStuff;

import android.content.Context;
import android.content.res.Configuration;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class FormBasic {
	EditText _searchField;
	Button _searchButton;
	
	// Setup IDs for form elements
	public static int Form_Button = 1;
	public static int Form_Field = 2;
	
	public LinearLayout textEntryWithButton(Context context, String hint, String buttonText) {
		LinearLayout ll = new LinearLayout(context);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		ll.setLayoutParams(lp);
		
		_searchField = new EditText(context);
		_searchField.setRawInputType(Configuration.KEYBOARD_12KEY);
		lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f);
		_searchField.setLayoutParams(lp);

		_searchField.setHint(hint);
		_searchField.setId(Form_Field);
		
		_searchButton = new Button(context);
		_searchButton.setText(buttonText);
		_searchButton.setId(Form_Button);
		
		ll.addView(_searchField);
		ll.addView(_searchButton);
		
		return ll;
	}
	public EditText getField(){
		return _searchField;
		
	}
	public  Button getButton(){
		return _searchButton;
		
	}
}
