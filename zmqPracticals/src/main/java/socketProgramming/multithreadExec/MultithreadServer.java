package socketProgramming.multithreadExec;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class MultithreadServer {

    // Define the port where the server will listen
    public static final int PORT = 12345;

    public static void main(String[] args) {
        try {
            // Create a ServerSocket to listen on the specified port
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server is listening on port " + PORT);

            // Thread pool to handle clients
            ExecutorService threadPool = Executors.newFixedThreadPool(10);

            while (true) {
                // Accept a new client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // Handle the client in a new thread from the thread pool
                threadPool.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            // Input and output streams to read from and write to the client
        	DataInputStream in = new DataInputStream(clientSocket.getInputStream());
        	String clientMessage;
            while ((clientMessage = in.readUTF()) != null) {
                System.out.println("Received from client: " + clientMessage);
                // Simulate processing delay by adding a sleep
                try {
                    Thread.sleep(1000); // Introduce a 100ms delay for each message
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        	
            // Close the client connection
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
