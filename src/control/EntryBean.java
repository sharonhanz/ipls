package control;

import java.util.List;

import model.Entry;
import model.Values;
import util.HttpHelper;
import util.XMLParser;

public class EntryBean {
	public boolean isExist(String entryNumber)
	{
		boolean ret = false;
		String url = Values.DOMAIN + "Entry/?Entry.number=" + entryNumber;
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Entry> us = Entry.parseXML(resultXML);
		if (us.size() > 0)
			ret = true;
		return ret;
	}
	
	public Entry get(String number) {
		String url = Values.DOMAIN + "Entry/?Entry.number=" + number;
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Entry> us = Entry.parseXML(resultXML);
		if (us.size() == 0) return null;
		return us.get(0);
	}
	
	public List<Entry> getAll()
	{
		String url = Values.DOMAIN + "Entry/";
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Entry> ret = Entry.parseXML(resultXML);
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
		String url = Values.DOMAIN + "Entry/";
		String resultXML = HttpHelper.SendHttpRequest("post", url, xmlBody);
		System.out.println(resultXML);
		return 0;
	}
	
	public int open(String number) {
		Entry p = get(number);
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
		Entry p = get(number);
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
