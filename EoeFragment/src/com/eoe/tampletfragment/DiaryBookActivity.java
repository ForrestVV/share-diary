package com.eoe.tampletfragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.ListView;

import com.eoe.tamplet.database.DataBaseManager;
import com.eoe.tamplet.database.Diary_data_item;
import com.eoe.tampletfragment.diarybook.DiaryBook_Item_Adapter;

public class DiaryBookActivity extends FragmentActivity {

	//DataBaseManager
	private DataBaseManager dataBaseaManager;
	private ArrayList<Diary_data_item> data;
	
	public DiaryBookActivity() {
		// TODO Auto-generated constructor stub
		
	}

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_diary_book);
		
		//��ȡ���ݿ�
		dataBaseaManager=new DataBaseManager(this.getApplicationContext());
		data=dataBaseaManager.query();
		
		//����listview----���Զ����data
		DiaryBook_Item_Adapter item_Adapter=new DiaryBook_Item_Adapter(data, this);
		ListView mListView=(ListView) findViewById(R.id.diarybook_item_listview);
		mListView.setAdapter(item_Adapter);

	}
	
}
