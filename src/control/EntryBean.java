package control;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.Entry;
import model.Values;
import net.sf.json.JSONObject;
import util.HttpHelper;

public class EntryBean {
	public boolean isExist(String entryNumber)
	{
		boolean ret = false;
		String url = Values.DOMAIN + "Entry/?Entry.number=" + entryNumber;
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Entry> us = Entry.parseJSON(resultXML);
		if (us.size() > 0)
			ret = true;
		return ret;
	}
	
	public Entry get(String number) {
		String url = Values.DOMAIN + "Entry/?Entry.number=" + number;
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Entry> us = Entry.parseJSON(resultXML);
		if (us.size() == 0) return null;
		return us.get(0);
	}
	
	public List<Entry> getAll()
	{
		String url = Values.DOMAIN + "Entry/";
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Entry> ret = Entry.parseJSON(resultXML);
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
		String url = Values.DOMAIN + "Entry/";
		String resultXML = HttpHelper.SendHttpRequest("post", url, jo.toString());
		System.out.println(resultXML);
		return 0;
	}
	
	public int open(String number) {
		Entry p = get(number);
		if (p == null) return -1;
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("number", number);
		map.put("position", p.getPosition());
		map.put("status", 1);
		JSONObject jo = JSONObject.fromObject(map);
		String url = Values.DOMAIN + "Entry/" + p.getId();
		String resultXML = HttpHelper.SendHttpRequest("put", url, jo.toString());
		System.out.println(resultXML);
		return 0;
	}
	
	public int close(String number) {
		Entry p = get(number);
		if (p == null) return -1;
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("number", number);
		map.put("position", p.getPosition());
		map.put("status", 0);
		JSONObject jo = JSONObject.fromObject(map);
		String url = Values.DOMAIN + "Entry/" + p.getId();
		String resultXML = HttpHelper.SendHttpRequest("put", url, jo.toString());
		System.out.println(resultXML);
		return 0;
	}
}
