
import java.io.*;
import java.net.*;
 
public class WriteThread extends Thread {
    private PrintWriter printwriter;
    private Socket socket;
    private ChatClient chatClient;
 
    public WriteThread(Socket socket, ChatClient chatClient) {
        this.socket = socket;
        this.chatClient = chatClient;
 
        try {
            OutputStream output = socket.getOutputStream();
            printwriter = new PrintWriter(output, true);
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
