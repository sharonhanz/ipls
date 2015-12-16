package control;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.Balance;
import model.Exit;
import model.Values;
import net.sf.json.JSONArray;
import util.HttpHelper;

public class BalanceBean {
	public boolean isExist(String carNumber)
	{
		boolean ret = false;
		String url = Values.DOMAIN + "Balance/?Balance.carnumber=" + carNumber;
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Exit> us = Exit.parseJSON(resultXML);
		if (us.size() > 0)
			ret = true;
		return ret;
	}
	
	public Balance get(String carNumber) {
		String url = Values.DOMAIN + "Balance/?Balance.carnumber=" + carNumber;
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Balance> us = Balance.parseJSON(resultXML);
		if (us.size() == 0) return null;
		return us.get(0);
	}
	
	public List<Balance> getAll()
	{
		String url = Values.DOMAIN + "Balance/";
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Balance> ret = Balance.parseJSON(resultXML);
		return ret;
	}
	
	int add(String number, Double balance)
	{
		if (isExist(number))
			return -1;
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("carnumber", number);
		map.put("balance", balance);
		JSONArray json = JSONArray.fromObject(map);
		String jstr = json.get(0).toString();
		String url = Values.DOMAIN + "Balance/";
		String resultXML = HttpHelper.SendHttpRequest("post", url, jstr);
		System.out.println(resultXML);
		return 0;
	}
	
	public int gain(String number, Double fee) {
		Balance b = get(number);
		if (b == null) return -1;
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("carnumber", number);
		map.put("balance", b.getBalance()+fee);
		JSONArray json = JSONArray.fromObject(map);
		String jstr = json.get(0).toString();
		String url = Values.DOMAIN + "Balance/" + b.getId();
		String resultXML = HttpHelper.SendHttpRequest("put", url, jstr);
		System.out.println(resultXML);
		return 0;
	}
	
	public int pay(String number, Double fee) {
		Balance b = get(number);
		if (b == null) return -1;
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("carnumber", number);
		map.put("balance", b.getBalance()-fee);
		JSONArray json = JSONArray.fromObject(map);
		String jstr = json.get(0).toString();
		String url = Values.DOMAIN + "Balance/" + b.getId();
		String resultXML = HttpHelper.SendHttpRequest("put", url, jstr);
		System.out.println(resultXML);
		return 0;
	}
}
