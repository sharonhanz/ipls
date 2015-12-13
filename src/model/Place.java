package model;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import model.Values.Privilege;

public class Place {
	private String uri;
	private String number;
	private boolean occupied;
	private String position;
	private Privilege privilege;
	private int size;
	
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
	public boolean getOccupied() {
		return occupied;
	}
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Privilege getPrivilege() {
		return privilege;
	}
	public void setPrivilege(int privilege) {
		this.privilege = Privilege.values()[privilege];
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	static public List<Place> parseXML(String xml)
	{
		List<Place> ret = new  ArrayList<Place>();
		try {
			StringReader read = new StringReader(xml);
			InputSource inputSource = new InputSource(read);
			SAXBuilder builder = new SAXBuilder();
	        Document doc = builder.build(inputSource);
	        Element collection = doc.getRootElement();
	        List<Element> placeList = collection.getChildren("U8129336708a3d_ipls_Place");
	        if (null != placeList){
	        	for(int i = 0, j = placeList.size();i < j; i++)  
		        {
		        	Element cE = placeList.get(i);
		        	Element cUri = cE.getChild("uri");
		        	Element cNumber = cE.getChild("number");
		        	Element cSize = cE.getChild("size");
		        	Element cPrivilege = cE.getChild("privilege");
		        	Element cOccupied = cE.getChild("occupied");
		        	Element cPosition = cE.getChild("position");
		        	Place cM = new Place();
		        	cM.setUri(cUri.getText());
		        	cM.setNumber(cNumber.getText());
		        	cM.setOccupied(cOccupied.getText().equals("1"));
		        	cM.setPosition(cPosition.getText());
		        	cM.setPrivilege(Integer.getInteger(cPrivilege.getText()));
		        	cM.setSize(Integer.getInteger(cSize.getText()));
		        	ret.add(cM);
		        }
	        }
	        
		} catch (Exception e) {
			// do nothing
		}
        return ret;
	}
}
