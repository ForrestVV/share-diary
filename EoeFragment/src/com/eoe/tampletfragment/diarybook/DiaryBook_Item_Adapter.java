package com.eoe.tampletfragment.diarybook;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eoe.tamplet.database.Diary_data_item;
import com.eoe.tampletfragment.R;

public class DiaryBook_Item_Adapter extends BaseAdapter {

	//传递过来的数据，用来填充每一个listview
	private ArrayList<Diary_data_item> datas=null;
	private LayoutInflater mInflater;
	
	public DiaryBook_Item_Adapter(ArrayList<Diary_data_item> data, Context context) {
		// TODO Auto-generated constructor stub
		this.mInflater=LayoutInflater.from(context);
		this.datas=data;
	}
	
	public DiaryBook_Item_Adapter(Context context){
		this.mInflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return datas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		View mView=mInflater.inflate(R.layout.diarybook_item, null);

		//获取布局的元素
		TextView mMoth=(TextView)mView.findViewById(R.id.diarybook_month);
		TextView mDay=(TextView)mView.findViewById(R.id.diarybook_day);
		TextView mTime=(TextView)mView.findViewById(R.id.diarybook_time);
		TextView mLocation=(TextView)mView.findViewById(R.id.diarybook_location);
		ImageView mWeather=(ImageView)mView.findViewById(R.id.diarybook_weather);
		TextView mContent=(TextView)mView.findViewById(R.id.diarybook_content);
		ImageView mMood=(ImageView)mView.findViewById(R.id.diarybook_mood);
		
		//对布局控件----赋值
		mMoth.setText(datas.get(position).month);
		mDay.setText(datas.get(position).day);
		mTime.setText(datas.get(position).time);
		if(datas.get(position).location.startsWith("点")|| datas.get(position).location.startsWith("无")){
			mLocation.setText("地址：无");
		}else{
			mLocation.setText(datas.get(position).location);
		}
		mContent.setText(datas.get(position).diary_content);
		//显示天气----
		int weather=(Integer)datas.get(position).weather;
		switch (weather) {
		case 1:
			mWeather.setImageResource(R.drawable.weather_01);
			break;
		case 2:
			mWeather.setImageResource(R.drawable.weather_02);
			break;
		case 3:
			mWeather.setImageResource(R.drawable.weather_03);
			break;
		case 4:
			mWeather.setImageResource(R.drawable.weather_04);
			break;
		case 5:
			mWeather.setImageResource(R.drawable.weather_05);
			break;
		case 6:
			mWeather.setImageResource(R.drawable.weather_06);
			break;
		default:
			mWeather.setImageResource(R.drawable.moren);
			break;
		}
		
		//显示心情----
		int mood=(Integer)datas.get(position).mood;
		switch (mood) {
		case 1:
			mMood.setImageResource(R.drawable.mood01);
			break;
		case 2:
			mMood.setImageResource(R.drawable.mood02);
			break;
		case 3:
			mMood.setImageResource(R.drawable.mood03);
			break;
		case 4:
			mMood.setImageResource(R.drawable.mood04);
			break;
		case 5:
			mMood.setImageResource(R.drawable.mood05);
			break;
		case 6:
			mMood.setImageResource(R.drawable.mood06);
			break;
		case 7:
			mMood.setImageResource(R.drawable.mood07);
			break;
		case 8:
			mMood.setImageResource(R.drawable.mood08);
			break;
		case 9:
			mMood.setImageResource(R.drawable.mood09);
			break;
		default:
			mMood.setImageResource(R.drawable.moren);
			break;
		}
		return mView;
	}
	
}
