 
import java.io.*;
import java.net.*;
import java.util.*;
 
public class ChatServer {
	// declare variables 
    private int port;
    
    private Set<String> userNames = new HashSet<>();
    private Set<UserThread> userThreads = new HashSet<>();
 
    public ChatServer(int port) {
        this.port = port;
    }
 
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
 
        	// let user know the chat server is now ready 
            System.out.println("The Chat Server is now listening on the port " + port);
 
            while (true) {
                Socket socket = serverSocket.accept();
                // let user know a new user has been added 
                System.out.println("A new user has connected to the server");
 
                UserThread newUser = new UserThread(socket, this);
                userThreads.add(newUser);
                newUser.start();
 
            }
 
            // throw errors if neccesary 
        } catch (IOException ex) {
            System.out.println("There is an error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    // main method 
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntax: java ChatServer <port-number>");
            System.exit(0);
        }
 
        int port = Integer.parseInt(args[0]);
 
        ChatServer server = new ChatServer(port);
        server.run();
    }
 
    
    void display(String message, UserThread excludeUser) {
        for (UserThread aUser : userThreads) {
            if (aUser != excludeUser) {
                aUser.send(message);
            }
        }
    }
 
   
    // stores username of a new user who has connected 
    void addUserName(String userName) {
        userNames.add(userName);
    }
 
   
    // when a user leaves, remove the username and userThread
    void removeTheUser(String userName, UserThread aUser) {
        boolean removed = userNames.remove(userName);
        if (removed) {
            userThreads.remove(aUser);
            System.out.println("The user " + userName + " has left");
        }
    }
 
    Set<String> getUserNames() {
        return this.userNames;
    }
 
    
    boolean hasUsers() {
        return !this.userNames.isEmpty();
    }
}
