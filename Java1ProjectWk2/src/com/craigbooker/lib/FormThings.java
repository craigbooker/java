package com.craigbooker.lib;


import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class FormThings {
	int selectedIndex;
	
	public static LinearLayout singleEntryWithButton(Context context, String hint, String buttonText){
	LinearLayout ll = new LinearLayout(context);
	LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	ll.setLayoutParams(lp);
	
	EditText et = new EditText(context);
	lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f);
	et.setHint(hint);
	et.setLayoutParams(lp);
	et.setId(1);
	
	Button b = new Button(context);
	b.setText(buttonText);
	b.setId(2);
	b.setTag(et);
	
	ll.addView(et);
	ll.addView(b);
	
	return ll;
	}
	
	public static LinearLayout singleSpinnerWithLabel(Context context, String labelText) {
    	Log.i("I made it to: ", "singleSpinnerWithLabel start");
        LinearLayout spinnerLl = new LinearLayout(context);
        spinnerLl.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        spinnerLl.setLayoutParams(lp);
    	Log.i("I made it to: ", "singleSpinnerWithLabel Point 1");      
    	TextView spinnerLabel = new TextView(context);
    	spinnerLabel.setText(labelText);
        spinnerLl.addView(spinnerLabel);
    	Log.i("I made it to: ", "singleSpinnerWithLabel Point 2");           
        Spinner main_spinner = new Spinner(context);
        Log.i("I made it to: ", "singleSpinnerWithLabel Point 2.1"); 
        spinnerLl.addView(spinnerLabel);
        Log.i("I made it to: ", "singleSpinnerWithLabel just past adding the spinner label to the view."); 
        spinnerLl.addView(main_spinner);
     
    	Log.i("I made it to: ", "singleSpinnerWithLabel Point 3");        
        return spinnerLl;
	}
	
}
