package com.example.absensikaryawan.model.absen;

import com.google.gson.annotations.SerializedName;

public class AbsenData {

	@SerializedName("keterangan")
	private String keterangan;

	@SerializedName("nip")
	private String nip;

	@SerializedName("name")
	private String name;

	@SerializedName("waktu")
	private String waktu;

	@SerializedName("tanggal")
	private String tanggal;

	@SerializedName("alamat_ip")
	private String alamat_ip;

	public void setKeterangan(String keterangan){
		this.keterangan = keterangan;
	}

	public String getKeterangan(){
		return keterangan;
	}

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

	public void setWaktu(String waktu){
		this.waktu = waktu;
	}

	public String getWaktu(){
		return waktu;
	}

	public void setTanggal(String tanggal){
		this.tanggal = tanggal;
	}

	public String getTanggal(){
		return tanggal;
	}

	public void setAlamat_ip(String alamat_ip){
		this.alamat_ip = alamat_ip;
	}

	public String getAlamat_ip(){
		return alamat_ip;
	}
}