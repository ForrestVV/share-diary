package com.eoe.tampletfragment.baidulocation;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

public class MyLocationListener implements BDLocationListener{

	private Button locaBtn;
	private ProgressBar progressBar;
	public MyLocationListener(Button btn,ProgressBar progressBar) {
		// TODO Auto-generated constructor stub
		this.locaBtn=btn;
		this.progressBar=progressBar;
	}

	@Override
	public void onReceiveLocation(BDLocation location) {
		// TODO Auto-generated method stub
		if(location==null)
			return;
		StringBuilder sb=new StringBuilder();
		sb.append("time:");
		sb.append(location.getTime());
		sb.append("\nerror code:");
		sb.append(location.getLocType());
//		sb.append("\nlatitude:");
//		sb.append(location.getLatitude());
//		sb.append("\nlontitude:");
//		sb.append(location.getLongitude());
//		sb.append("\nradius:");
//		sb.append(location.getRadius());
		if(location.getLocType()==BDLocation.TypeGpsLocation){
			sb.append("\nspeed:");
			sb.append(location.getSpeed());
			sb.append("\nsatelite:");
			sb.append(location.getSatelliteNumber());
		}else if(location.getLocType()==BDLocation.TypeNetWorkLocation){
			sb.append("\naddr:");
			sb.append(location.getAddrStr());
			progressBar.setVisibility(View.INVISIBLE);
			locaBtn.setText(location.getAddrStr());
		}else{
			progressBar.setVisibility(View.INVISIBLE);
			locaBtn.setText("没有网络，无法获取地理位置");
		}
		logMsg(sb.toString());

	}

	private void logMsg(String str) {
		// TODO Auto-generated method stub
		Log.e("tag",str);
	}

	@Override
	public void onReceivePoi(BDLocation location) {
		// TODO Auto-generated method stub
		if(location==null)
			return;
		StringBuilder sb=new StringBuilder();
		sb.append("time:");
		sb.append(location.getTime());
		sb.append("\nerror code:");
		sb.append(location.getLocType());
		sb.append("\nlatitude:");
		sb.append(location.getLatitude());
		sb.append("\nlontitude:");
		sb.append(location.getLongitude());
		sb.append("\nradius:");
		sb.append(location.getRadius());
		if(location.getLocType()==BDLocation.TypeGpsLocation){
			sb.append("\nspeed:");
			sb.append(location.getSpeed());
			sb.append("\nsatelite:");
			sb.append(location.getSatelliteNumber());
		}else if(location.getLocType()==BDLocation.TypeNetWorkLocation){
			sb.append("\naddr:");
			sb.append(location.getAddrStr());
		}
		
		if(location.hasPoi()){
			sb.append("\nPoi:");
			sb.append(location.getPoi());
		}else{
			sb.append("noPoi information");
		}
		Log.e("tag",sb.toString());
	}

}
