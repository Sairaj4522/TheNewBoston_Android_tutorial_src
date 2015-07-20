package com.thenewboston.travis;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class HandlingXMLStuff extends DefaultHandler{
	
	XMLDataCollected info = new XMLDataCollected();
	
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		
		if(localName.equals("city")){
			String city = attributes.getValue("data");
			info.setCity(city);
		} else if(localName.equals("temp_f")){
			String t = attributes.getValue("data");
			int temp = Integer.parseInt(t);
			info.setTemp(temp);
		}
	}


	public String getInformation() {
		return info.dataToString();
	}
}
