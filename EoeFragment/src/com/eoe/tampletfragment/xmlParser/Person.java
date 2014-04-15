package com.eoe.tampletfragment.xmlParser;

public class Person {

	private Integer id;
	private String name;
	private Short age;
	
	public Person() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getAge() {
		return age;
	}

	public void setAge(Short age) {
		this.age = age;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String toString(){
		return "id="+id+",name="+name+",age="+age;
	}

}
