package com.craigbooker.lib;

public class placesClass implements placeInterface {

	String bizName;
	String bizAddress;
	String bizCity;
	String bizURL;
	
	public placesClass(String bizName, String bizAddress, String bizCity, String bizURL){
		setBizName(bizName);
		setBizAddress(bizAddress);
		setBizCity(bizCity);
		setBizURL(bizURL);
	}
	
	
	
	@Override
	public boolean setBizName(String bizName) {
		this.bizName = bizName;
		return false;
	}

	@Override
	public boolean setBizAddress(String bizAddress) {
		this.bizAddress = bizAddress;
		return false;
	}

	@Override
	public boolean setBizCity(String bizCity) {
		this.bizCity = bizCity;
		return false;
	}

	@Override
	public boolean setBizURL(String bizURL) {
		this.bizURL = bizURL;
		return false;
	}

	@Override
	public String getBizName() {
		return this.bizName;
	}

	@Override
	public String getBizAddress() {
		return this.bizAddress;
	}

	@Override
	public String getBizCity() {
		return this.bizCity;
	}

	@Override
	public String getBizURL() {
		return this.bizURL;
	}

}
