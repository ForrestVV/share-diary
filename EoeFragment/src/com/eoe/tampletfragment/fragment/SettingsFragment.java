package com.eoe.tampletfragment.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.eoe.tampletfragment.HelpActivity;
import com.eoe.tampletfragment.R;
import com.eoe.tampletfragment.RegisterActivity;
import com.eoe.tampletfragment.view.TitleView;
import com.eoe.tampletfragment.view.TitleView.OnLeftButtonClickListener;
import com.eoe.tampletfragment.view.TitleView.OnRightButtonClickListener;

/**
 * @author yangyu
 *	��������������fragmentҳ��
 */
public class SettingsFragment extends Fragment {
	
	private View mParent;
	
	private FragmentActivity mActivity;

	private TitleView mTitle;

	//ע�����
	private LinearLayout register_linear;
	private LinearLayout login_linear;
	
	//ͬ���ռ�button
	private Button updateBtn;
	/**
	 * Create a new instance of DetailsFragment, initialized to show the text at
	 * 'index'.
	 */
	public static SettingsFragment newInstance(int index) {
		SettingsFragment f = new SettingsFragment();

		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", index);
		f.setArguments(args);

		return f;
	}

	public int getShownIndex() {
		return getArguments().getInt("index", 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_settings, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mParent = getView();
		mActivity = getActivity();

		//����ע���----
		register_linear=(LinearLayout)mParent.findViewById(R.id.linear_register);
		register_linear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(mActivity,RegisterActivity.class));
			}
		});
		
		mTitle = (TitleView) mParent.findViewById(R.id.title);
		mTitle.setTitle(R.string.title_settings);
		mTitle.setLeftButton(R.string.back, new OnLeftButtonClickListener() {
			@Override
			public void onClick(View button) {
				
			}
		});
		mTitle.hiddenLeftButton();//������Title����ߵİ�ť
		
		mTitle.setRightButton(R.string.help, new OnRightButtonClickListener() {
			@Override
			public void onClick(View button) {
				goHelp();
			}
		});
		
		//ͬ���ռ�--------
		updateBtn=(Button)mParent.findViewById(R.id.setting_update);
		updateBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	//����activity
	private void goHelp(){
		startActivity(new Intent(mActivity,HelpActivity.class));
	}

}
