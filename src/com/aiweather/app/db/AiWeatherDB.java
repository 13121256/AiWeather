package com.aiweather.app.db;

import java.util.ArrayList;
import java.util.List;

import com.aiweather.app.entities.City;
import com.aiweather.app.entities.County;
import com.aiweather.app.entities.Province;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AiWeatherDB {

	public static final String DB_NAME="ai_weather";//���ݿ���
	
	public static final int VERSION=1; //���ݿ�汾
	  
	private static AiWeatherDB aiWeatherDB;
	private SQLiteDatabase db;

	/**
	 * ���캯��
	 * @param context
	 */
	private AiWeatherDB(Context context) {
		AiWeatherOpenHelper aiOpenHelper=new AiWeatherOpenHelper(
				context, DB_NAME, null, VERSION);
		
		db=aiOpenHelper.getWritableDatabase();
	}
	
	/**
	 * ��ȡAiWeaherDBʵ��
	 */
	public synchronized static AiWeatherDB getInstance(Context context){
		
		if(aiWeatherDB == null){
			aiWeatherDB=new AiWeatherDB(context);
		}
		return aiWeatherDB;
	}
	
	/**
	 * ��Province��洢�����ݿ���
	 */
	public void saveProvince(Province province){
		if(province != null){
			ContentValues values=new ContentValues();
			values.put("provinceName", province.getProvinceName());
			values.put("provinceCode", province.getProvinceCode());
			db.insert(DB_NAME, null, values);
		}
	}
	
	/**
	 * �����ݿ��ж�ȡʡ����Ϣ
	 */
	public List<Province> getProvince(SQLiteDatabase db){
		
		List<Province> provinceList=new ArrayList<Province>();
		Cursor cursor=db.query("CREATE_PROVINCE", null, null, null, null, null, null);
		while(cursor!=null&&cursor.moveToFirst()){
		
			if(cursor.moveToNext()){
				Province province=new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("provinceName")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("provinceCode")));
				
				provinceList.add(province);
			}
			cursor.close();
			db.close();
		}	
		return provinceList;		
	}	
	
	/**
	 * ��City��洢�����ݿ���
	 */
	public void saveCity(City city){
		if(city != null){
			ContentValues values=new ContentValues();
			values.put("cityName", city.getCityName());
			values.put("cityCode", city.getCityCode());
			values.put("provinceId", city.getProvinceId());
			db.insert(DB_NAME, null, values);
		}
	}
	
	/**
	 * �����ݿ��ж�ȡcity��Ϣ
	 */
	public List<City> getCity(int provinceId){
		
		List<City> cityList=new ArrayList<City>();
		Cursor cursor=db.query("CREATE_CITY", null, "provinceId = ?", new String[]{String.valueOf(provinceId)}, null, null, null);
		while(cursor!=null&&cursor.moveToFirst()){
		
			if(cursor.moveToNext()){
				City city=new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("cityName")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("cityCode")));
				city.setProvinceId(cursor.getInt(cursor.getColumnIndex("provinceId")));
				cityList.add(city);
			}
			cursor.close();
			db.close();
		}	
		return cityList;		
	}	
	
	
	/**
	 * ��County��洢�����ݿ���
	 */
	public void saveCounty(County county){
		if(county != null){
			ContentValues values=new ContentValues();
			values.put("countyName", county.getCountyName());
			values.put("countyCode", county.getCountyCode());
			values.put("cityId", county.getCityId());		
			db.insert("DB_NAME", null, values);
		}		
	}
	
	/**
	 * �����ݿ��ж�ȡcity��Ϣ
	 */
	public List<County> getCounty(int cityId){
		
		List<County> countyList=new ArrayList<County>();
		Cursor cursor=db.query("CREATE_COUNTY", null, "cityId = ?", new String[]{String.valueOf(cityId)}, null, null, null);
		while(cursor!=null&&cursor.moveToFirst()){
		     
			if(cursor.moveToNext()){
				County county = new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("countyName")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("countyCode")));
				county.setCityId(cursor.getInt(cursor.getColumnIndex("cityId")));
				countyList.add(county);
			}
			cursor.close();
			db.close();
		}
		return countyList;
		
	}
	
}
