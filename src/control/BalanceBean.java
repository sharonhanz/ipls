package control;

import java.util.List;

import model.Balance;
import model.Exit;
import model.Values;
import util.HttpHelper;
import util.XMLParser;

public class BalanceBean {
	public boolean isExist(String carNumber)
	{
		boolean ret = false;
		String url = Values.DOMAIN + "Balance/?Balance.carnumber=" + carNumber;
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Exit> us = Exit.parseXML(resultXML);
		if (us.size() > 0)
			ret = true;
		return ret;
	}
	
	public Balance get(String carNumber) {
		String url = Values.DOMAIN + "Balance/?Balance.carnumber=" + carNumber;
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Balance> us = Balance.parseXML(resultXML);
		if (us.size() == 0) return null;
		return us.get(0);
	}
	
	public List<Balance> getAll()
	{
		String url = Values.DOMAIN + "Balance/";
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Balance> ret = Balance.parseXML(resultXML);
		return ret;
	}
	
	int add(String number, Double balance)
	{
		if (isExist(number))
			return 1;
		XMLParser xp = new XMLParser("post");
		xp.add("set", "this.carnumber", number);
		xp.add("set", "this.balance", balance.toString());
		String xmlBody = xp.getXML();
		String url = Values.DOMAIN + "Balance/";
		String resultXML = HttpHelper.SendHttpRequest("post", url, xmlBody);
		System.out.println(resultXML);
		return 0;
	}
	
	public int gain(String number, Double fee) {
		Balance b = get(number);
		if (b == null) return 1;
		XMLParser xp = new XMLParser("put");
		xp.add("set", "this.balance", String.valueOf((b.getBalance()+fee)));
		String xmlBody = xp.getXML();
		String url = Values.DOMAIN + b.getUri();
		String resultXML = HttpHelper.SendHttpRequest("put", url, xmlBody);
		System.out.println(resultXML);
		return 0;
	}
	
	public int pay(String number, Double fee) {
		Balance b = get(number);
		if (b == null) return 1;
		XMLParser xp = new XMLParser("put");
		xp.add("set", "this.balance", String.valueOf((b.getBalance()-fee)));
		String xmlBody = xp.getXML();
		String url = Values.DOMAIN + b.getUri();
		String resultXML = HttpHelper.SendHttpRequest("put", url, xmlBody);
		System.out.println(resultXML);
		return 0;
	}
}
