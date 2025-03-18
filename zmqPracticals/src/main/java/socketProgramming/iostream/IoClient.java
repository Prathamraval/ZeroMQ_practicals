package socketProgramming.iostream;

import java.io.*;
import java.net.*;

public class IoClient {
    public static void main(String[] args) {
        try(Socket socket = new Socket("localhost", 5050)) {
            System.out.println("socket client :"+socket);
            // Get the output stream of the socket
            OutputStream outputStream = socket.getOutputStream();
            
            // Send data as raw bytes
            byte[] message = "Hello from client".getBytes();  // Convert string to bytes
            outputStream.write(message);
            outputStream.flush();  // Ensure data is sent immediately
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
