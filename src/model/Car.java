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
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Car {
	private long id;
	private String number;
	private Date registerTime;
	private Date expireTime;
	private Privilege privilege;
	private int size;
	private String name;
	private String phone;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
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
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	static public List<Car> parseJSON(String jstr)
	{
		List<Car> ret = new  ArrayList<Car>();
		if (jstr.equals("{}") || jstr.isEmpty()) return ret;
		jstr = jstr.substring(jstr.indexOf(':') + 1, jstr.length() - 1);
		JSONArray array = JSONArray.fromObject(jstr);
		for(int i = 0; i < array.size(); i++)  {
	        JSONObject jCar = JSONObject.fromObject(array.get(i));
		    Car cM = new Car();
		    cM.setId(jCar.getLong("id"));
		    cM.setNumber(jCar.getString("number"));
		    cM.setRegisterTime(jCar.getString("registertime"));
		    cM.setExpireTime(jCar.getString("expiretime"));
		    cM.setPrivilege(jCar.getInt("privilege"));
		    cM.setSize(jCar.getInt("size"));
		    cM.setName(jCar.getString("name"));
		    cM.setPhone(jCar.getString("phone"));
		    ret.add(cM);
		}
	        
        return ret;
	}
}
