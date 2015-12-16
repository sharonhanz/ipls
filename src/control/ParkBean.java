package control;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.Car;
import model.Park;
import model.Values;
import net.sf.json.JSONObject;
import util.HttpHelper;
import util.XMLParser;

public class ParkBean {
	
	public Park get(String carNumber) {
		String url = Values.DOMAIN + "Park/?Park.carnumber=" + carNumber + "&Park.left=0";
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Park> us = Park.parseJSON(resultXML);
		if (us.size() == 0) return null;
		return us.get(0);
	}
	
	public List<Park> getAll()
	{
		String url = Values.DOMAIN + "Park/";
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Park> ret = Park.parseJSON(resultXML);
		return ret;
	}
	
	public List<Park> getAllIn()
	{
		String url = Values.DOMAIN + "Park/?Park.left=0";
		String resultXML = HttpHelper.SendHttpRequest("get", url, null);
		List<Park> ret = Park.parseJSON(resultXML);
		return ret;
	}
	
	public int add(String carNumber, String placeNumber, String entryNumber, String exitNumber, boolean left)
	{
		if (get(carNumber) != null) return -1;
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("carnumber", carNumber);
		map.put("placenumber", placeNumber);
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		Date startTime = new Date();
		map.put("starttime", fmt.format(startTime));
		map.put("entrynumber", entryNumber);
		if (left) {
			Date endTime = new Date();
			map.put("endtime", fmt.format(endTime));
			map.put("exitnumber", exitNumber);
			map.put("fee", charge(carNumber, endTime.getTime()-startTime.getTime()));
			map.put("left", 1);
		} else {
			map.put("left", 0);
		}
		JSONObject jo = JSONObject.fromObject(map);
		String url = Values.DOMAIN + "Park/";
		String resultXML = HttpHelper.SendHttpRequest("post", url, jo.toString());
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
		Map<String, Object> map = new LinkedHashMap<>();
		Date endTime = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		map.put("carnumber", carNumber);
		map.put("placenumber", park.getPlaceNumber());
		park.setStartTime(fmt.format(endTime));
		map.put("starttime", fmt.format(park.getStartTime()));
		map.put("entrynumber", park.getEntryNumber());
		map.put("endtime", fmt.format(endTime));
		map.put("exitnumber", exitNumber);
		map.put("fee", charge(carNumber, endTime.getTime()-park.getStartTime().getTime()));
		map.put("left", 1);
		JSONObject jo = JSONObject.fromObject(map);
		String url = Values.DOMAIN + "Park/" + park.getId();
		String resultXML = HttpHelper.SendHttpRequest("put", url, jo.toString());
		System.out.println(resultXML);
		return 0;
	}
}
