package com.eoe.tampletfragment.listview;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.eoe.tampletfragment.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class MyListView extends ListView implements OnScrollListener {

	private static final String TAG="am10";
	private static final int RELEASE_TO_REFRESH=0;
	private static final int PULL_TO_REFRESH=1;
	private static final int REFRESHING=2;
	private static final int DONE=3;
	private static final int LOADING=4;
	
	//实际的padding的距离与界面上偏移距离的比例
	private final static int RATIO=3;
	
	private LayoutInflater inflater;
	private LinearLayout headView;
	private TextView tipsTextView;
	private TextView lastUpdatedTextView;
	private ImageView arrowImageView;
	private ProgressBar progressBar;
	
	private RotateAnimation animation;
	private RotateAnimation reverseAnimation;
	
	//用于保证startY的值在一个完整的touch事件中只被记录一次
	private boolean isRecored;
	private int startY;
	private int firstItemIndex;
	
	private int headContentWidth;
	private int headContentHeight;
	
	private int state;
	
	private boolean isBack;
	
	private onRefreshListener refreshListener;
	
	private boolean isRefreshable;
	
//**************************************************************
	//每个view重新绘制
	private Paint marginPaint;
	private Paint linePaint;
	private int paperColor;
	private float margin;
	
//**************************************************************

	
	public MyListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
		initOnDrawResources();
	}

	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub

	}
	
	

	private void init(Context context) {
		// TODO Auto-generated method stub
		inflater= LayoutInflater.from(context);
		headView=(LinearLayout)inflater.inflate(R.layout.head, null);
		arrowImageView=(ImageView) headView.findViewById(R.id.head_arrowImageView);
		arrowImageView.setMaxHeight(100);
		arrowImageView.setMaxWidth(100);
		arrowImageView.setMinimumWidth(70);
		arrowImageView.setMinimumHeight(50);
		
		progressBar = (ProgressBar)headView.findViewById(R.id.head_progressbar);
		
		tipsTextView=(TextView)headView.findViewById(R.id.head_tipsTextView);
		lastUpdatedTextView=(TextView)headView.findViewById(R.id.head_lastUpdatedTextView);
		
		//估算headView的宽和高
		measureView(headView);
		headContentHeight=headView.getMeasuredHeight();
		headContentWidth=headView.getMeasuredWidth();
		
		headView.setPadding(0, -1*headContentHeight, 0, 0);
		headView.invalidate();
		
		addHeaderView(headView,null,false);//add to the top of listview
		setOnScrollListener(this);
		
		animation=new RotateAnimation(0,-180,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(250);
		animation.setFillAfter(true);
		
		reverseAnimation=new RotateAnimation(-180, 0,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(200);
		reverseAnimation.setFillAfter(true);
		
		state=DONE;
		isRefreshable=true;
	}

	
	
	private void measureView(View child) {
		// TODO Auto-generated method stub
		ViewGroup.LayoutParams p=child.getLayoutParams();
		if(p==null){
			p=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		
		int childWidthSpec=ViewGroup.getChildMeasureSpec(0, 0+0, p.width);
		int lpHeight=p.height;
		int childHeightSpec;
		
		if(lpHeight>0){
			childHeightSpec=MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
		}else{
			childHeightSpec=MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}
	
	public boolean onTouchEvent(MotionEvent event){
		if(isRefreshable){
			switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				if(firstItemIndex==0 && !isRecored){
					isRecored=true;
					
					//触摸屏的位置
					startY=(int)event.getY();
					Log.e(TAG, "在down时候记录当前位置"+"startY:"+startY);
				}
				break;
			case MotionEvent.ACTION_UP:
				if(state!=REFRESHING && state!=LOADING){
					if(state==DONE){
						//nothing
					}
				if(state==PULL_TO_REFRESH){
					state=DONE;
					changeHeaderViewByState();
					Log.e(TAG, "由下拉刷新状态，到down状态");
				}
				if(state==RELEASE_TO_REFRESH){
					state=REFRESHING;
					onRefresh();
					Log.v(TAG,"由松开刷新状态到done状态");
				}
			}
			
			isRecored=false;
			isBack=false;
			break;
			
			case MotionEvent.ACTION_MOVE:
				int tempY=(int)event.getY();
				if(!isRecored && firstItemIndex==0){
					isRecored=true;
					startY=tempY;
					Log.e(TAG, "在move的时候记录下位置"+"startY:"+startY);
				}
				
				if(state!=REFRESHING && isRecored && state!=LOADING){				
					if(state==RELEASE_TO_REFRESH){
						setSelction(0);
						
						if(((tempY-startY)/RATIO<headContentHeight)&&(tempY-startY)>0){
							state=PULL_TO_REFRESH;
							changeHeaderViewByState();
							Log.e(TAG, "由松开状态转变到下拉刷新状态");
						}else if(tempY-startY<=0){//推到顶了
							state=DONE;
							changeHeaderViewByState();
							Log.e(TAG, "由松开刷新状态到done状态");
						}else{
							//
						}			
					}
					
				if(state==PULL_TO_REFRESH){
					setSelection(0);
					if((tempY-startY)/RATIO>=headContentHeight){
						state=RELEASE_TO_REFRESH;
						isBack=true;
						changeHeaderViewByState();
						Log.e(TAG, "由done或者下拉刷新状态转变到松开刷新状态");
						
					}else if(tempY-startY<=0){
						state=DONE;
						changeHeaderViewByState();
						Log.e(TAG, "由done状态或者下拉刷新状态转变到done状态");
					}
				}
				
				if(state==DONE){
					if(tempY-startY>0){
						state=PULL_TO_REFRESH;
						changeHeaderViewByState();
					}
				}
				
				if(state==PULL_TO_REFRESH){
					headView.setPadding(0, -1*headContentHeight+(tempY-startY)/RATIO, 0, 0);
				}
				
				if(state==RELEASE_TO_REFRESH){
					headView.setPadding(0, (tempY-startY)/RATIO, 0, 0);
					
				}
			}
				break;
		}
		}
		return super.onTouchEvent(event);
	}

	private void setSelction(int i) {
		// TODO Auto-generated method stub
		
	}

	private void onRefresh() {
		// TODO Auto-generated method stub
		if(refreshListener!=null){
			refreshListener.onRefresh();//
		}
	}

	private void changeHeaderViewByState() {
		// TODO Auto-generated method stub
		switch(state){
			case RELEASE_TO_REFRESH:
				arrowImageView.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
				tipsTextView.setVisibility(View.VISIBLE);
				lastUpdatedTextView.setVisibility(View.VISIBLE);
				
				arrowImageView.clearAnimation();
				arrowImageView.startAnimation(animation);
				
				tipsTextView.setText("松开刷新");
				Log.e(TAG, "当前状态，松开刷新");
				break;
			case PULL_TO_REFRESH:
				progressBar.setVisibility(View.GONE);
				tipsTextView.setVisibility(View.VISIBLE);
				lastUpdatedTextView.setVisibility(View.VISIBLE);
				arrowImageView.clearAnimation();
				arrowImageView.setVisibility(View.VISIBLE);
				
				if(isBack){
					isBack=false;
					arrowImageView.clearAnimation();
					arrowImageView.startAnimation(reverseAnimation);
					tipsTextView.setText("下拉刷新");
				}else{
					tipsTextView.setText("下拉刷新");
				}
				Log.e(TAG, "当前状态，下拉刷新");
				break;
			case REFRESHING:
				Log.e(TAG, "Refresing....");
				headView.setPadding(0, 0, 0, 0);
				progressBar.setVisibility(View.VISIBLE);
				arrowImageView.clearAnimation();
				arrowImageView.setVisibility(View.GONE);
				tipsTextView.setText("正在刷新。。。");
				lastUpdatedTextView.setVisibility(View.VISIBLE);
				Log.e(TAG, "当前状态，正在刷新...");
				break;
				
			case DONE:
				headView.setPadding(0, -1*headContentHeight, 0, 0);
				progressBar.setVisibility(View.GONE);
				arrowImageView.clearAnimation();
				arrowImageView.setImageResource(R.drawable.arrow_down);
				tipsTextView.setText("下拉刷新");
				lastUpdatedTextView.setVisibility(View.VISIBLE);
				
				Log.e(TAG, "当前状态，done");
				break;
		}
	}

	public void setOnRefreshListener(onRefreshListener onRefreshListener){
		this.refreshListener=onRefreshListener;
		isRefreshable=true;
	}
	public void onRefreshComplete(){
		state=DONE;
		SimpleDateFormat formate=new SimpleDateFormat("yyyy年MM月dd日HH:mm");
		String data=formate.format(new Date());
		lastUpdatedTextView.setText("最新更新"+data);
		changeHeaderViewByState();
	}
	
	
	
//**************************************************************
	//每个view重新绘制
	private void initOnDrawResources(){
		Resources myResources=getResources();
		//创建在onDraw中使用的绘图刷
		marginPaint =new Paint(Paint.ANTI_ALIAS_FLAG);
		marginPaint.setColor(myResources.getColor(R.color.notepad_margin));
		
		linePaint =new Paint(Paint.ANTI_ALIAS_FLAG);
		linePaint.setColor(myResources.getColor(R.color.notepad_line));
		
		paperColor=myResources.getColor(R.color.notepad_paper);
		margin=myResources.getDimension(R.dimen.notepad_margin);
	}
	
//**************************************************************

	
//**************************************************************
	//重新绘制
	public void onDraw(Canvas canvas){
		canvas.drawColor(paperColor);
		canvas.drawLine(0, 0, getMeasuredHeight(), 0, linePaint);
		canvas.drawLine(0, getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight(), linePaint);
		canvas.drawLine(margin, 0, margin, getMeasuredHeight(), marginPaint);
		
		canvas.save();
		canvas.translate(margin, 0);
		
		super.onDraw(canvas);
		canvas.restore();
	}
	
//**************************************************************

}
