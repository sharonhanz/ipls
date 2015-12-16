package control;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.Exit;
import model.Values;
import net.sf.json.JSONObject;
import util.HttpHelper;

public class ExitBean {
	public boolean isExist(String exitNumber)
	{
		boolean ret = false;
		String url = Values.DOMAIN + "Exit/?Exit.number=" + exitNumber;
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Exit> us = Exit.parseJSON(resultXML);
		if (us.size() > 0)
			ret = true;
		return ret;
	}
	
	public Exit get(String number) {
		String url = Values.DOMAIN + "Exit/?Exit.number=" + number;
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Exit> us = Exit.parseJSON(resultXML);
		if (us.size() == 0) return null;
		return us.get(0);
	}
	
	public List<Exit> getAll()
	{
		String url = Values.DOMAIN + "Exit/";
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Exit> ret = Exit.parseJSON(resultXML);
		return ret;
	}
	
	public int add(String number, String position, boolean status)
	{
		if (isExist(number))
			return -1;
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("number", number);
		if (status) {
			map.put("status", 1);
		} else {
			map.put("status", 0);
		}
		map.put("position", position);
		JSONObject jo = JSONObject.fromObject(map);
		String url = Values.DOMAIN + "Exit/";
		String resultXML = HttpHelper.SendHttpRequest("post", url, jo.toString());
		System.out.println(resultXML);
		return 0;
	}
	
	public int open(String number) {
		Exit p = get(number);
		if (p == null) return -1;
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("number", number);
		map.put("status", 1);
		map.put("position", p.getPosition());
		JSONObject jo = JSONObject.fromObject(map);
		String url = Values.DOMAIN + "Exit/" + p.getId();
		String resultXML = HttpHelper.SendHttpRequest("put", url, jo.toString());
		System.out.println(resultXML);
		return 0;
	}
	
	public int close(String number) {
		Exit p = get(number);
		if (p == null) return -1;
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("number", number);
		map.put("status", 0);
		map.put("position", p.getPosition());
		JSONObject jo = JSONObject.fromObject(map);
		String url = Values.DOMAIN + "Exit/" + p.getId();
		String resultXML = HttpHelper.SendHttpRequest("put", url, jo.toString());
		System.out.println(resultXML);
		return 0;
	}
}
