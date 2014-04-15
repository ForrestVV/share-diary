package com.eoe.tampletfragment.xmlParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import android.annotation.SuppressLint;
import android.util.Xml;

public class PullParser {

	public PullParser() {
		// TODO Auto-generated constructor stub
	}
	
	@SuppressLint("UseValueOf")
	private List<Person> xmlParser(InputStream in) throws XmlPullParserException, IOException{
//		InputStream is=this.getClass().getClassLoader().getResourceAsStream("**.xml");
		Person person=null;
		List<Person> persons=null;
		XmlPullParser pullParser=Xml.newPullParser();
		pullParser.setInput(in,"UTF-8");
		
		int event=pullParser.getEventType();//触发第一个事件
		while(event!=XmlPullParser.END_DOCUMENT){
			switch(event){
			case XmlPullParser.START_DOCUMENT:
				persons=new ArrayList<Person>();
				break;
			case XmlPullParser.START_TAG:
				if("person".equals(pullParser.getName())){
					int id=new Integer(pullParser.getAttributeName(0));
					person=new Person();
					person.setId(id);
				}
				if(person!=null){
					if("name".equals(pullParser.getName())){
						person.setName(pullParser.nextText());
					}
					if("age".equals(pullParser.getName())){
						person.setAge(new Short(pullParser.nextText()));
					}
				}
				break;
			case XmlPullParser.END_TAG:
				if("person".equals(pullParser.getName())){
					persons.add(person);
					person=null;
				}
				break;
			}
			event=pullParser.next();
		}
		return persons;
	}

	//生成xml文档----
	public static void save(List<Person> persons, OutputStream out) throws Exception, IllegalStateException, IOException{
		//顶一个一个接口，实现串行化--对象序列化
		XmlSerializer serializer=Xml.newSerializer();
		serializer.setOutput(out,"UTF-8");
		serializer.startDocument("UTF-8", true);
		serializer.startTag(null, "persons");
		
		for(Person person:persons){
			serializer.startTag(null, "person");
			serializer.attribute(null, "id", person.getId().toString());
			serializer.startTag(null, "name");
			serializer.text(person.getName());
			serializer.endTag(null, "name");
			
			serializer.startTag(null, "age");
			serializer.text(person.getAge().toString());
			serializer.endTag(null, "age");
			
			serializer.endTag(null, "person");
		}
		
		serializer.endTag(null, "persons");
		serializer.endDocument();
		out.flush();
		out.close();
	}
}
