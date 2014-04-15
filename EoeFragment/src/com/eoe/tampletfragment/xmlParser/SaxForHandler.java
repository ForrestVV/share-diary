package com.eoe.tampletfragment.xmlParser;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;


/*
 * xml文档的格式：
 * <persons>
 * 		<person id="23">
 * 			<name>NEWii</name>
 * 			<age>23</age>
 * 		</person>
 * 		<person id="22">
 * 			<name>Wii</name>
 * 			<age>25</age>
 * 		</person>
 * </persons>
 * 
 *应用中应该添加的代码：
 *InputStream is=XMLactivity.class.getClassLoader().getResourceAsStream("person.xml");
 *SaxForHandler handler=new SaxForHandler();
 *SAXParserFactory spf=SAXparserFactory.newInstance();
 *SAXParser saxParser=spf.newSAXParser();
 *saxParser.parse(is,handler);
 *List<Person> list=handler.getPersons();
 *is.close();
 *return list;
 * */
public class SaxForHandler extends DefaultHandler {

	private static final String TAG="SaxForHandler";
	private List<Person> persons;
	private String perTag;//通过此变量，记录前一个标签的名称
	Person person;//记录当前person
	
	public SaxForHandler() {
		// TODO Auto-generated constructor stub
	}
	public List<Person> getPersons(){
		return persons;
	}
	
	//遇到textNode时的操作
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		String data=new String(ch,start,length).trim();
		if(!"".equals(data.trim())){
			
		}
		if("name".equals(perTag)){
			person.setName(data);
		}else if("age".equals(perTag)){
			person.setAge(new Short(data));
		}
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
		Log.i(TAG, "***endDocument()****");
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		if("person".equals(localName)){
			persons.add(person);
		}
		person=null;
	}

	//在此事件中作初始化行为
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
		persons=new ArrayList<Person>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		if("person".equals(localName)){
			for(int i=0;i<attributes.getLength();i++){
				person=new Person();
				person.setId(Integer.valueOf(attributes.getValue(i)));
			}
		}
		perTag=localName;//记录Element的值
	}

}
