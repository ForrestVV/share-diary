package com.eoe.tamplet.database;

public class Diary_data_item {
	
	/*
	 * 数据库的每一行数据
	 * */

	public String key_id;//主键
	public String year;//年
	public String month;
	public String day;
	public String time;
	public String weekday;
	public String location;//地理位置
	public int weather;//天气
	public int mood;//心情
	public String diary_content;//日记内容
	
	public Diary_data_item() {
		// TODO Auto-generated constructor stub
	}
	
	public Diary_data_item(String key_id, String year, String month, String day, String time, String weekday, String location, int weahter, int mood, String diarycontent){
		this.key_id=key_id;
		this.year=year;
		this.month=month;
		this.day=day;
		this.time=time;
		this.weekday=weekday;
		this.location=location;
		this.weather=weahter;
		this.mood=mood;
		this.diary_content=diarycontent;
	}
	

}
