package com.craigbooker.ecom;

public class Phone implements Product {
	String name;
	double price;
	
	public Phone(String name, double price){
		setName(name);
		setPrice(price);
		
	}
	@Override
	public boolean setName(String name) {
		this.name = name;
		return true;
	}

	@Override
	public boolean setPrice(double price) {
		this.price = price;
		return true;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getPrice() {
		// TODO Auto-generated method stub
		return this.price;
	}

}
