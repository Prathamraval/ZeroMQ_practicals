package socketProgramming;
import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) {
        int port = 5000; // Port to listen on
        String serverIP = "10.20.41.5"; // Change this to your system's IP

        try {
            InetAddress bindAddr = InetAddress.getByName(serverIP);
            ServerSocket serverSocket = new ServerSocket(port, 50, bindAddr);

            System.out.println("Server is listening on " + serverIP + ":" + port);

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Accepts client connections
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Read data from client
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String message = reader.readLine();
                System.out.println("Client says: " + message);

                // Send response to client
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                writer.println("Hello from Server!");

                // Close client connection
                clientSocket.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
