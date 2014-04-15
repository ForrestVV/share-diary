package com.eoe.tampletfragment.fragment;

import com.eoe.tampletfragment.R;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;

public class MoodWeatherListener implements OnClickListener {

	private PopupWindow popupWindow;
	private ImageView moodWeatherImgView;
	
	public MoodWeatherListener(PopupWindow p,ImageView imgView) {
		// TODO Auto-generated constructor stub
		this.popupWindow=p;
		this.moodWeatherImgView=imgView;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int tag=(Integer)v.getTag();
		switch (tag) {
		case 1:
			Log.e("tag","1");

			moodWeatherImgView.setImageResource(R.drawable.mood01);
			moodWeatherImgView.setTag(1);
			popupWindow.dismiss();
			popupWindow=null;
			break;
		case 2:
			moodWeatherImgView.setImageResource(R.drawable.mood02);
			moodWeatherImgView.setTag(2);
			popupWindow.dismiss();
			popupWindow=null;
			break;
		case 3:
			moodWeatherImgView.setImageResource(R.drawable.mood03);
			moodWeatherImgView.setTag(3);
			popupWindow.dismiss();
			popupWindow=null;
			break;
		case 4:
			moodWeatherImgView.setImageResource(R.drawable.mood04);
			moodWeatherImgView.setTag(4);
			popupWindow.dismiss();
			popupWindow=null;
			break;
		case 5:
			moodWeatherImgView.setImageResource(R.drawable.mood05);
			moodWeatherImgView.setTag(5);
			popupWindow.dismiss();
			popupWindow=null;
			break;
		case 6:
			moodWeatherImgView.setImageResource(R.drawable.mood06);
			moodWeatherImgView.setTag(6);
			popupWindow.dismiss();
			popupWindow=null;
			break;
		case 7:
			moodWeatherImgView.setImageResource(R.drawable.mood07);
			moodWeatherImgView.setTag(7);
			popupWindow.dismiss();
			popupWindow=null;
			break;
		case 8:
			moodWeatherImgView.setImageResource(R.drawable.mood08);
			moodWeatherImgView.setTag(8);
			popupWindow.dismiss();
			popupWindow=null;
			break;
		case 9:
			moodWeatherImgView.setImageResource(R.drawable.mood09);
			moodWeatherImgView.setTag(9);
			popupWindow.dismiss();
			popupWindow=null;
			break;
			//开始处理天气图标选择------
		case 10:
			moodWeatherImgView.setImageResource(R.drawable.weather_01);
			moodWeatherImgView.setTag(1);
			popupWindow.dismiss();
			popupWindow=null;
			break;
		case 11:
			moodWeatherImgView.setImageResource(R.drawable.weather_02);
			moodWeatherImgView.setTag(2);
			popupWindow.dismiss();
			popupWindow=null;
			break;
		case 12:
			moodWeatherImgView.setImageResource(R.drawable.weather_03);
			moodWeatherImgView.setTag(3);
			popupWindow.dismiss();
			popupWindow=null;
			break;
		case 13:
			moodWeatherImgView.setImageResource(R.drawable.weather_04);
			moodWeatherImgView.setTag(4);
			popupWindow.dismiss();
			popupWindow=null;
			break;
		case 14:
			moodWeatherImgView.setImageResource(R.drawable.weather_05);
			moodWeatherImgView.setTag(5);
			popupWindow.dismiss();
			popupWindow=null;
			break;
		case 15:
			moodWeatherImgView.setImageResource(R.drawable.weather_06);
			moodWeatherImgView.setTag(6);
			popupWindow.dismiss();
			popupWindow=null;
			break;
		default:
			moodWeatherImgView.setTag(0);
			break;
		}
	}

}
