package com.eoe.tampletfragment.listview;

import com.eoe.tampletfragment.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class writediary_bottom_item extends LinearLayout {

	public writediary_bottom_item(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public writediary_bottom_item(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.writediary_bottom, this);
	}

	@SuppressLint("NewApi")
	public writediary_bottom_item(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.writediary_bottom, this,false);
		
		
	}

}
