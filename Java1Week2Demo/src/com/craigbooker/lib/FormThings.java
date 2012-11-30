package com.craigbooker.lib;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class FormThings {

	public static LinearLayout singleEntryWithButton(Context context, String hint, String buttonText){
		LinearLayout ll = new LinearLayout(context);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
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
	public static RadioGroup getOptions(Context context, String[] options){
		RadioGroup boxes = new RadioGroup(context);
		
		for(int i=0; i<options.length; i++){
			RadioButton rb = new RadioButton(context);
			rb.setText(options[i]);
			rb.setId(i+1);
			boxes.addView(rb);
				
		}
		return boxes;
		
	}
} 
