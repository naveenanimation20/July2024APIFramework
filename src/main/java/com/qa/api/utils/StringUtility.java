package com.qa.api.utils;

public class StringUtility {
	
	public static String getRandomEmailId() {
		return "apiautomation"+System.currentTimeMillis()+"@open.com";
	}
	
	///select * from user where email like '%apiautomation%'
	

}
