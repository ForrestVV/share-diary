package com.eoe.tampletfragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

public class PlanActivity extends FragmentActivity {

	public PlanActivity() {
		// TODO Auto-generated constructor stub
	}
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_plan);
	}

}
