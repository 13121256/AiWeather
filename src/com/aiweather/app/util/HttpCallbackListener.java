package com.aiweather.app.util;
/**
 * ר�ýӿڣ���HttpUtil�����
 * @author liao jingwei
 * 2015/5/4
 */
public interface HttpCallbackListener {
     void onFinish(String response);
     void onError(Exception e);
}
