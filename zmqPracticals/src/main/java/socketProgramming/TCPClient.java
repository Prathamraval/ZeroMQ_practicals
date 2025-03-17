package socketProgramming;
import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) {
        String serverIP = "localhost"; // Change to match the server's IP
        int port = 5000;

        try {
            Socket socket = new Socket(serverIP, port);
            System.out.println("Connected to server at " + serverIP + ":" + port);

            // Send data to server
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("Hello from Client!");

            // Read response from server
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = reader.readLine();
            System.out.println("Server says: " + response);

            // Close the socket
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
