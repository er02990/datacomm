import java.io.*;
import java.net.*;
import java.util.*;
 

public class UserThread extends Thread {
    private Socket socket;
    private ChatServer server;
    private PrintWriter writer;
 
    public UserThread(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }
 
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
 
            printUsers();
 
            String userName = reader.readLine();
            server.addUserName(userName);
 
            String serverMessage = "A new user is now connected: " + userName;
            server.display(serverMessage, this);
 
            String clientMessage;
 
            do {
                clientMessage = reader.readLine();
                serverMessage = "[" + userName + "]: " + clientMessage;
                server.display(serverMessage, this);
 
            } while (!clientMessage.equals("peace"));
 
            server.removeTheUser(userName, this);
            socket.close();
 
            serverMessage = userName + " has left.";
            server.display(serverMessage, this);
 
        } catch (IOException ex) {
            System.out.println("There is an error in UserThread: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
   
    void printUsers() {
        if (server.hasUsers()) {
            writer.println("Connected users: " + server.getUserNames());
        } else {
            writer.println("No other users connected");
        }
    }
 
   
    void send(String message) {
        writer.println(message);
    }
}
