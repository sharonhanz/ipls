package control;

import java.util.List;

import model.Place;
import model.Values;
import model.Values.Privilege;
import util.HttpHelper;
import util.XMLParser;

public class PlaceBean {
	
	public boolean isExist(String placeNumber)
	{
		boolean ret = false;
		String url = Values.DOMAIN + "Place/?Place.number=" + placeNumber;
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Place> us = Place.parseXML(resultXML);
		if (us.size() > 0)
			ret = true;
		return ret;
	}
	
	public Place get(String number) {
		String url = Values.DOMAIN + "Place/?Place.number=" + number;
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Place> us = Place.parseXML(resultXML);
		if (us.size() == 0) return null;
		return us.get(0);
	}
	
	public List<Place> getAll()
	{
		String url = Values.DOMAIN + "Place/";
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Place> ret = Place.parseXML(resultXML);
		return ret;
	}
	
	public List<Place> getAllEmpty() {
		String url = Values.DOMAIN + "Place/?Place.occupied=0";
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Place> ret = Place.parseXML(resultXML);
		return ret;
	}
	
	public List<Place> getAllOccupied() {
		String url = Values.DOMAIN + "Place/?Place.occupied=1";
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Place> ret = Place.parseXML(resultXML);
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
		List<Place> ret = Place.parseXML(resultXML);
		return ret;
	}
	
	public int add(String number,Integer size, Privilege privilege, boolean occupied, String position)
	{
		if (isExist(number))
			return 1;
		XMLParser xp = new XMLParser("post");
		xp.add("set", "this.number", number);
		xp.add("set", "this.size", size.toString());
		xp.add("set", "this.privilege", String.valueOf(privilege.ordinal()));
		if (occupied) {
			xp.add("set", "this.occupied", "1");
		} else {
			xp.add("set", "this.occupied", "0");
		}
		xp.add("set", "this.position", position);
		String xmlBody = xp.getXML();
		String url = Values.DOMAIN + "Place/";
		String resultXML = HttpHelper.SendHttpRequest("post", url, xmlBody);
		System.out.println(resultXML);
		return 0;
	}
	
	public int occupy(String number) {
		Place p = get(number);
		if (p == null) return 1;
		XMLParser xp = new XMLParser("put");
		xp.add("set", "this.occupied", "1");
		String xmlBody = xp.getXML();
		String url = Values.DOMAIN + p.getUri();
		String resultXML = HttpHelper.SendHttpRequest("put", url, xmlBody);
		System.out.println(resultXML);
		return 0;
	}
	
	public int free(String number) {
		Place p = get(number);
		if (p == null) return 1;
		XMLParser xp = new XMLParser("put");
		xp.add("set", "this.occupied", "0");
		String xmlBody = xp.getXML();
		String url = Values.DOMAIN + p.getUri();
		String resultXML = HttpHelper.SendHttpRequest("put", url, xmlBody);
		System.out.println(resultXML);
		return 0;
	}
}
