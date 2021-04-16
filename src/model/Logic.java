package model;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Logic {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public String getIp(){
		
        try {
        	InetAddress address = InetAddress.getLocalHost();
        	
            return address.getHostAddress();
        } catch (UnknownHostException e) {
            return "No se obtuvo la ip";
        }
    }

}
