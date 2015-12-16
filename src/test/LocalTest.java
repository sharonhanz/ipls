package test;

import org.apache.tomcat.jni.Time;

import control.BalanceBean;
import control.CarBean;
import control.EntryBean;
import control.ExitBean;
import control.ParkBean;
import control.PlaceBean;
import model.Car;
import model.Values.Privilege;

public class LocalTest  {
		public static void main(String[] args) {
			CarBean cb = new CarBean();
			cb.add("CA123", Privilege.Commen, 1);
			cb.add("AB321", Privilege.Internal, 3);
			cb.add("YU272", Privilege.VIP, 2);
			cb.add("HA090", Privilege.Commen, 2);
			cb.add("WW999", Privilege.Commen, 2);
			if (cb.isExist("CA123")) {
				Car c = cb.getAll().get(3);
				System.out.print(c.getNumber() + c.getSize() + c.getExpireTime() + c.getPrivilege() + c.getRegisterTime());
			}
			
			/*BalanceBean bb = new BalanceBean();
			bb.pay("CA123", 200.00);
			bb.gain("AB321", 100.00);
			bb.gain("YU272", 10.00);
			bb.pay("HA090", 3.00);
			bb.pay("WW999", 15.00);*/
			
			ExitBean eb = new ExitBean();
			eb.add("B1", "W1S1", false);
			eb.add("B2", "E2S2", true);
			eb.add("B3", "W1N1", false);
			if (eb.get("B1").getStatus())
				eb.close("B1");
			else eb.open("B1");
			
			PlaceBean pb = new PlaceBean();
			pb.add("A1001", 3, Privilege.Commen, false, "B1W1");
			
			ParkBean pkb = new ParkBean();
			pkb.add("CA123", "A1001", "A1", "", false);
			//Time.sleep(1000);
			pkb.leave("CA123", "B1");
		}
}
