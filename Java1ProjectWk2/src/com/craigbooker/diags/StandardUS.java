package com.craigbooker.diags;

public class StandardUS implements Sensor {
	
	String name;
	String code;

	public StandardUS(String name, String code){
		setName(name);
		setCode(code);
		
	}
	
	@Override
	public boolean setName(String name) {
		this.name = name;
		return true;
	}

	@Override
	public boolean setCode(String code) {
		this.code = code;
		return true;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return this.code;
	}

}
