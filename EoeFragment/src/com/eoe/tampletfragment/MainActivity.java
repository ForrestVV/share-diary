package com.eoe.tampletfragment;

import java.util.LinkedList;

import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.eoe.tampletfragment.adapter.MyBaseAdapter;
import com.eoe.tampletfragment.fragment.FragmentIndicator;
import com.eoe.tampletfragment.fragment.FragmentIndicator.OnIndicateListener;
import com.eoe.tampletfragment.listview.MyListView;
import com.eoe.tampletfragment.listview.onRefreshListener;
/**
 * @author yangyu
 *	功能描述：主Activity类，继承自FragmentActivity
 */
public class MainActivity extends FragmentActivity {

	public static Fragment[] mFragments;

	//*********
	private LinkedList<String> data;
	private MyBaseAdapter adapter;
	private MyListView listView;
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		context=getApplicationContext();

		setFragmentIndicator(1);

		displayScreenSize();//获取屏幕大小
		
		data=new LinkedList<String>();
		for(int i=0;i<11;i++){
			data.add(String.valueOf(i));
		}
		
		listView=(MyListView) findViewById(R.id.listview);
		adapter=new MyBaseAdapter(context, data);
		
		listView.setAdapter(adapter);
		
		listView.setOnRefreshListener(new onRefreshListener() {//as a callback
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				RefreshTask rTask=new RefreshTask();
				rTask.execute(1000);
			}
		});
		
	}
		
	class RefreshTask extends AsyncTask<Integer, Integer, String> {

		@Override
		protected String doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			try{
				Thread.sleep(arg0[0]);
			}catch(Exception e){
				e.printStackTrace();
			}
			data.addFirst("刷新后的内容");
			return null;
		}
		
		protected void onPostExecute(String result){
			super.onPostExecute(result);
			adapter.notifyDataSetChanged();
			listView.onRefreshComplete();
		}
	}

	private void displayScreenSize() {
		// TODO Auto-generated method stub
		if(this.getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE){
			setTitle("landscape");
		}
		if(this.getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT){
			setTitle("portrait");
		}
				
		//获取屏幕大小2
		DisplayMetrics dMetrics=new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
		int screenWidth=dMetrics.widthPixels;
		int screenHeight=dMetrics.heightPixels;
		
		Log.e("am10", "screenWidth:"+screenWidth+"  screenHeight"+screenHeight);
	}
	//*************
		
	/**
	 * 初始化fragment
	 */
	private void setFragmentIndicator(int whichIsDefault) {
		mFragments = new Fragment[3];
		mFragments[0] = getSupportFragmentManager().findFragmentById(R.id.fragment_home);
		mFragments[1] = getSupportFragmentManager().findFragmentById(R.id.fragment_search);
		mFragments[2] = getSupportFragmentManager().findFragmentById(R.id.fragment_settings);
		getSupportFragmentManager().beginTransaction().hide(mFragments[0])
				.hide(mFragments[1]).hide(mFragments[2]).show(mFragments[whichIsDefault]).commit();

		FragmentIndicator mIndicator = (FragmentIndicator) findViewById(R.id.indicator);
		FragmentIndicator.setIndicator(whichIsDefault);
		mIndicator.setOnIndicateListener(new OnIndicateListener() {
			@Override
			public void onIndicate(View v, int which) {
				getSupportFragmentManager().beginTransaction()
						.hide(mFragments[0]).hide(mFragments[1])
						.hide(mFragments[2]).show(mFragments[which]).commit();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
}
