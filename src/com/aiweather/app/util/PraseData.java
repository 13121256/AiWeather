package com.aiweather.app.util;

import android.text.TextUtils;
import com.aiweather.app.db.AiWeatherDB;
import com.aiweather.app.entities.City;
import com.aiweather.app.entities.County;
import com.aiweather.app.entities.Province;

/**
 * 解析数据工具类
 * @author liao jingwei
 * 2015/5/4
 */

public class PraseData {
     
	/**
	 * 解析并处理从服务器返回的省级政区数据
	 * @param aiWeatherDB
	 * @param response
	 * @return
	 */
	public synchronized static boolean handleProvincesResponse(AiWeatherDB aiWeatherDB, String response){
		//response中的数据格式为 --代号|省，代号|省......		
		if(!TextUtils.isEmpty(response)){
			String[] allProvince=response.split(",");//单一省份数组--代号|省
			
			if(allProvince != null && allProvince.length>0){
				for(String p : allProvince){
					Province province=new Province();
					String[] oneProvince=p.split("\\|");//省份组成部分数组--代号，省
					province.setProvinceCode(oneProvince[0]);
					province.setProvinceName(oneProvince[1]);
					//将解析的数据存入Province表中
					aiWeatherDB.saveProvince(province);
				}
			return true;
			}
		}		
		return false;		
	}
	
	/**
	 * 解析并处理从服务器返回的市级政区数据
	 * @param aiWeatherDB
	 * @param response
	 * @return
	 */
	public synchronized static boolean handleCitiesResponse(AiWeatherDB aiWeatherDB, String response){
				
		if(!TextUtils.isEmpty(response)){
			String[] allCity=response.split(",");
			
			if(allCity != null && allCity.length > 0){
				for(String c : allCity){
					City city=new City();
					String[] oneCity=c.split("|");
					city.setCityCode(oneCity[0]);
					city.setCityName(oneCity[1]);
					city.setProvinceId(Integer.parseInt(oneCity[2]));
					aiWeatherDB.saveCity(city);
				}
				return true;
			}
		}
		return false;		
	}
	
	/**
	 * 解析并处理从服务器返回的县级政区数据
	 * @param aiWeatherDB
	 * @param response
	 * @return
	 */
	public synchronized static boolean handleCountiesResponse(AiWeatherDB aiWeatherDB, String response){
				
		if(!TextUtils.isEmpty(response)){
			String[] allCounty=response.split(",");
			
			if(allCounty != null && allCounty.length > 0){
				for(String c : allCounty){
					County county=new County();
					String[] oneCounty=c.split("|");
					county.setCountyCode(oneCounty[0]);
					county.setCountyName(oneCounty[1]);
					county.setCityId(Integer.parseInt(oneCounty[2]));
					aiWeatherDB.saveCounty(county);
				}
				return true;
			}
		}
		return false;		
	}
}
