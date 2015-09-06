package com.aiweather.app.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.http.HttpConnection;

/**
 * 从服务器端读取数据
 * @author liao jingwei
 * 2015/5/4
 */
public class HttpUtil {
     
	/**
	 * 从服务器读取数据
	 * @param address
	 * @param listener
	 */
	public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				HttpURLConnection connection=null;
				try {
					URL url=new URL(address);
					connection=(HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(10000);
					
					InputStream is=connection.getInputStream();
					BufferedReader br=new BufferedReader(new InputStreamReader(is));
					
					StringBuffer response=new StringBuffer();
					String line;
					while((line=br.readLine())!=null){
						response.append(line);
					}
					
					if(listener != null){
						//回调onFinish()方法
						listener.onFinish(response.toString());
					}
					
				} catch (Exception e) {
					if(listener != null){
						//回调onError()方法
						listener.onError(e);
					}					
				}finally{
					if(connection != null){
						
						connection.disconnect();
					}
				}				
			}
		}).start();
	}
}
