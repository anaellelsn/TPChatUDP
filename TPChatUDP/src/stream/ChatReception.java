package stream;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ChatReception extends Thread {

	private MulticastSocket s;
	
	
	public ChatReception(MulticastSocket s){
		this.s=s;
	}
	
	public void run() {
		try {
			String line;
			while(true) {
				byte[] buf = new byte[1000];
				
				
				
				DatagramPacket recv = new
						DatagramPacket(buf, buf.length);
				
				s.receive(recv);
				
				String msg = new String(recv.getData(), 0,
						recv.getLength());
				
				System.out.println(msg);
				//System.out.println(buf.toString());
			}
		}catch(Exception e) {
	    	System.err.println("EnvoiThread:" + e);
	    }
	}
	
	
}
