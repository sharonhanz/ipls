package model;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

public class Balance {
	private String uri;
	private String carNumber;
	private Double balance;
	
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
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	static public List<Balance> parseXML(String xml)
	{
		List<Balance> ret = new  ArrayList<Balance>();
		try {
			StringReader read = new StringReader(xml);
			InputSource inputSource = new InputSource(read);
			SAXBuilder builder = new SAXBuilder();
	        Document doc = builder.build(inputSource);
	        Element collection = doc.getRootElement();
	        List<Element> balanceList = collection.getChildren("U8129336708a3d_ipls_Balance");
	        if (null != balanceList){
	        	for(int i = 0, j = balanceList.size();i < j; i++)  
		        {
		        	Element cE = balanceList.get(i);
		        	Element cUri = cE.getChild("uri");
		        	Element cNumber = cE.getChild("carnumber");
		        	Element cBalance = cE.getChild("balance");
		        	Balance cM = new Balance();
		        	cM.setUri(cUri.getText());
		        	cM.setCarNumber(cNumber.getText());
		        	cM.setBalance(Double.valueOf(cBalance.getText()));
		        	ret.add(cM);
		        }
	        }
	        
		} catch (Exception e) {
			// do nothing
		}
        return ret;
	}
}
