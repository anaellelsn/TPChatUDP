package stream;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ChatEnvoie extends Thread {

	private MulticastSocket s;
	
	private InetAddress groupAddr;
	
	private int groupPort;
	
	private BufferedReader stdIn;
	
	public ChatEnvoie(MulticastSocket s,InetAddress groupAddr,int groupPort){
		
		this.s=s;
		this.groupAddr=groupAddr;
		this.groupPort=groupPort;
		
		stdIn = new BufferedReader(new InputStreamReader(System.in));
		
	}
	
	public void run() {
		try {
			String line;
			while(true) {
				line=stdIn.readLine();
				System.out.println("me : "+line);
				DatagramPacket hi = new DatagramPacket(line.getBytes(),line.length(), groupAddr, groupPort);
				s.send(hi);
			}
		}catch(Exception e) {
	    	System.err.println("EnvoiThread:" + e);
	    }
	}
	
}
