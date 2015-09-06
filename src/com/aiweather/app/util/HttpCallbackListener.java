package com.aiweather.app.util;
/**
 * 专用接口，被HttpUtil类调用
 * @author liao jingwei
 * 2015/5/4
 */
public interface HttpCallbackListener {
     void onFinish(String response);
     void onError(Exception e);
}
