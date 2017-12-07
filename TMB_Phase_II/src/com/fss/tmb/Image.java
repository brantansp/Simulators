package com.fss.tmb;

import java.io.Serializable;

public class Image implements Serializable{

	
	int image_id;
	String image_Name;
	public Image(int image_id , String image_Name) {
		// TODO Auto-generated constructor stub
		this.image_id = image_id;
		this.image_Name = image_Name;
	}
	public int getImage_id() {
		return this.image_id;
	}
	public String getImage_Name() {
		return this.image_Name;
	}
}
