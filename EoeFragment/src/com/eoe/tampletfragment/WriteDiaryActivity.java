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
	
	//����location��weather��mood��ImageView
	private ImageView addView;
	private Button locationButton;
	private ImageView weatherView;
	private ImageView moodView;
	private ProgressBar progressBar;
	
	//�ٶȶ�λ
	public LocationClient mLocationClient=null;
	public BDLocationListener mListener;
	
	
	public WriteDiaryActivity() {
		// TODO Auto-generated constructor stub
	}

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_write_diary);
		
		//��Ƭ������λ�á�����������
		View mView02=(View)findViewById(R.id.bottom_item);
		Log.e("tag", "0");
		addView=(ImageView) mView02.findViewById(R.id.writediary_addimage);
		locationButton=(Button) mView02.findViewById(R.id.get_location);
		Log.e("tag", "1");

		weatherView=(ImageView) mView02.findViewById(R.id.writeDiary_weather);
		moodView=(ImageView) mView02.findViewById(R.id.writeDiary_mood);
		progressBar=(ProgressBar)mView02.findViewById(R.id.getlocation_progressBar);
		progressBar.setVisibility(View.INVISIBLE);
		
		//�ٶȵ�ͼ----��ȡ����λ��
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
				locationButton.setText("        ���ڻ�ȡ����λ��");
				progressBar.setVisibility(View.VISIBLE);
			}
		});

	}
		
	
	//�����location������
	private LocationClientOption getLocationOption(){
		LocationClientOption locationOption=new LocationClientOption();
		locationOption.setOpenGps(true);
		locationOption.setCoorType("bd0911");
		locationOption.setPriority(LocationClientOption.NetWorkFirst);
		locationOption.setAddrType("all");
		locationOption.setProdName("WeiDiary_BaiduLocation");
		locationOption.setScanSpan(5000);//����λ�����ʱ����5��
		locationOption.disableCache(true);//��ֹ���û��涨λ
		locationOption.setPoiNumber(2);//��෵��POI����
		locationOption.setPoiDistance(1000);//POI��ѯ����
		locationOption.setPoiExtraInfo(false);//�Ƿ���ҪPOI�ĵ绰�͵�ַ����ϸ��Ϣ
		Log.e("location", "�ٶȶ�λ��ʼ......");
		return locationOption;
	}
}
