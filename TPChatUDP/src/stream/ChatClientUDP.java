package stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class ChatClientUDP extends Thread {

	
	private MulticastSocket s;
	
	private InetAddress groupAddr;
	
	private int groupPort;
	
	private BufferedReader stdIn;
	
	private String pseudo;
	
	private ChatEnvoie envoie;
	
	private ChatReception reception;
	
	/**
	 * Create a new client chat using UDP
	 * @param host
	 * @param port
	 */
	
	public ChatClientUDP (String host, int port) {
		try {
			groupAddr = InetAddress.getByName(host);
			groupPort = port;
			s = new MulticastSocket(groupPort);
			// Join the group
		    s.joinGroup(groupAddr);
		    
		    stdIn = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("Veuillez entrer le pseudo vous identifiant sur le chat :");
			String line=stdIn.readLine();
			while (line==null) line=stdIn.readLine();
			pseudo=line;
			System.out.println("Vous pouvez commencer l'echange (pour quitter ecrivez 'quitter') :");
			
		    envoie = new ChatEnvoie(this,pseudo,s,groupAddr,groupPort);
		    reception = new ChatReception(pseudo,s);
		    
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

	   ChatClientUDP c = new ChatClientUDP("228.5.6.7",6789);
	        
	   c.start();
 
    }
	
	/**
	 * Closing method used to leave the chat
	 */
	public void close() {
		try {
			s.leaveGroup(groupAddr);
			envoie.stop();
			reception.stop();
			s.close();
			this.stop();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
