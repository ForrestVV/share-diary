package com.eoe.tampletfragment.listview;

import com.eoe.tampletfragment.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BaseItem extends LinearLayout {

	private ImageView imageView;
	private TextView textView;
//	private ImageView imageViweGo;
	
	
	public BaseItem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public BaseItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.diary_base_item, this);
		
		imageView=(ImageView)findViewById(R.id.diary_book_item_image);
		textView=(TextView)findViewById(R.id.diary_book_item_text);
	}
	
	public void setImageResource(int  imageId){
		imageView.setImageResource(imageId);
	}
	public void setText(int textId){
		textView.setText(textId);
	}

	@SuppressLint("NewApi")
	public BaseItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

}
