package com.aiweather.app.entities;

public class City {
    
	private int id;
	private String cityNamee;
	private String cityCode;
	private int provinceId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCityNamee() {
		return cityNamee;
	}
	public void setCityNamee(String cityNamee) {
		this.cityNamee = cityNamee;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public int getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
	
	
}
