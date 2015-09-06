package com.aiweather.app.activity;

import java.util.ArrayList;
import java.util.List;

import com.aiweather.app.R;
import com.aiweather.app.db.AiWeatherDB;
import com.aiweather.app.entities.City;
import com.aiweather.app.entities.County;
import com.aiweather.app.entities.Province;
import com.aiweather.app.util.HttpCallbackListener;
import com.aiweather.app.util.HttpUtil;
import com.aiweather.app.util.PraseData;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 主界面，选择省份
 * @author liao jingwei
 * 2015/5/4
 */
public class ChoiceAreaActivity extends Activity {
    /**
     * 设置不同行政级别对应的级别  
     */
	public static final int LEVEL_PROVINCE = 0;
	public static final int LEVEL_CITY = 1;
	public static final int LEVEL_COUNTY = 2;
	/**
	 * 省，市，县的存储容器
	 */
	private List<Province> provinceList;
	private List<City> cityList;
	private List<County> countyList;
	/**
	 * 选中的省份，城市
	 */
	private Province selectedProvince;
	private City selectedCity;
	/**
	 * 当前选择行政区的级别	
	 */
	private int currentLevel;
	
	private ProgressDialog progressDialog;
	private TextView title;
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private ArrayList<String> dataList = new ArrayList<String>();
	private AiWeatherDB aiWeatherDB;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choice_area);
		
		title = (TextView) findViewById(R.id.title);
		listView = (ListView) findViewById(R.id.list_view);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
		
		listView.setAdapter(adapter);
		aiWeatherDB=AiWeatherDB.getInstance(this);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if(currentLevel == LEVEL_PROVINCE){
					selectedProvince = provinceList.get(position);
					queryCities();
					
				}else if(currentLevel == LEVEL_CITY){
					selectedCity = cityList.get(position);
					queryCounties();
				}
			}
		});
		queryProvinces();
	}
	
	private void queryProvinces(){
		provinceList=aiWeatherDB.getProvince();
		if(provinceList.size() > 0){
			dataList.clear();
			for(Province province : provinceList){
				dataList.add(province.getProvinceName());
			}
			
		adapter.setNotifyOnChange(true);
		listView.setSelection(0);
		title.setText("中国");
		currentLevel = LEVEL_PROVINCE;
		}else{
			queryFromServer(null, "province");
		}
	}
	
	private void queryCities(){
		cityList = aiWeatherDB.getCity(selectedProvince.getId());
		if(cityList.size() > 0){
			dataList.clear();
			for(City city : cityList){
				dataList.add(city.getCityName());				
			}
		
			adapter.setNotifyOnChange(true);
			listView.setSelection(0);
			currentLevel = LEVEL_CITY;
			title.setText(selectedProvince.getProvinceName());		
		}else{
			queryFromServer(selectedProvince.getProvinceCode(), "city");
		}
	}
	
	private void queryCounties(){
		countyList = aiWeatherDB.getCounty(selectedCity.getId());
		if(countyList.size() > 0){
			dataList.clear();
			for(County county : countyList){
				dataList.add(county.getCountyName());
			}
		
		adapter.setNotifyOnChange(true);
		listView.setSelection(0);
		currentLevel = LEVEL_COUNTY;
		title.setText(selectedCity.getCityName().toString());
		}else{
			queryFromServer(selectedCity.getCityCode(), "county");
		}
	}
	
	private void queryFromServer(final String code, final String type){
		String address;
		if(!TextUtils.isEmpty(code)){
			address="http://www.weather.com.cn/data/list3/city"+ code +".xml";		
		}else{
			address="http://www.weather.com.cn/data/list3/city.xml";	
		}
		showProgressDialog();
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {

				boolean result = false;
				if("province".equals(type)){
					result = PraseData.handleCountiesResponse(aiWeatherDB, response);
				}else if("city".equals(type)){
					result = PraseData.handleCitiesResponse(aiWeatherDB, response);
				}else if("county".equals(type)){
					result = PraseData.handleCitiesResponse(aiWeatherDB, response);
				}
				if(result){					
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							closeProgressDialog();
							if("province".equals(type)){
                                queryProvinces();
							}else if("city".equals(type)){
                                queryCities();
							}else if("county".equals(type)){
                                queryCounties();
							}
						}
					});
				}
			}
			
			@Override
			public void onError(Exception e) {

				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {

						closeProgressDialog();
						Toast.makeText(ChoiceAreaActivity.this, "加载失败", 
								Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}
	
	private void showProgressDialog(){
		if(progressDialog == null){
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("正在加载......");
			progressDialog.setCanceledOnTouchOutside(false);
		}
	}
	
	private void closeProgressDialog(){
		if(progressDialog != null){
			progressDialog.dismiss();
		}
	}
	
	@Override
	public void onBackPressed() {

		if(currentLevel == LEVEL_COUNTY){
			queryCities();
		}else if(currentLevel == LEVEL_PROVINCE){
			queryProvinces();
		}else{
			finish();
		}
	}
}
