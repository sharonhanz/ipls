package model;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import model.Values.Privilege;

public class Car {
	private String uri;
	private String number;
	private Date registerTime;
	private Date expireTime;
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
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(String timeStirng) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.nnnnnn");
		try {
			registerTime = fmt.parse(timeStirng);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(String timeStirng) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.nnnnnn");
		try {
			expireTime = fmt.parse(timeStirng);
		} catch (ParseException e) {
			e.printStackTrace();
		}
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
	static public List<Car> parseXML(String xml)
	{
		List<Car> ret = new  ArrayList<Car>();
		try {
			StringReader read = new StringReader(xml);
			InputSource inputSource = new InputSource(read);
			SAXBuilder builder = new SAXBuilder();
	        Document doc = builder.build(inputSource);
	        Element collection = doc.getRootElement();
	        List<Element> carList = collection.getChildren("U8129336708a3d_ipls_Car");
	        if (null != carList){
	        	for(int i = 0, j = carList.size();i < j; i++)  
		        {
		        	Element cE = carList.get(i);
		        	Element cUri = cE.getChild("uri");
		        	Element cNumber = cE.getChild("number");
		        	Element cRegisterTime = cE.getChild("registerTime");
		        	Element cExpireTime = cE.getChild("expireTime");
		        	Element cPrivilege = cE.getChild("privilege");
		        	Element cSize = cE.getChild("size");
		        	Car cM = new Car();
		        	cM.setUri(cUri.getText());
		        	cM.setNumber(cNumber.getText());
		        	cM.setRegisterTime(cRegisterTime.getText());
		        	cM.setExpireTime(cExpireTime.getText());
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
