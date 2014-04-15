package com.eoe.tampletfragment.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import android.util.Log;


/*
 * 注册----
 * 未实现注册查重
 * 
 *  * */

public class Register {

	public Register() {
		// TODO Auto-generated constructor stub
	}
	
	//通过params保存key-value对
	public static boolean save(Map<String,String> params) throws Exception{
//		String path="http://10.0.2.2/test/register";	
		String path="http://192.168.50.44/test/register";	

		Log.e("http", "6");

		return sendGetRequest(path,params,"UTF-8");
	}

	//发送get请求
	private static boolean sendGetRequest(String path,
			Map<String, String> params, String encoding) throws IOException {
		// TODO Auto-generated method stub
		StringBuilder sb=new StringBuilder(path);
		if(params!=null && !params.isEmpty()){
			Log.e("http", "7");

			sb.append("?");
			for(Map.Entry<String, String> entry:params.entrySet()){
				sb.append(entry.getKey()).append("=");
				sb.append(URLEncoder.encode(entry.getValue(),encoding));
				sb.append("&");
			}
			sb.deleteCharAt(sb.length()-1);//多加了一个&
		}
		Log.e("http", "8");

		HttpURLConnection conn=(HttpURLConnection) new URL(sb.toString()).openConnection();
		conn.setConnectTimeout(10000);
		conn.setRequestMethod("GET");
		Log.e("http", "链接成功。。。。。。");
		if(conn.getResponseCode()==200){
			Log.e("http", "链接成功。。。。。。");
			return true;
		}
		conn.disconnect();
		return true;
	}
}
