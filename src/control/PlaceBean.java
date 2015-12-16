package control;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.Place;
import model.Values;
import model.Values.Privilege;
import net.sf.json.JSONObject;
import util.HttpHelper;

public class PlaceBean {
	
	public boolean isExist(String placeNumber)
	{
		boolean ret = false;
		String url = Values.DOMAIN + "Place/?Place.number=" + placeNumber;
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Place> us = Place.parseJSON(resultXML);
		if (us.size() > 0)
			ret = true;
		return ret;
	}
	
	public Place get(String number) {
		String url = Values.DOMAIN + "Place/?Place.number=" + number;
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Place> us = Place.parseJSON(resultXML);
		if (us.size() == 0) return null;
		return us.get(0);
	}
	
	public List<Place> getAll()
	{
		String url = Values.DOMAIN + "Place/";
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Place> ret = Place.parseJSON(resultXML);
		return ret;
	}
	
	public List<Place> getAllEmpty() {
		String url = Values.DOMAIN + "Place/?Place.occupied=0";
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Place> ret = Place.parseJSON(resultXML);
		return ret;
	}
	
	public List<Place> getAllOccupied() {
		String url = Values.DOMAIN + "Place/?Place.occupied=1";
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Place> ret = Place.parseJSON(resultXML);
		return ret;
	}
	
	public List<Place> getCertain(Privilege privilege, boolean occupied, int size) {
		String url = Values.DOMAIN + "Place/?Place.privilege=" + privilege.ordinal() + "&Place.size>" + size + "&Place.occupied=";
		if (occupied) {
			url = url.concat("1");
		} else {
			url = url.concat("0");
		}
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Place> ret = Place.parseJSON(resultXML);
		return ret;
	}
	
	public int add(String number,Integer size, Privilege privilege, boolean occupied, String position)
	{
		if (isExist(number))
			return -1;
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("number", number);
		map.put("size", size);
		map.put("privilege", privilege.ordinal());
		if (occupied) {
			map.put("occupied", 1);
		} else {
			map.put("occupied", 0);
		}
		map.put("position", position);
		JSONObject jo = JSONObject.fromObject(map);
		String url = Values.DOMAIN + "Place/";
		String resultXML = HttpHelper.SendHttpRequest("post", url, jo.toString());
		System.out.println(resultXML);
		return 0;
	}
	
	public int occupy(String number) {
		Place p = get(number);
		if (p == null) return -1;
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("number", number);
		map.put("size", p.getSize());
		map.put("privilege", p.getPrivilege().ordinal());
	    map.put("occupied", 1);
		map.put("position", p.getPosition());
		JSONObject jo = JSONObject.fromObject(map);
		String url = Values.DOMAIN + "Place/" + p.getId();
		String resultXML = HttpHelper.SendHttpRequest("put", url, jo.toString());
		System.out.println(resultXML);
		return 0;
	}
	
	public int free(String number) {
		Place p = get(number);
		if (p == null) return -1;
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("number", number);
		map.put("size", p.getSize());
		map.put("privilege", p.getPrivilege().ordinal());
	    map.put("occupied", 0);
		map.put("position", p.getPosition());
		JSONObject jo = JSONObject.fromObject(map);
		String url = Values.DOMAIN + "Place/" + p.getId();
		String resultXML = HttpHelper.SendHttpRequest("put", url, jo.toString());
		System.out.println(resultXML);
		return 0;
	}
}
