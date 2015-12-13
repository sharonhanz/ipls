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

public class Park {
	private String uri;
	private String carNumber;
	private String placeNumber;
	private Date startTime;
	private Date endTime;
	private String entryNumber;
	private String exitNumber;
	private Double fee;
	private boolean left;
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String number) {
		this.carNumber = number;
	}
	public String getPlaceNumber() {
		return placeNumber;
	}
	public void setPlaceNumber(String number) {
		this.placeNumber = number;
	}
	public String getEntryNumber() {
		return entryNumber;
	}
	public void setEntryNumber(String number) {
		this.entryNumber = number;
	}
	public String getExitNumber() {
		return exitNumber;
	}
	public void setExitNumber(String number) {
		this.exitNumber = number;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(String timeStirng) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.nnnnnn");
		try {
			startTime = fmt.parse(timeStirng);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(String timeStirng) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.nnnnnn");
		try {
			endTime = fmt.parse(timeStirng);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public boolean getLeft() {
		return left;
	}
	public void setLeft(boolean left) {
		this.left = left;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	
	static public List<Park> parseXML(String xml)
	{
		List<Park> ret = new  ArrayList<Park>();
		try {
			StringReader read = new StringReader(xml);
			InputSource inputSource = new InputSource(read);
			SAXBuilder builder = new SAXBuilder();
	        Document doc = builder.build(inputSource);
	        Element collection = doc.getRootElement();
	        List<Element> parkList = collection.getChildren("U8129336708a3d_ipls_Park");
	        if (null != parkList){
	        	for(int i = 0, j = parkList.size();i < j; i++)  
		        {
		        	Element cE = parkList.get(i);
		        	Element cUri = cE.getChild("uri");
		        	Element cCarNumber = cE.getChild("carNumber");
		        	Element cPlaceNumber = cE.getChild("placeNumber");
		        	Element cEntryNumber = cE.getChild("entryNumber");
		        	Element cStartTime = cE.getChild("startTime");
		        	Element cLeft = cE.getChild("left");
		        	Park cM = new Park();
		        	cM.setUri(cUri.getText());
		        	cM.setCarNumber(cCarNumber.getText());
		        	cM.setPlaceNumber(cPlaceNumber.getText());
		        	cM.setEntryNumber(cEntryNumber.getText());
		        	cM.setStartTime(cStartTime.getText());
		        	cM.setLeft(cLeft.getText().equals("1"));
		        	if (cM.getLeft()) {
			        	Element cExitNumber = cE.getChild("exitNumber");
			        	Element cEndTime = cE.getChild("endTime");
			        	Element cFee = cE.getChild("fee");
			        	cM.setExitNumber(cExitNumber.getText());
			        	cM.setFee(Double.valueOf(cFee.getText()));
			        	cM.setEndTime(cEndTime.getText());
		        	}
		        	ret.add(cM);
		        }
	        }
	        
		} catch (Exception e) {
			// do nothing
		}
        return ret;
	}
}
