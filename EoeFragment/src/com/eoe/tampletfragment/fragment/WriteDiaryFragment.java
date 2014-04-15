package com.eoe.tampletfragment.fragment;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.eoe.tamplet.database.DataBaseManager;
import com.eoe.tampletfragment.R;
import com.eoe.tampletfragment.view.TitleView;
import com.eoe.tampletfragment.view.TitleView.OnLeftButtonClickListener;
import com.eoe.tampletfragment.view.TitleView.OnRightButtonClickListener;


public class WriteDiaryFragment extends Fragment {

	//����activity,��FragmentActivity�ı���
	private View mView;
	private FragmentActivity mActivity;
	
	private TitleView mTitleView;
	private EditText editText;
	private Button locationBtn;
	private ImageView weatherImgView;
	private ImageView moodImgView;
	
	private boolean isWeatherPopup=false;
	private boolean isMoodPopup=false;
	private boolean isWeatherClicked=true;
	
	//������
	private PopupWindow popupWindow;
	
	//���ݿ����
	DataBaseManager dataBaseManager;
	
	public static WriteDiaryFragment newInstance(){
		WriteDiaryFragment wrtDirF=new WriteDiaryFragment();
		return wrtDirF;
	}
	
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
		View view=inflater.inflate(R.layout.fragment_write_diary, container,false);
		return view;
	}
	
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);	
		
		mView=getView();
		mActivity=getActivity();
		
		//��ʼ��DataBaseManager--���ݿ����
		dataBaseManager=new DataBaseManager(mActivity.getApplicationContext());
		
		//��ȡ�ռ�����
		editText=(EditText)mView.findViewById(R.id.write_diary_multitext);
		//��ȡ����λ��
		locationBtn=(Button)mView.findViewById(R.id.get_location);
		
		mTitleView=(TitleView)mView.findViewById(R.id.title_write_diary);
		mTitleView.setTitle(getDate());
		mTitleView.setTextSize(17);
		mTitleView.setLeftButton(R.string.title_write_diary_back, new OnLeftButtonClickListener() {
			
			@Override
			public void onClick(View button) {
				// TODO Auto-generated method stub
				mActivity.finish();
				deleteTest(34);
			}
		});
		mTitleView.setRightButton(R.string.title_write_diary_add, new OnRightButtonClickListener() {
			
			@Override
			public void onClick(View button) {
				// TODO Auto-generated method stub
				writeToDataBase();
			}
		});
		
		//����--������
		weatherImgView=(ImageView)mView.findViewById(R.id.writeDiary_weather);
		weatherImgView.setTag(0);
		weatherImgView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				isWeatherClicked=true;
				if(popupWindow!=null && isMoodPopup){
					popupWindow.dismiss();
					popupWindow=null;
					isMoodPopup=false;
					initPopWindow();
					popupWindow.showAtLocation(arg0, Gravity.CENTER, 0, 0);
				}else if(popupWindow!=null && isWeatherPopup){
					popupWindow.dismiss();
					popupWindow=null;
					isWeatherPopup=false;
				}else{
					initPopWindow();
					popupWindow.showAtLocation(arg0, Gravity.CENTER, 0, 0);
				}
			}
		});
		
		//����---������
		moodImgView=(ImageView)mView.findViewById(R.id.writeDiary_mood);
		moodImgView.setTag(0);
		moodImgView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				isWeatherClicked=false;
				if(popupWindow!=null && isMoodPopup){
					popupWindow.dismiss();
					popupWindow=null;
					isMoodPopup=false;
				}else if(popupWindow!=null && isWeatherPopup){
					popupWindow.dismiss();
					popupWindow=null;
					isWeatherPopup=false;
					initPopWindow();
					popupWindow.showAtLocation(arg0, Gravity.CENTER, 0, 0);
				}else{
					initPopWindow();
					popupWindow.showAtLocation(arg0, Gravity.CENTER, 0, 0);
				}
			}
		});
	}

	
	//ɾ������----test
	private void deleteTest(int index){
		dataBaseManager.delete(index);
	}
	
	//��ʾ�û��������ѱ���
	private void haveToast(String str){
		Toast toast=Toast.makeText(mActivity.getApplicationContext(), str, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	//д�����ݿ�
	public void writeToDataBase(){
		String year_local=getYear();
		String day_local=getDay();
		String month_local=getMonth();
		String time_local=getTime();
		String weekday_local=getWeekDay();
		String editText_local=editText.getText().toString();
		String loation_btn_local=locationBtn.getText().toString();
		//�����������----��¼����
		int weather_local=(Integer)weatherImgView.getTag();
		int mood_local=(Integer)moodImgView.getTag();
		if(editText_local.length()==0){
			haveToast("�ռ����ݲ���Ϊ��");
		}else{
			/*
			 * ����λ��û���жϾͱ��������ݿ�
			 * ��ȡ����λ��ʱ����Ҫ�жϣ�����¼�ĵ���λ���Ƿ���Ч--
			 * ��Ч�ĵ���λ��--�������ȡ����λ�á�--��û�����磬�޷���ȡ����λ�á�
			 * */
			dataBaseManager.insert(year_local, month_local, day_local, time_local, weekday_local, loation_btn_local, weather_local, mood_local, editText_local);
			haveToast("�ռ��ѱ���");
			//�ӳ�ִ��--
			TimerTask task=new TimerTask() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					mActivity.finish();
				}
			};
			Timer timer=new Timer();
			timer.schedule(task, 1000);
		}
	}
	//���
	private String getYear(){
		Calendar c=Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		String mYear=String.valueOf(c.get(Calendar.YEAR));
		return mYear;
	}
	private String getMonth(){
		Calendar c=Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		String mMoth=String.valueOf(c.get(Calendar.MONTH)+1);
		return mMoth;
	}
	private String getDay(){
		Calendar c=Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		String mDay=String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		return mDay;
	}
	//��ȡ�ռ��ύʱ��ʱ��--��¼�����ݿ���
	private String getTime(){
		Calendar c=Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		String mHour=String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String mMinute=String.valueOf(c.get(Calendar.MINUTE));
		String mSecond=String.valueOf(c.get(Calendar.SECOND));
		return mHour+"ʱ"+mMinute+"��"+mSecond+"��";
	}
	private String getWeekDay(){
		Calendar c=Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		String mWeek=String.valueOf(c.get(Calendar.DAY_OF_WEEK));
/*		if("1".equals(mWeek)){
			mWeek="��";
		}else if("2".equals(mWeek)){
			mWeek="һ";
		}else if("3".equals(mWeek)){
			mWeek="��";
		}else if("4".equals(mWeek)){
			mWeek="��";
		}else if("5".equals(mWeek)){
			mWeek="��";
		}else if("6".equals(mWeek)){
			mWeek="��";
		}else if("7".equals(mWeek)){
			mWeek="��";
		}*/
		return mWeek;
	}
	//��ȡ��������--��ʾ��title��
	private String getDate(){
		Calendar c=Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		String mYear=String.valueOf(c.get(Calendar.YEAR));
		String mMoth=String.valueOf(c.get(Calendar.MONTH)+1);
		String mDay=String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		String mWeek=String.valueOf(c.get(Calendar.DAY_OF_WEEK));
		if("1".equals(mWeek)){
			mWeek="��";
		}else if("2".equals(mWeek)){
			mWeek="һ";
		}else if("3".equals(mWeek)){
			mWeek="��";
		}else if("4".equals(mWeek)){
			mWeek="��";
		}else if("5".equals(mWeek)){
			mWeek="��";
		}else if("6".equals(mWeek)){
			mWeek="��";
		}else if("7".equals(mWeek)){
			mWeek="��";
		}
		return mYear+"��"+mMoth+"��"+mDay+"��"+" "+"����"+mWeek;
	}
	
	//����������---��ʼ��
	private void initPopWindow() {
		// TODO Auto-generated method stub
		View popView;
		boolean isWeather=true;
		if(isWeatherClicked){
			popView=mActivity.getLayoutInflater().inflate(R.layout.weather_popuopwindow, null, false);
			isWeatherPopup=true;
		}else{
			popView=mActivity.getLayoutInflater().inflate(R.layout.mood_popupwindow, null, false);
			isMoodPopup=true;
			isWeather=false;
		}
		popupWindow=new PopupWindow(popView,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//		weatherPopup.setAnimationStyle(R.style.);
		if(isWeather==false){
			//����--ѡ��
			ImageView mood01=(ImageView)popView.findViewById(R.id.mood_imgview01);
			mood01.setTag(1);
			mood01.setOnClickListener(new MoodWeatherListener(popupWindow,moodImgView));
			
			ImageView mood02=(ImageView)popView.findViewById(R.id.mood_imgview02);
			mood02.setTag(2);
			mood02.setOnClickListener(new MoodWeatherListener(popupWindow,moodImgView));
			
			ImageView mood03=(ImageView)popView.findViewById(R.id.mood_imgview03);
			mood03.setTag(3);
			mood03.setOnClickListener(new MoodWeatherListener(popupWindow,moodImgView));
			
			ImageView mood04=(ImageView)popView.findViewById(R.id.mood_imgview04);
			mood04.setTag(4);
			mood04.setOnClickListener(new MoodWeatherListener(popupWindow,moodImgView));
			
			ImageView mood05=(ImageView)popView.findViewById(R.id.mood_imgview05);
			mood05.setTag(5);
			mood05.setOnClickListener(new MoodWeatherListener(popupWindow,moodImgView));
			
			ImageView mood06=(ImageView)popView.findViewById(R.id.mood_imgview06);
			mood06.setTag(6);
			mood06.setOnClickListener(new MoodWeatherListener(popupWindow,moodImgView));
			
			ImageView mood07=(ImageView)popView.findViewById(R.id.mood_imgview07);
			mood07.setTag(7);
			mood07.setOnClickListener(new MoodWeatherListener(popupWindow,moodImgView));
			
			ImageView mood08=(ImageView)popView.findViewById(R.id.mood_imgview08);
			mood08.setTag(8);
			mood08.setOnClickListener(new MoodWeatherListener(popupWindow,moodImgView));
			
			ImageView mood09=(ImageView)popView.findViewById(R.id.mood_imgview09);
			mood09.setTag(9);
			mood09.setOnClickListener(new MoodWeatherListener(popupWindow,moodImgView));
			
		}else{
			ImageView weather01=(ImageView)popView.findViewById(R.id.weather_imgview01);
			weather01.setTag(10);
			weather01.setOnClickListener(new MoodWeatherListener(popupWindow, weatherImgView));
			
			ImageView weather02=(ImageView)popView.findViewById(R.id.weather_imgview02);
			weather02.setTag(11);
			weather02.setOnClickListener(new MoodWeatherListener(popupWindow, weatherImgView));
			
			ImageView weather03=(ImageView)popView.findViewById(R.id.weather_imgview03);
			weather03.setTag(12);
			weather03.setOnClickListener(new MoodWeatherListener(popupWindow, weatherImgView));
			
			ImageView weather04=(ImageView)popView.findViewById(R.id.weather_imgview04);
			weather04.setTag(13);
			weather04.setOnClickListener(new MoodWeatherListener(popupWindow, weatherImgView));
			
			ImageView weather05=(ImageView)popView.findViewById(R.id.weather_imgview05);
			weather05.setTag(14);
			weather05.setOnClickListener(new MoodWeatherListener(popupWindow, weatherImgView));
			
			ImageView weather06=(ImageView)popView.findViewById(R.id.weather_imgview06);
			weather06.setTag(15);
			weather06.setOnClickListener(new MoodWeatherListener(popupWindow, weatherImgView));
			
		}
		mView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				if(popupWindow!=null && popupWindow.isShowing()){
					popupWindow.dismiss();
					popupWindow=null;
				}
				return false;
			}
		});		
	}
	
}
