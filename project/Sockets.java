import java.io.*;
import java.net.*;
import java.util.*;

public class Sockets {
	private int portID;
	private Set<String> usernames = new HashSet();
	private Set<String> threads = new HashSet();
	
	public Sockets(int port) {
		this.portID = portID;
	}
	
	public void execute() {
		try (ServerSocket ss = new ServerSocket(portID)) {
			System.out.println("Chat is listening on port ID: " + portID);
			
			while (true) {
				Socket sock = ss.accept();
				
				System.out.println("New user connected");
				
				UserThread newUser = new UserThread(sock, this);
				threads.add(newUser);
				newUser.start();
			}
		} catch (IOException ex) {
			System.out.println("Error on the server: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Please try this syntax: java Sockets <port num>");
			System.exit(0);
		}
		
		int portID = Integer.parseInt(args[0]);
		
		Sockets server = new Sockets(portID);
		server.execute();
	}
	
	void broadcast(String message, UserThread user1) {
		for (UserThread user2: threads) {
			if (user2 != user1)
				user2.sendMessage(message);
		}
	}
	
	void addUsername(String user) {
		usernames.add(user);
	}
	
	void removeUser(String userName, UserThread user) {
		boolean remove = usernames.remove(userName);
		
		if (remove) {
			threads.remove(user);
			System.out.println(userName + " quitted");
		}
	}
	
	Set<String> getUsernames() {
		return this.usernames;
	}
	
	boolean hasUsers() {
		return !this.usernames.isEmpty();
	}
}
