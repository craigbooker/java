package com.craigbooker.diags;

public class MaintenanceRepair implements Location {
	String name;
	String longitude;
	String latitude;
	String gpsCoords;
	
	public MaintenanceRepair(String name, String longitude, String latitude){
		setName(name);
		setGPS(longitude, latitude);
		
	}	
	
	@Override
	public boolean setName(String name) {
		this.name = name;
		return true;
	}

	@Override
	public boolean setGPS(String longitude, String latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
		return true;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public String getGPS() {
		// TODO Auto-generated method stub
		return this.gpsCoords;
	}

}
