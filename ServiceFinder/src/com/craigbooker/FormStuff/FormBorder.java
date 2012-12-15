package com.craigbooker.FormStuff;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

	

public class FormBorder extends LinearLayout {
	Context _context;
	
		public FormBorder(Context context, int height){
			super(context);
			
			_context = context;
			LinearLayout _ll = new LinearLayout(context);
			LayoutParams _lp = new LayoutParams(LayoutParams.MATCH_PARENT, height);
			_ll.setBackgroundColor(Color.BLACK);
			_ll.setLayoutParams(_lp);
			
			this.addView(_ll);
			//return _ll;
		}
}