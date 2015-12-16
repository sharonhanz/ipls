package model;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Entry {
	private long id;
	private String number;
	private String position;
	private boolean status;
	
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
	
	static public List<Entry> parseJSON(String jstr)
	{
		List<Entry> ret = new  ArrayList<Entry>();
		if (jstr.equals("{}") || jstr.isEmpty()) return ret;
		jstr = jstr.substring(jstr.indexOf(':') + 1, jstr.length() - 1);
		JSONArray array = JSONArray.fromObject(jstr);
		for(int i = 0; i < array.size(); i++)  {
	        JSONObject jo = JSONObject.fromObject(array.get(i));
	        Entry cM = new Entry();
		    cM.setId(jo.getLong("id"));
		    cM.setNumber(jo.getString("number"));
        	cM.setStatus(jo.getInt("status") == 1);
        	if (jo.has("position"))
        		cM.setPosition(jo.getString("position"));
		    ret.add(cM);
		}
	        
        return ret;
	}
}
