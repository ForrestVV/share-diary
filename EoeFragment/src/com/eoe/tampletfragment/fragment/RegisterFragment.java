package com.eoe.tampletfragment.fragment;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eoe.tampletfragment.HelpActivity;
import com.eoe.tampletfragment.R;
import com.eoe.tampletfragment.http.Register;
import com.eoe.tampletfragment.view.TitleView;
import com.eoe.tampletfragment.view.TitleView.OnLeftButtonClickListener;
import com.eoe.tampletfragment.view.TitleView.OnRightButtonClickListener;

public class RegisterFragment extends Fragment {

	private View mView;
	private FragmentActivity mActivity;
	private TitleView titleView;
	//注册的控件
	private EditText  name_edit;
	private EditText passWrd01_edit;
	private EditText passWrd02_edit;
	private EditText email_edit;
	private Button btn_cancel;
	private Button btn_ok;

	public static RegisterFragment newInstance(){
		RegisterFragment rf=new RegisterFragment();
		return rf;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.fragment_register,container,false);
		return view;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		mView=getView();
		mActivity=getActivity();
		
		//title配置
		titleView=(TitleView)mView.findViewById(R.id.title_register);
		titleView.setTitle("注册");
		titleView.setLeftButton("返回", new OnLeftButtonClickListener() {
			
			@Override
			public void onClick(View button) {
				// TODO Auto-generated method stub
				mActivity.finish();
			}
		});
		titleView.setRightButton("帮助", new OnRightButtonClickListener() {
			
			@Override
			public void onClick(View button) {
				// TODO Auto-generated method stub
				startActivity(new Intent(mActivity,HelpActivity.class));
			}
		});
				
		//提取控件
		name_edit=(EditText)mView.findViewById(R.id.register_name_edit);
		passWrd01_edit=(EditText)mView.findViewById(R.id.register_pssw_edit);
		passWrd02_edit=(EditText)mView.findViewById(R.id.register_pssw_edit2);
		email_edit=(EditText)mView.findViewById(R.id.register_email_edit);
		btn_cancel=(Button)mView.findViewById(R.id.register_btn_cancel);
		btn_ok=(Button)mView.findViewById(R.id.register_btn_ok);
		
		//发送get请求
		btn_ok.setOnClickListener(new OnClickListener() {
			@SuppressLint("ShowToast")
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					if(name_edit.getText().toString().equals("")){
						haveToast("用户名不能为空！");
						return;
					}
					if(passWrd01_edit.getText().toString().equals("")||passWrd02_edit.getText().toString().equals("")){
						haveToast("密码不能为空！");
						return;
					}
					if(!(passWrd01_edit.getText().toString().equals(passWrd02_edit.getText().toString()))){
						haveToast("两次输入密码不一致");
						return;
					}
					if(email_edit.getText().toString().equals("")){
						haveToast("邮箱不能为空！");
						return;
					}
					if(!emailFormat(email_edit.getText().toString())){
						haveToast("邮箱格式不正确！");
//						return;
					}
					Map<String,String> params=new HashMap<String, String>();
					params.put("name", name_edit.getText().toString());
					params.put("email", email_edit.getText().toString());
					params.put("passwrd",passWrd01_edit.getText().toString());
					register(params);
					haveToast("注册成功");
					//延迟返回
					TimerTask tmTask=new TimerTask() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							mActivity.finish();
						}
					};
					Timer t=new Timer();
					t.schedule(tmTask, 1500);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	//网络连接部分用一个新线程启动执行
	private void register(final Map<String,String> params){
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					httpGetRequest(params);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}).start();
	}
	//get形式发送到服务器--------
	private void httpGetRequest(Map<String,String > params) throws Exception{
		Log.e("http", "5");

		Register.save(params);
	}

	//验证邮箱的格式
	private boolean emailFormat(String email){
		String pattern= "[a-zA-Z0-9-_]+@+[a-zA-Z0-9]+.+[a-zA-Z0-9]";
		Pattern p=Pattern.compile(pattern);
		Matcher matcher=p.matcher(email);
		if(!matcher.find()){
			return false;
		}
		return true;
	}
	
	//提示用户------
	private void haveToast(String str){
		Toast toast=Toast.makeText(mActivity.getApplicationContext(), str, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
}
