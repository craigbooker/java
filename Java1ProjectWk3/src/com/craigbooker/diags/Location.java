package com.craigbooker.diags;

public interface Location {
	
	// Set the name of the location
	public boolean setName(String name);
	
	// Set the GPS long and latitude for the location
	public boolean setGPS(String longitude, String latitude);
	
	// Get the name of the location
	public String getName();
	
	// Get the GPS long and latitude for the location
	public String getGPS();
}
