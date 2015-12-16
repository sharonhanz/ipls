package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Park {
	private long id;
	private String carNumber;
	private String placeNumber;
	private Date startTime;
	private Date endTime;
	private String entryNumber;
	private String exitNumber;
	private Double fee;
	private boolean left;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
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
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
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
	
	static public List<Park> parseJSON(String jstr)
	{
		List<Park> ret = new  ArrayList<Park>();
		if (jstr.equals("{}") || jstr.isEmpty()) return ret;
		//System.out.println(jstr);
		jstr = jstr.substring(jstr.indexOf(':') + 1, jstr.length() - 1);
		JSONArray array = JSONArray.fromObject(jstr);
		for(int i = 0; i < array.size(); i++)  {
	        JSONObject jo = JSONObject.fromObject(array.get(i));
	        Park cM = new Park();
	        cM.setId(jo.getLong("id"));
        	cM.setCarNumber(jo.getString("carnumber"));
        	cM.setPlaceNumber(jo.getString("placenumber"));
        	cM.setEntryNumber(jo.getString("entrynumber"));
        	cM.setStartTime(jo.getString("starttime"));
        	cM.setLeft(jo.getInt("left") == 1);
        	if (cM.getLeft()) {
	        	cM.setExitNumber(jo.getString("exitnumber"));
	        	cM.setFee(jo.getDouble("fee"));
	        	cM.setEndTime(jo.getString("endtime"));
        	}
		    ret.add(cM);
		}
	        
        return ret;
	}
}
