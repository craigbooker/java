package com.craigbooker.lib;

public interface placeInterface {

	
	// Set Business Name
	public boolean setBizName(String bizName);
	
	// Set Business Address
	public boolean setBizAddress(String bizAddress);
	
	
	// Set Business City
	public boolean setBizCity(String bizCity);
	
	// Set Business URL
	public boolean setBizURL(String bizURL);
	
	
	// Get Business Name
	public String getBizName();
	
	// Get Business Address
	public String getBizAddress();
	
	// Get Business City
	public String getBizCity();
	
	// Get Business URL	
	public String getBizURL();
	
}
