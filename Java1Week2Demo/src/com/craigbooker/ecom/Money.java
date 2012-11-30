package com.craigbooker.ecom;

import java.util.HashMap;

public enum Money {
	PENNY(1),
	NICKEL(5),
	DIME(10),
	QUARTER(25),
	DOLLAR(100);
	
	private final int value;

	Money(int value){
		this.value = value;	
	}
	public static HashMap<Money, Integer> getChange(double amount){
		HashMap<Money, Integer> change = new HashMap<Money, Integer>();
		Money[] coins =  {DOLLAR, QUARTER, DIME, NICKEL, PENNY};
		double left = amount * 100;
		for(int i=0; i<coins.length; i++) {
		Money coin = coins[i];
		int num = (int) Math.floor(left/coin.value);
		left = left%coin.value;	
		change.put(coin, num);
		}
		return change;
	}
}
