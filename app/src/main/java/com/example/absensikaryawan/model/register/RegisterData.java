package com.example.absensikaryawan.model.register;

import com.google.gson.annotations.SerializedName;

public class RegisterData {

	@SerializedName("nip")
	private String nip;

	@SerializedName("name")
	private String name;

	@SerializedName("username")
	private String username;

	public void setNip(String nip){
		this.nip = nip;
	}

	public String getNip(){
		return nip;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}
}