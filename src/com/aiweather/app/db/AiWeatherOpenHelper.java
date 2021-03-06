package com.aiweather.app.db;
/**
 * 数据库帮助类，建立省，市，县三张表
 * 用于存储相关数据
 * @author liao jingwei
 * 2015/5/3
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AiWeatherOpenHelper extends SQLiteOpenHelper {
    
	/**
	 * 建Province表语句
	 */
	public static final String CREATE_PROVINCE="create table Province(" +
			                              "id integer primary key autoincrement, " +
			                              "province_name text, " +
			                              "province_code text ) ";
	/**
	 * 建City表语句
	 */
	public static final String CREATE_CITY="create table City(" +
			                              "id integer primary key autoincrement, " +
			                              "city_name text, " +
			                              "city_code text, " +
			                              "province_id integer ) ";
	/**
	 * 建County表语句
	 */
	public static final String CREATE_COUNTY="create table County(" +
			                              "id integer primary key autoincrement, " +
			                              "county_name text, " +
			                              "county_code text " +
			                              "city_id ) ";
	
	public AiWeatherOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(CREATE_PROVINCE);   //创建Province表
		db.execSQL(CREATE_CITY);   //创建City表
		db.execSQL(CREATE_COUNTY);   //创建County表
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		
	}

}
