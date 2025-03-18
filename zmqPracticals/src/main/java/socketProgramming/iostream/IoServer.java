package socketProgramming.iostream;

import java.io.*;
import java.net.*;

public class IoServer {
    public static void main(String[] args) {
        try {
            // Create server socket to listen for connections on port 5050
            ServerSocket serverSocket = new ServerSocket(5050);
            System.out.println("serversocket: "+serverSocket);
            Socket socket = serverSocket.accept();  // Accept client connection
            System.out.println("socket:"+socket);
            // Get the input stream to receive data from the client
            InputStream inputStream = socket.getInputStream();
            byte[] buffer = new byte[1024];  // Buffer to store incoming data
            
            int bytesRead = inputStream.read(buffer);  // Read data from client
            String receivedMessage = new String(buffer, 0, bytesRead);  // Convert bytes to string
            
            System.out.println("Received from client: " + receivedMessage);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
