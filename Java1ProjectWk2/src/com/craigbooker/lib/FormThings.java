package com.craigbooker.lib;

import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class FormThings {

	
	public static LinearLayout singleEntryWithButton(Context context, String hint, String buttonText){
	LinearLayout ll = new LinearLayout(context);
	LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	ll.setLayoutParams(lp);
	
	EditText et = new EditText(context);
	et.setHint(hint);
	
	Button b = new Button(context);
	b.setText(buttonText);
	
	ll.addView(et);
	ll.addView(b);
	
	return ll;
	}
	
}
