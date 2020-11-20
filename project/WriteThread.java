
import java.io.*;
import java.net.*;
 
public class WriteThread extends Thread {
    private Printprintwriter printwriter;
    private Socket socket;
    private ChatchatClient chatClient;
 
    public WriteThread(Socket socket, ChatchatClient chatClient) {
        this.socket = socket;
        this.chatClient = chatClient;
 
        try {
            OutputStream output = socket.getOutputStream();
            printwriter = new Printprintwriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    public void run() {
 
        Console console = System.console();
 
        String userName = console.readLine("\nEnter your name: ");
        chatClient.setUserName(userName);
        printwriter.println(userName);
 
        String text;
 
        do {
            text = console.readLine("[" + userName + "]: ");
            printwriter.println(text);
 
        } while (!text.equals("bye"));
 
        try {
            socket.close();
        } catch (IOException ex) {
 
            System.out.println("Error writing to server: " + ex.getMessage());
        }
    }
}
