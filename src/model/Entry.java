package model;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

public class Entry {
	private String uri;
	private String number;
	private String position;
	private boolean status;
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	static public List<Entry> parseXML(String xml)
	{
		List<Entry> ret = new  ArrayList<Entry>();
		try {
			StringReader read = new StringReader(xml);
			InputSource inputSource = new InputSource(read);
			SAXBuilder builder = new SAXBuilder();
	        Document doc = builder.build(inputSource);
	        Element collection = doc.getRootElement();
	        List<Element> entryList = collection.getChildren("U8129336708a3d_ipls_Entry");
	        if (null != entryList){
	        	for(int i = 0, j = entryList.size();i < j; i++)  
		        {
		        	Element cE = entryList.get(i);
		        	Element cUri = cE.getChild("uri");
		        	Element cNumber = cE.getChild("number");
		        	Element cPosition = cE.getChild("position");
		        	Element cStatus = cE.getChild("status");
		        	Entry cM = new Entry();
		        	cM.setUri(cUri.getText());
		        	cM.setNumber(cNumber.getText());
		        	cM.setStatus(cStatus.getText().equals("1"));
		        	cM.setPosition(cPosition.getText());
		        	ret.add(cM);
		        }
	        }
	        
		} catch (Exception e) {
			// do nothing
		}
        return ret;
	}
}
