package com.craigbooker.FormStuff;

import com.craigbooker.lib.placeInterface;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlacesDisplay extends LinearLayout implements placeInterface {

	TextView _bizName;
	TextView _bizAddress;
	TextView _bizCity;
	TextView _bizURL;
	LayoutParams lp;
	
	String pBizName = "Apple Inc";
	String pBizAddress = "1 Infinite Loop";
	String pBizCity = "Cupertino, California";
	String pBizURL = "www.apple.com";
		
	Context _context;
	
	public PlacesDisplay(Context context) {
		super(context);
		
		this.setOrientation(LinearLayout.VERTICAL);
		lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		this.setLayoutParams(lp);
		_context = context;
		
		_bizName = new TextView(_context);
		_bizName.setId(30);
		_bizName.setText("Business:" + pBizName);
		

		_bizAddress = new TextView(_context);
		_bizAddress.setId(31);
		_bizAddress.setText("Address:" + pBizAddress);		

		_bizCity = new TextView(_context);
		_bizCity.setId(32);
		_bizCity.setText("Address:" + pBizCity);
		
		_bizURL = new TextView(_context);
		_bizURL.setId(33);
		_bizURL.setText("URL:" + pBizURL);		
		
		this.addView(_bizName);
		this.addView(_bizAddress);
		this.addView(_bizCity);
		this.addView(_bizURL);	
	}

	@Override
	public boolean setBizName(String bizName) {
		this.pBizName = bizName;
		return true;
	}

	@Override
	public boolean setBizAddress(String bizAddress) {
		this.pBizAddress = bizAddress;
		return true;
	}

	@Override
	public boolean setBizCity(String bizCity) {
		this.pBizCity = bizCity;
		return false;
	}

	@Override
	public boolean setBizURL(String bizURL) {
		this.pBizURL = bizURL;
		return true;
	}

	@Override
	public String getBizName() {
		return pBizName;
	}

	@Override
	public String getBizAddress() {
		return pBizAddress;
	}

	@Override
	public String getBizCity() {
		return pBizCity;
	}

	@Override
	public String getBizURL() {
		return pBizURL;
	}
	
}