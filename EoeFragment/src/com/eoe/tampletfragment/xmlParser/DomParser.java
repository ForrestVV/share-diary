package com.eoe.tampletfragment.xmlParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomParser {

	public DomParser() {
		// TODO Auto-generated constructor stub
	}

	public static List<Person> getPersons(InputStream in) throws ParserConfigurationException, SAXException, Exception{
		List<Person> persons=new ArrayList<Person>();
		
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=factory.newDocumentBuilder();
		Document document=builder.parse(in);
		Element root= document.getDocumentElement();//¸ùnode
		NodeList personNodes=root.getElementsByTagName("person");
		
		for(int i=0;i<personNodes.getLength();i++){
			Element personElement=(Element)personNodes.item(i);
			int id= new Integer(personElement.getAttribute("id"));
			Person person=new Person();
			person.setId(id);
			
			NodeList childNodes=personElement.getChildNodes();
			for(int y=0;y<childNodes.getLength();y++){
				if(childNodes.item(y).getNodeType()==Node.ELEMENT_NODE){
					if("name".equals(childNodes.item(y).getNodeName())){
						String name=childNodes.item(y).getFirstChild().getNodeValue();
						person.setName(name);
					}else if("age".equals(childNodes.item(y).getNodeName())){
						String age=childNodes.item(y).getFirstChild().getNodeName();
						person.setAge(new Short(age));
					}
				}
			}
			
			persons.add(person);
		}
		in.close();
		return persons;
	}
}
