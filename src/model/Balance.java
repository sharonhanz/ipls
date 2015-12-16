package model;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Balance {
	private long id;
	private String carNumber;
	private Double balance;
	
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
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}

	static public List<Balance> parseJSON(String jstr)
	{
		List<Balance> ret = new  ArrayList<Balance>();
		if (jstr.equals("{}") || jstr.isEmpty()) return ret;
		jstr = jstr.substring(jstr.indexOf(':') + 1, jstr.length() - 1);
		JSONArray array = JSONArray.fromObject(jstr);
		for(int i = 0; i < array.size(); i++)  {
	        JSONObject jo = JSONObject.fromObject(array.get(i));
		    Balance cM = new Balance();
		    cM.setId(jo.getLong("id"));
		    cM.setCarNumber(jo.getString("carnumber"));
        	cM.setBalance(jo.getDouble("balance"));
		    ret.add(cM);
		}
	        
        return ret;
	}
}
