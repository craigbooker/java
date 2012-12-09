package com.eatrightapp.util;

public class Convert {
	public static final double METERS_IN_ONE_MILE = 0.000621371192;

	public static double metersToMiles(double distance) {			
		return distance * METERS_IN_ONE_MILE;
	}
	
	public static String yelpifiedIdString(String id) {
		StringBuffer sb = new StringBuffer();
		id = id.trim().toLowerCase();
		for(int i=0; i<id.length(); i++) {
			char c = id.charAt(i);
			if((c >= 'a' && c <='z') || (c >= '0' && c <= '9') || c == '-' || c == '_') {
				sb.append(c);
			} else if(c == ' ') {
				sb.append('-');
			}
		}
		return sb.toString();
	}
}
