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
 
        String userName = console.readLine("\nPlease enter your name: ");
        chatClient.setUserName(userName);
        printwriter.println(userName);
 
        String text;
        String test = "peace";
 
        do {
            text = console.readLine("[" + userName + "]: ");
            printwriter.println(text);
            text = text.toLowerCase();
 
        } while (!(text.equals("peace")));
 
        try {
            socket.close();
        } catch (IOException ex) {
 
            System.out.println("There is an writing to the server: " + ex.getMessage());
        }
    }
}
