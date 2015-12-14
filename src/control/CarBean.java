package control;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.Car;
import model.Values;
import model.Values.Privilege;
import util.HttpHelper;
import util.XMLParser;

public class CarBean {
	
	public boolean isExist(String carNumber)
	{
		boolean ret = false;
		String url = Values.DOMAIN + "Car/?Car.number=" + carNumber;
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Car> us = Car.parseXML(resultXML);
		if (us.size() > 0)
			ret = true;
		return ret;
	}
	
	public Car get(String number) {
		String url = Values.DOMAIN + "Car/?Car.number=" + number;
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Car> us = Car.parseXML(resultXML);
		if (us.size() == 0) return null;
		return us.get(0);
	}
	
	public List<Car> getAll()
	{
		String url = Values.DOMAIN + "Car/";
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Car> ret = Car.parseXML(resultXML);
		return ret;
	}
	
	public int add(String number, Privilege privilege, Integer size)
	{
		if (isExist(number))
			return 1;
		XMLParser xp = new XMLParser("post");
		xp.add("set", "this.number", number);
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.nnnnnn");
		Date registerTime = new Date();
		xp.add("set", "this.registerTime", fmt.format(registerTime));
		xp.add("set", "this.size", size.toString());
		xp.add("set", "this.privilege", String.valueOf(privilege.ordinal()));
		if (privilege == Privilege.Commen) {
			registerTime.setYear(registerTime.getYear() + 2);
			xp.add("set", "this.expireTime", fmt.format(registerTime));
		} else if (privilege == Privilege.Internal) {
			registerTime.setYear(registerTime.getYear() + 3);
			xp.add("set", "this.expireTime", fmt.format(registerTime));
		} else {
			registerTime.setYear(9999);
			xp.add("set", "this.expireTime", fmt.format(registerTime));
		}
		BalanceBean bBean = new BalanceBean();
		bBean.add(number, 100.00);
		String xmlBody = xp.getXML();
		String url = Values.DOMAIN + "Car/";
		String resultXML = HttpHelper.SendHttpRequest("post", url, xmlBody);
		System.out.println(resultXML);
		return 0;
	}
}
