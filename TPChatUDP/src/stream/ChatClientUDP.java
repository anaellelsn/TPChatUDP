package stream;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class ChatClientUDP extends Thread {

	private MulticastSocket s;
	
	private InetAddress groupAddr;
	
	private int groupPort;
	
	private ChatEnvoie envoie;
	
	private ChatReception reception;
	
	public ChatClientUDP () {
		try {
			groupAddr = InetAddress.getByName("228.5.6.7");
			groupPort = 6789;
			s = new MulticastSocket(groupPort);
			// Join the group
		    s.joinGroup(groupAddr);
		    
		    envoie = new ChatEnvoie(s,groupAddr,groupPort);
		    reception = new ChatReception(s);
		    
		    envoie.start();
		    reception.start();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	/**
	*  main method
	*  accepts a connection, receives a message from client then sends an echo to the client
	**/
	public static void main(String[] args) throws IOException {

	   ChatClientUDP c = new ChatClientUDP();
	        
	   c.start();
 
    }
	
	public void close() {
		try {
			s.leaveGroup(groupAddr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
