package socketProgramming.multithreadExec;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class MultithreadServer {

    public static final int PORT = 5050;
    private static final List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        try {

        	ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server is listening on port " + PORT);

            ExecutorService threadPool = Executors.newFixedThreadPool(2);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);

                // Submit the ClientHandler to the thread pool
                threadPool.submit(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to broadcast messages to all connected clients
    public static void broadcastMessage(String message, ClientHandler sender) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client != sender) {
                    client.sendMessage(message);
                }
            }
        }
    }

    // ClientHandler class to handle client communication
    static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private DataOutputStream out;
        private DataInputStream in;
        private String clientName;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                // Setup input and output streams
                in = new DataInputStream(clientSocket.getInputStream());
                out = new DataOutputStream(clientSocket.getOutputStream());

                out.writeUTF("Enter your name: ");

                clientName = in.readUTF(); 
                System.out.println(clientName + " has joined the chat!");

                broadcastMessage(clientName + " has joined the chat.", this);

                String clientMessage;

                while (true) {
                    clientMessage = in.readUTF();

                    if (clientMessage.equals("exit")) {
                        break;
                    }
                    broadcastMessage(clientName + ": " + clientMessage, this);
                }

                System.out.println(clientName + " has disconnected.");
                broadcastMessage(clientName + " has left the chat.", this);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (clientSocket != null) {
                        clientSocket.close(); 
                    }
                    synchronized (clients) {
                        clients.remove(this);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Method to send messages to the client
        public void sendMessage(String message) {
            try {
                out.writeUTF(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
