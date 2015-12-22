package control;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.Car;
import model.Values;
import model.Values.Privilege;
import net.sf.json.JSONArray;
import util.HttpHelper;

public class CarBean {
	
	public boolean isExist(String carNumber)
	{
		boolean ret = false;
		String url = Values.DOMAIN + "Car/?Car.number=" + carNumber;
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Car> us = Car.parseJSON(resultXML);
		if (us.size() > 0)
			ret = true;
		return ret;
	}

	private void addName() {
		String url = Values.DOMAIN + "Car/";
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Car> ret = Car.parseJSON(resultXML);
		int i = 0;
		for (Car c : ret) {
			c.setName(c.getNumber().substring(0, 1));
			c.setPhone("1381234" + Integer.toString(5678 + i));
			i++;
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("number", c.getNumber());
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			map.put("registertime", fmt.format(c.getRegisterTime()));
			map.put("expiretime", fmt.format(c.getExpireTime()));
			map.put("privilege", c.getPrivilege().ordinal());
			map.put("size", c.getSize());
			map.put("name", c.getName());
			map.put("phone", c.getPhone());
			JSONArray json = JSONArray.fromObject(map);
			String jstr = json.get(0).toString();
			String url2 = Values.DOMAIN + "Car/" + c.getId();
			String resultXML2 = HttpHelper.SendHttpRequest("put", url2, jstr);
			System.out.println(resultXML);
		}
	}
	
	public Car get(String number) {
		String url = Values.DOMAIN + "Car/?Car.number=" + number;
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Car> us = Car.parseJSON(resultXML);
		if (us.size() == 0) return null;
		return us.get(0);
	}
	
	public List<Car> getAll()
	{
		String url = Values.DOMAIN + "Car/";
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Car> ret = Car.parseJSON(resultXML);
		return ret;
	}
	
	public int add(String number, Privilege privilege, Integer size)
	{
		if (isExist(number))
			return -1;
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("number", number);
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		Date registerTime = new Date();
		map.put("registertime", fmt.format(registerTime));
		if (privilege == Privilege.Commen) {
			registerTime.setYear(registerTime.getYear() + 2);
			map.put("expiretime", fmt.format(registerTime));
		} else if (privilege == Privilege.Internal) {
			registerTime.setYear(registerTime.getYear() + 3);
			map.put("expiretime", fmt.format(registerTime));
		} else {
			registerTime.setYear(9999);
			map.put("expiretime", fmt.format(registerTime));
		}
		map.put("privilege", privilege.ordinal());
		map.put("size", size);
		JSONArray json = JSONArray.fromObject(map);
		String jstr = json.get(0).toString();
		String url = Values.DOMAIN + "Car/";
		String resultXML = HttpHelper.SendHttpRequest("post", url, jstr);
		System.out.println(resultXML);
		BalanceBean bb = new BalanceBean();
		bb.add(number, 00.00);
		return 0;
	}
	
	public int add(String number, Privilege privilege, Integer size, String name, String phone)
	{
		if (isExist(number))
			return -1;
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("number", number);
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		Date registerTime = new Date();
		map.put("registertime", fmt.format(registerTime));
		if (privilege == Privilege.Commen) {
			registerTime.setYear(registerTime.getYear() + 2);
			map.put("expiretime", fmt.format(registerTime));
		} else if (privilege == Privilege.Internal) {
			registerTime.setYear(registerTime.getYear() + 3);
			map.put("expiretime", fmt.format(registerTime));
		} else {
			registerTime.setYear(9999);
			map.put("expiretime", fmt.format(registerTime));
		}
		map.put("privilege", privilege.ordinal());
		map.put("size", size);
		map.put("name", name);
		map.put("phone", phone);
		JSONArray json = JSONArray.fromObject(map);
		String jstr = json.get(0).toString();
		String url = Values.DOMAIN + "Car/";
		String resultXML = HttpHelper.SendHttpRequest("post", url, jstr);
		System.out.println(resultXML);
		BalanceBean bb = new BalanceBean();
		bb.add(number, 00.00);
		return 0;
	}
}
