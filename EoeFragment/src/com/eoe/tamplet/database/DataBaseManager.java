package com.eoe.tamplet.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * 封装了对数据库的基本操作
 * 封装了常用的业务逻辑
 * */
public class DataBaseManager{
	
	private static final String DATABASE_NAME="diary_Version3.db";
	private static final String DATABASE_TABLE="diaryRecord";
	private static final int DATABSE_VERSION=1;
	
	private static final String KEY_ID_Column="key_id";//主键
	private static final String YEAY_Column="_year";//日期
	private static final String MONTH_Column="_month";
	private static final String DAY_Column="_day";
	private static final String TIME_Column="_time";
	private static final String WEEKDAY_Column="_weekday";
	private static final String LOCATION_Column="_location";//地理位置
	private static final String WEATHER_Column="_weather";//天气
	private static final String MOOD_Column = "_mood";//心情
	private static final String DIARYCONTENT_Column="_diary";//日记内容
	
	private Context context;
	private SQLiteDatabase db;
	private DataBaseHelper DBHelper;
	
	
	public DataBaseManager(Context contt){
		context=contt;
		DBHelper=new DataBaseHelper(context);//需要确保context被实例化
		
		db =DBHelper.getWritableDatabase();
	}
		
	//插入数据库
	public long insert(String year, String month,String day, String time,String weekday, String location, int weather, int mood, String content){
		ContentValues values=new ContentValues();
		values.put(YEAY_Column, year);
		values.put(MONTH_Column, month);
		values.put(DAY_Column, day);
		values.put(TIME_Column, time);
		values.put(WEEKDAY_Column, weekday);
		values.put(LOCATION_Column, location);
		values.put(WEATHER_Column, weather);
		values.put(MOOD_Column, mood);
		values.put(DIARYCONTENT_Column, content);
		return db.insert(DATABASE_TABLE, null, values);
	}
	
	//一次性插入多条数据
/*	public void add(ArrayList<Diary_data_item> Rows){
		db.beginTransaction();
		try{
			for(Diary_data_item row: Rows){
				ContentValues value=new ContentValues();
//				value.put(KEY_ID_Column, row.key_id);
				value.put(LOCATION_Column, row.location);
				value.put(DATE_Column, row.date);
				value.put(WEATHER_Column, row.weather);
				value.put(MOOD_Column, row.mood);
				value.put(DIARYCONTENT_Column, row.diary_content);
				db.insert(DATABASE_TABLE, null, value);
			}
		}finally{
			db.endTransaction();
		}
	}*/
	//删除操作----只是把除了_id外的各个字段设置为空
	public boolean delete(int id){
		ContentValues c=new ContentValues();
		c.put(LOCATION_Column, "");
		c.put(YEAY_Column, "");
		c.put(MONTH_Column, "");
		c.put(DAY_Column, "");
		c.put(TIME_Column, "");
		c.put(WEEKDAY_Column, "");
		c.put(WEATHER_Column, "");
		c.put(MOOD_Column, "");
		c.put(DIARYCONTENT_Column, "");
		return db.update(DATABASE_TABLE, c, KEY_ID_Column+"="+id, null)>0;
//		return db.delete(DATABASE_TABLE, KEY_ID_Column+"="+id, null)>0;//正在删除
	}
	
	//获取所有的数据--返回Cursor
	private Cursor getAll(){
		Cursor cur=db.query(DATABASE_TABLE, null, null, null, null, null, null);
		return cur;
	}
	//查询所有的数据
	public ArrayList<Diary_data_item> query(){
		ArrayList<Diary_data_item> Rows=new ArrayList<Diary_data_item>();
		Cursor c=getAll();
		c.moveToLast();
		while(c.moveToPrevious()){
			Diary_data_item row=new Diary_data_item();
			row.key_id=c.getString(c.getColumnIndex(KEY_ID_Column));
			row.year=c.getString(c.getColumnIndex(YEAY_Column));
			row.month=c.getString(c.getColumnIndex(MONTH_Column));
			row.day=c.getString(c.getColumnIndex(DAY_Column));
			row.time=c.getString(c.getColumnIndex(TIME_Column));
			row.weekday=c.getString(c.getColumnIndex(WEEKDAY_Column));
			row.location=c.getString(c.getColumnIndex(LOCATION_Column));
			row.weather=c.getInt(c.getColumnIndex(WEATHER_Column));
			row.mood=c.getInt(c.getColumnIndex(MOOD_Column));
			row.diary_content=c.getString(c.getColumnIndex(DIARYCONTENT_Column));
			Rows.add(row);
		}
		c.close();
		return Rows;
	}
	
	//关闭数据库
	public void close(){
		db.close();
		DBHelper.close();
	}
	
		
	
	/*创建数据库--同时创建了数据表
	 * 
	 * 维护管理数据库的基类。
	 * */
	private static class DataBaseHelper extends SQLiteOpenHelper {
		
		public DataBaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABSE_VERSION);
			// TODO Auto-generated constructor stub
		}

		//数据库第一次创建的时候会被调用
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			String sql="CREATE TABLE "+DATABASE_TABLE+"( "+KEY_ID_Column+" INTEGER DEFAULT '1' NOT NULL PRIMARY KEY AUTOINCREMENT, "+YEAY_Column +" TEXT, "+MONTH_Column+" TEXT, "+DAY_Column+" TEXT , "+TIME_Column+" TEXT, "+WEEKDAY_Column+" TEXT, "+ LOCATION_Column +" TEXT, "+ WEATHER_Column +" INTEGER, "+ MOOD_Column+" INTEGER, "+DIARYCONTENT_Column+ " TEXT NOT NULL)";
			db.execSQL(sql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			// TODO Auto-generated method stub
			db.execSQL("drop tabel if exists"+DATABASE_TABLE);
			onCreate(db);
		}
	}
	
}

