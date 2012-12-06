package com.craigbooker.java1projectwk3;


import android.content.Context;
import android.widget.GridLayout;
import android.widget.TextView;

public class StockDisplay extends GridLayout {

	TextView _symbol;
	TextView _price;
	TextView _time;
	TextView _high;
	TextView _low;
	TextView _change;
	TextView _open;
	TextView _close;	
	TextView _volume;	
	Context _context;
	
	public StockDisplay(Context context) {
		super(context);
		_context = context;
		
		this.setColumnCount(2);
		
		TextView symbolLabel = new TextView(_context);
		symbolLabel.setText("Symbol:");
		_symbol = new TextView(_context);
		
		TextView priceLabel = new TextView(_context);
		priceLabel.setText("Price:");
		_price = new TextView(_context);
		
		TextView timeLabel = new TextView(_context);
		timeLabel.setText("Updated:");
		_time = new TextView(_context);
		
		TextView highLabel = new TextView(_context);
		highLabel.setText("High:");
		_high = new TextView(_context);
		
		TextView lowLabel = new TextView(_context);
		lowLabel.setText("Low:");
		_low = new TextView(_context);
		
		TextView changeLabel = new TextView(_context);
		changeLabel.setText("Change:");
		_change = new TextView(_context);
		
		TextView openLabel = new TextView(_context);
		openLabel.setText("Open:");
		_open = new TextView(_context);

		TextView closeLabel = new TextView(_context);
		closeLabel.setText("Close:");
		_close = new TextView(_context);

		TextView volumeLabel = new TextView(_context);
		volumeLabel.setText("Volume:");
		_volume = new TextView(_context);	
		
		this.addView(symbolLabel);
		this.addView(_symbol);
		this.addView(priceLabel);
		this.addView(_price);
		this.addView(timeLabel);
		this.addView(_time);	
		this.addView(highLabel);
		this.addView(_high);	
		this.addView(lowLabel);
		this.addView(_low);	
		this.addView(changeLabel);
		this.addView(_change);
		this.addView(openLabel);
		this.addView(_open);	
		this.addView(closeLabel);
		this.addView(_close);	
		this.addView(volumeLabel);
		this.addView(_volume);			
	}
	
}