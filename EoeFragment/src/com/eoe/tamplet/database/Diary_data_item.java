package com.eoe.tamplet.database;

public class Diary_data_item {
	
	/*
	 * ���ݿ��ÿһ������
	 * */

	public String key_id;//����
	public String year;//��
	public String month;
	public String day;
	public String time;
	public String weekday;
	public String location;//����λ��
	public int weather;//����
	public int mood;//����
	public String diary_content;//�ռ�����
	
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
