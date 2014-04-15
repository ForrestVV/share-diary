package com.eoe.tampletfragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.eoe.tampletfragment.baidulocation.MyLocationListener;

public class WriteDiaryActivity extends FragmentActivity {
	
	//代表location、weather和mood的ImageView
	private ImageView addView;
	private Button locationButton;
	private ImageView weatherView;
	private ImageView moodView;
	private ProgressBar progressBar;
	
	//百度定位
	public LocationClient mLocationClient=null;
	public BDLocationListener mListener;
	
	
	public WriteDiaryActivity() {
		// TODO Auto-generated constructor stub
	}

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_write_diary);
		
		//照片、地理位置、天气、心情
		View mView02=(View)findViewById(R.id.bottom_item);
		Log.e("tag", "0");
		addView=(ImageView) mView02.findViewById(R.id.writediary_addimage);
		locationButton=(Button) mView02.findViewById(R.id.get_location);
		Log.e("tag", "1");

		weatherView=(ImageView) mView02.findViewById(R.id.writeDiary_weather);
		moodView=(ImageView) mView02.findViewById(R.id.writeDiary_mood);
		progressBar=(ProgressBar)mView02.findViewById(R.id.getlocation_progressBar);
		progressBar.setVisibility(View.INVISIBLE);
		
		//百度地图----获取地理位置
		mListener=new MyLocationListener(locationButton,progressBar);
		mLocationClient=new LocationClient(getApplicationContext());
		mLocationClient.setAK("nXVaGLq7fvIy0SosNuMeYKGG");
		mLocationClient.registerLocationListener(mListener);
		mLocationClient.setLocOption(getLocationOption());
		
		locationButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mLocationClient.start();
				if(mLocationClient!=null && mLocationClient.isStarted()){
					Log.e("tag", "3");
					mLocationClient.requestLocation();
				}
				locationButton.setText("        正在获取地理位置");
				progressBar.setVisibility(View.VISIBLE);
			}
		});

	}
		
	
	//具体的location的设置
	private LocationClientOption getLocationOption(){
		LocationClientOption locationOption=new LocationClientOption();
		locationOption.setOpenGps(true);
		locationOption.setCoorType("bd0911");
		locationOption.setPriority(LocationClientOption.NetWorkFirst);
		locationOption.setAddrType("all");
		locationOption.setProdName("WeiDiary_BaiduLocation");
		locationOption.setScanSpan(5000);//发起定位请求的时间间隔5秒
		locationOption.disableCache(true);//禁止启用缓存定位
		locationOption.setPoiNumber(2);//最多返回POI个数
		locationOption.setPoiDistance(1000);//POI查询距离
		locationOption.setPoiExtraInfo(false);//是否需要POI的电话和地址等详细信息
		Log.e("location", "百度定位开始......");
		return locationOption;
	}
}
