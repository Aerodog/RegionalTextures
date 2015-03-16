package com.shiniofthegami.regionaltextures.base;


public class Pack {
	private String packURL;
	private String packName;
	
	public Pack(String packURL, String packName){
		this.packURL = packURL;
		this.packName = packName;
	}
	
	public String getURL(){
		return packURL;
	}
	
	public String getName(){
		return packName;
	}
	
	public String toString(){
		return "[" + this.getName() + ", " + this.getURL() + "]";
	}
	
}
