package model;

import java.util.ArrayList;
import java.util.List;

import model.Values.Privilege;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Place {
	private long id;
	private String number;
	private int occupied;
	private String position;
	private Privilege privilege;
	private int size;
	
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
	public int getOccupied() {
		return occupied;
	}
	public void setOccupied(int occupied) {
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
	static public List<Place> parseJSON(String jstr)
	{
		List<Place> ret = new  ArrayList<Place>();
		if (jstr.equals("{}") || jstr.isEmpty()) return ret;
		jstr = jstr.substring(jstr.indexOf(':') + 1, jstr.length() - 1);
		JSONArray array = JSONArray.fromObject(jstr);
		for(int i = 0; i < array.size(); i++)  {
	        JSONObject jo = JSONObject.fromObject(array.get(i));
	        Place cM = new Place();
		    cM.setId(jo.getLong("id"));
		    cM.setNumber(jo.getString("number"));
		    cM.setOccupied(jo.getInt("occupied"));
        	cM.setPosition(jo.getString("position"));
        	cM.setPrivilege(jo.getInt("privilege"));
        	cM.setSize(jo.getInt("size"));
		    ret.add(cM);
		}
	        
        return ret;
	}
}
