package com.example.absensikaryawan.model.absen;

import com.google.gson.annotations.SerializedName;

public class Absen{

	@SerializedName("absenData")
	private AbsenData absenData;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public void setAbsenData(AbsenData absenData){
		this.absenData = absenData;
	}

	public AbsenData getAbsenData(){
		return absenData;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}