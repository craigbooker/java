package com.craigbooker.FormStuff;

import com.craigbooker.FormStuff.FormBorder;

import android.content.Context;
import android.widget.GridLayout;
import android.widget.TextView;

public class PlacesGridDisplay extends GridLayout {
		TextView _bizName;
		TextView _bizAddress;	
		TextView _bizCity;	
		TextView _bizURL;
		
		Context _context;

		public PlacesGridDisplay(Context context){
			super(context);
		
			_context = context;
			
			this.setColumnCount(2);
			
			
			TextView placeBizName = new TextView(_context);
			placeBizName.setText("Business: ");
			
			_bizName = new TextView(_context);
			_bizName.setId(30);

			TextView placeBizAddress = new TextView(_context);
			placeBizAddress.setText("Address: ");
			
			_bizAddress = new TextView(_context);
			_bizAddress.setId(31);			
			
			
			TextView placeBizCity = new TextView(_context);
			placeBizCity.setText("City: ");
			
			_bizCity = new TextView(_context);
			_bizCity.setId(32);			
			
			// BizURL
			TextView placeBizURL = new TextView(_context);
			placeBizURL.setText("URL: ");
			_bizURL = new TextView(_context);
			_bizURL.setId(33);			
			
			this.addView(placeBizName);
			this.addView(_bizName);

			this.addView(placeBizAddress);
			this.addView(_bizAddress);	
			
			this.addView(placeBizCity);
			this.addView(_bizCity);

			this.addView(placeBizURL);
			this.addView(_bizURL);
			
			FormBorder fbPGD = new FormBorder(_context, 20);
			this.addView(fbPGD);
			
			
		}
}
