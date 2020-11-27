package stream;

/**
 * @author Anaelle Lesne & Agathe Liguori
 * 
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ChatEnvoie extends Thread {

	private MulticastSocket s;
	
	private InetAddress groupAddr;
	
	private String pseudo;
	
	private ChatClientUDP chat;
	
	private int groupPort;
	
	private BufferedReader stdIn;
	
	/**
	 * Creates a thread to send messages 
	 * @param chat
	 * @param pseudo
	 * @param s
	 * @param groupAddr
	 * @param groupPort
	 */
	
	public ChatEnvoie(ChatClientUDP chat,String pseudo,MulticastSocket s,InetAddress groupAddr,int groupPort){
		this.chat=chat;
		this.pseudo=pseudo;
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
				if(line!=null) {
					if(line.contentEquals("quitter")){
						line = pseudo+" a quitté le chat.";
						DatagramPacket hi = new DatagramPacket(line.getBytes(),line.length(), groupAddr, groupPort);
						s.send(hi);
						chat.close();
						
						
					}else {
						System.out.println("me : "+line);
						line = "De "+pseudo+" : "+line;
						DatagramPacket hi = new DatagramPacket(line.getBytes(),line.length(), groupAddr, groupPort);
						s.send(hi);
					}
				}
				
			}
		}catch(Exception e) {
	    	System.err.println("EnvoiThread:" + e);
	    }
	}
	
}
