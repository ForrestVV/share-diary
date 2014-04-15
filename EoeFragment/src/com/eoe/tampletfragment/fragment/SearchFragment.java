package com.eoe.tampletfragment.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eoe.tampletfragment.DiaryBookActivity;
import com.eoe.tampletfragment.HelpActivity;
import com.eoe.tampletfragment.MainActivity;
import com.eoe.tampletfragment.PlanActivity;
import com.eoe.tampletfragment.R;
import com.eoe.tampletfragment.WriteDiaryActivity;
import com.eoe.tampletfragment.view.TitleView;
import com.eoe.tampletfragment.view.TitleView.OnLeftButtonClickListener;
import com.eoe.tampletfragment.view.TitleView.OnRightButtonClickListener;

/**
 * @author yangyu
 *	功能描述：搜索fragment页面
 */
public class SearchFragment extends Fragment {

	private View mParent;
	private FragmentActivity mActivity;

	private TitleView mTitle;
	
	
	//************add on 15:09
	private ImageView imgView_write;
	private TextView txtView_write;
	
	private ImageView imgView_book;
	private TextView txtView_book;
	
	private ImageView imgView_plan;
	private TextView txtView_plan;
	
	public static SearchFragment newInstance(int index) {
		SearchFragment f = new SearchFragment();

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
		View view = inflater.inflate(R.layout.fragment_search, container,false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mParent = getView();
		mActivity = getActivity();

		mTitle = (TitleView) mParent.findViewById(R.id.title);
		mTitle.setTitle(R.string.title_search);
		mTitle.setLeftButton(R.string.back, new OnLeftButtonClickListener() {

			@Override
			public void onClick(View button) {
				backHomeFragment();
			}
		});
		mTitle.setRightButton(R.string.help, new OnRightButtonClickListener() {

			public void onClick(View button) {
				goHelpActivity();
			}
		});
		
		//写日记
		View write_diary_view=mParent.findViewById(R.id.write_diary);
		write_diary_view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goWriteActivity();
			}
		});
		imgView_write=(ImageView)write_diary_view.findViewById(R.id.diary_book_item_image);
		imgView_write.setImageResource(R.drawable.diary_book_item_image);
		txtView_write=(TextView)write_diary_view.findViewById(R.id.diary_book_item_text);
		txtView_write.setText(R.string.write_diary);
		
		//日记本
		View diary_book_vew=mParent.findViewById(R.id.diary_book);
		diary_book_vew.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goDiaryBookActivity();
			}
		});
		imgView_book=(ImageView)diary_book_vew.findViewById(R.id.diary_book_item_image);
		txtView_book=(TextView)diary_book_vew.findViewById(R.id.diary_book_item_text);
		imgView_book.setImageResource(R.drawable.diary_book_item_image);
		txtView_book.setText(R.string.diary_book);
		
		//日程表
		View plan_view=mParent.findViewById(R.id.plan);
		plan_view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goPlan();
			}
		});
		imgView_plan=(ImageView)plan_view.findViewById(R.id.diary_book_item_image);
		imgView_plan.setImageResource(R.drawable.diary_book_item_image);
		txtView_plan=(TextView)plan_view.findViewById(R.id.diary_book_item_text);
		txtView_plan.setText(R.string.plan);
	}
	
	//start help activity
	private void goHelpActivity() {
		Intent intent = new Intent(mActivity, HelpActivity.class);
		startActivity(intent);
	}

	//转到“写日记”的activity
	private void goWriteActivity(){
		Intent intent=new Intent(mActivity,WriteDiaryActivity.class);
		startActivity(intent);
	}
	
	//转到"日记本”的activity
	private void goDiaryBookActivity(){
		startActivity(new Intent(mActivity,DiaryBookActivity.class));
	}
	
	//转到“备忘录”的activity
	private void goPlan(){
		startActivity(new Intent(mActivity,PlanActivity.class));
	}
	
	/**
	 * 返回到首页
	 */
	private void backHomeFragment() {
		getFragmentManager().beginTransaction()
				.hide(MainActivity.mFragments[1])
				.show(MainActivity.mFragments[0]).commit();
		FragmentIndicator.setIndicator(0);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (!hidden) {
		}
	}


}
