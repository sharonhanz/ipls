package control;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.Car;
import model.Park;
import model.Values;
import util.HttpHelper;
import util.XMLParser;

public class ParkBean {
	
	public Park get(String carNumber) {
		String url = Values.DOMAIN + "Park/?Park.carnumber=" + carNumber + "Park.left = 0";
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Park> us = Park.parseXML(resultXML);
		if (us.size() == 0) return null;
		return us.get(0);
	}
	
	public List<Park> getAll()
	{
		String url = Values.DOMAIN + "Park/";
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Park> ret = Park.parseXML(resultXML);
		return ret;
	}
	
	public List<Park> getAllIn()
	{
		String url = Values.DOMAIN + "Park/?Park.left=0";
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Park> ret = Park.parseXML(resultXML);
		return ret;
	}
	
	public int add(String carNumber, String placeNumber, String entryNumber, String exitNumber, boolean left)
	{
		XMLParser xp = new XMLParser("post");
		xp.add("set", "this.carnumber", carNumber);
		xp.add("set", "this.placenumber", placeNumber);
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.nnnnnn");
		Date startTime = new Date();
		xp.add("set", "this.startTime", fmt.format(startTime));
		xp.add("set", "this.entryNumber", entryNumber);
		if (left) {
			Date endTime = new Date();
			xp.add("set", "this.endTime", fmt.format(endTime));
			xp.add("set", "this.exitNumber", exitNumber);
			xp.add("set", "this.fee", charge(carNumber, endTime.getTime()-startTime.getTime()).toString());
			xp.add("set", "this.left", "1");
		} else {
			xp.add("set", "this.left", "0");
		}
		String xmlBody = xp.getXML();
		String url = Values.DOMAIN + "Park/";
		String resultXML = HttpHelper.SendHttpRequest("post", url, xmlBody);
		System.out.println(resultXML);
		return 0;
	}
	
	private Double charge(String carNumber, long time) {
		Double fee = null;
		long min = time/60000;
		CarBean cBean = new CarBean();
		Car car = cBean.get(carNumber);
		fee = min * Values.PRICEPERMIN * Values.Ratio[car.getPrivilege().ordinal()];
		BalanceBean bBean = new BalanceBean();
		bBean.pay(carNumber, fee);
		return fee;
	}
	
	public int leave(String carNumber, String exitNumber) {
		Park park = get(carNumber);
		XMLParser xp = new XMLParser("put");
		Date endTime = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.nnnnnn");
		xp.add("set", "this.endTime", fmt.format(endTime));
		xp.add("set", "this.exitNumber", exitNumber);
		xp.add("set", "this.fee", charge(carNumber, endTime.getTime()-park.getStartTime().getTime()).toString());
		xp.add("set", "this.left", "1");
		String xmlBody = xp.getXML();
		String url = Values.DOMAIN + "Park/";
		String resultXML = HttpHelper.SendHttpRequest("put", url, xmlBody);
		System.out.println(resultXML);
		return 0;
	}
}
