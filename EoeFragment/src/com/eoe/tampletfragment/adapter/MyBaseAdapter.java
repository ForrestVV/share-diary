package com.eoe.tampletfragment.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eoe.tampletfragment.R;

public class MyBaseAdapter extends BaseAdapter {

	private LinkedList<String> data;
	private Context context;

	public MyBaseAdapter() {
		// TODO Auto-generated constructor stub
	}

	public MyBaseAdapter(Context context, LinkedList<String> data){
		this.context=context;
		this.data=data;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return (long) arg0;
	}

	public int getItemViewType(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		convertView=LayoutInflater.from(context).inflate(R.layout.item, null);
		TextView textView=(TextView) convertView.findViewById(R.id.textView_item);
		textView.setText(data.get(position));
		return convertView;
	}

}
