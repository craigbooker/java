package com.craigbooker.external.yelp.v2;

import java.util.HashMap;

public enum YelpInfo {
	CONSUMER_KEY("8hYTZBUuiTxOwmTjQtFnTw"),
	CONSUMER_SECRET("2mTa_1uggZVU2aWoIWQ8VViSC6s"),
	TOKEN("BAr8f7RjszQjh3_4A8VrCI1TjDLw5uMt"),
	TOKEN_SECRET("mPgSRUOXlheC1c5Zr8I7-I3dVj0");
	
	private final String value;
	
	YelpInfo(String value){
		this.value = value;
	} 
	/*
	public static HashMap<K,V> getChange(double amount){
		
	}
	*/
}
