package control;

import java.util.List;

import model.Exit;
import model.Values;
import util.HttpHelper;
import util.XMLParser;

public class ExitBean {
	public boolean isExist(String exitNumber)
	{
		boolean ret = false;
		String url = Values.DOMAIN + "Exit/?Exit.number=" + exitNumber;
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Exit> us = Exit.parseXML(resultXML);
		if (us.size() > 0)
			ret = true;
		return ret;
	}
	
	public Exit get(String number) {
		String url = Values.DOMAIN + "Exit/?Exit.number=" + number;
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Exit> us = Exit.parseXML(resultXML);
		if (us.size() == 0) return null;
		return us.get(0);
	}
	
	public List<Exit> getAll()
	{
		String url = Values.DOMAIN + "Exit/";
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Exit> ret = Exit.parseXML(resultXML);
		return ret;
	}
	
	public int add(String number, String position, boolean status)
	{
		if (isExist(number))
			return 1;
		XMLParser xp = new XMLParser("post");
		xp.add("set", "this.number", number);
		if (status) {
			xp.add("set", "this.status", "1");
		} else {
			xp.add("set", "this.status", "0");
		}
		xp.add("set", "this.position", position);
		String xmlBody = xp.getXML();
		String url = Values.DOMAIN + "Exit/";
		String resultXML = HttpHelper.SendHttpRequest("post", url, xmlBody);
		System.out.println(resultXML);
		return 0;
	}
	
	public int open(String number) {
		Exit p = get(number);
		if (p == null) return 1;
		XMLParser xp = new XMLParser("put");
		xp.add("set", "this.status", "1");
		String xmlBody = xp.getXML();
		String url = Values.DOMAIN + p.getUri();
		String resultXML = HttpHelper.SendHttpRequest("put", url, xmlBody);
		System.out.println(resultXML);
		return 0;
	}
	
	public int close(String number) {
		Exit p = get(number);
		if (p == null) return 1;
		XMLParser xp = new XMLParser("put");
		xp.add("set", "this.status", "0");
		String xmlBody = xp.getXML();
		String url = Values.DOMAIN + p.getUri();
		String resultXML = HttpHelper.SendHttpRequest("put", url, xmlBody);
		System.out.println(resultXML);
		return 0;
	}
}
