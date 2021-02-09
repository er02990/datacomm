import java.io.*;
import java.net.*;

 

public class ChatClient {
	// declare variables 
	private String userName;
    private String host;
    private int port;
    
 
    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
 
    public void run() {
        try {
            Socket socket = new Socket(host, port);
 
            // let the user know they are now connected to the server itself 
            System.out.println("You are now connected to the chat server");
 
            new ReadThread(socket, this).start();
            new WriteThread(socket, this).start();
 
            // throw errors if neccesary 
        } catch (UnknownHostException ex) {
            System.out.println("Error: The server was not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }
 
    }
 
    void setUserName(String userName) {
        this.userName = userName;   
    }
 
    // retrieving the name of the user 
    String getUserName() {
        return this.userName;
    }
 
 
    // main method 
    public static void main(String[] args) {
        if (args.length < 2) return;
 
        String host = args[0];
        int port = Integer.parseInt(args[1]);
 
        ChatClient client = new ChatClient(host, port);
        client.run();
    }
}
