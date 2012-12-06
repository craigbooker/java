package com.craigbooker.diags;

public class MaintRepair implements Location {

	String name;
	String longitude;
	String latitude;
	String gpsCoords;
	
	public MaintRepair(String name, String longitude, String latitude){
		setName(name);
		setGPS(longitude, latitude);
		
	}	
	@Override
	public boolean setName(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setGPS(String longitude, String latitude) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getGPS() {
		// TODO Auto-generated method stub
		return null;
	}

}
