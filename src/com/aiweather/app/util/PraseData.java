package com.aiweather.app.util;

import android.text.TextUtils;
import com.aiweather.app.db.AiWeatherDB;
import com.aiweather.app.entities.City;
import com.aiweather.app.entities.County;
import com.aiweather.app.entities.Province;

/**
 * �������ݹ�����
 * @author liao jingwei
 * 2015/5/4
 */

public class PraseData {
     
	/**
	 * ����������ӷ��������ص�ʡ����������
	 * @param aiWeatherDB
	 * @param response
	 * @return
	 */
	public synchronized static boolean handleProvincesResponse(AiWeatherDB aiWeatherDB, String response){
		//response�е����ݸ�ʽΪ --����|ʡ������|ʡ......		
		if(!TextUtils.isEmpty(response)){
			String[] allProvince=response.split(",");//��һʡ������--����|ʡ
			
			if(allProvince != null && allProvince.length>0){
				for(String p : allProvince){
					Province province=new Province();
					String[] oneProvince=p.split("\\|");//ʡ����ɲ�������--���ţ�ʡ
					province.setProvinceCode(oneProvince[0]);
					province.setProvinceName(oneProvince[1]);
					//�����������ݴ���Province����
					aiWeatherDB.saveProvince(province);
				}
			return true;
			}
		}		
		return false;		
	}
	
	/**
	 * ����������ӷ��������ص��м���������
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
	 * ����������ӷ��������ص��ؼ���������
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
