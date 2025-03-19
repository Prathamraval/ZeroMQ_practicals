package socketProgramming.multithreadExec;

import java.io.*;
import java.net.*;

public class Client {
    private static DataOutputStream out;
    private static DataInputStream in;

    public static void main(String[] args) {
        try {
            // Connect to the server
            Socket socket = new Socket("localhost", 5050);
            System.out.println("Connected to the server");

            // Setup input and output streams for this client
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            // Create a thread to listen for incoming messages from the server

            // Send messages to the server
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            // Receive and display the "Enter your name" prompt from the server
            System.out.println(in.readUTF()); // Read the prompt for the client name

            // Send the client's name to the server after receiving the prompt
            out.writeUTF(userInput.readLine()); // You can replace "pratham" with user input if needed

            Thread listenerThread = new Thread(new MessageListener());
            listenerThread.start();

            String message;
            while ((message = userInput.readLine()) != null) {
                out.writeUTF(message); // Send message to the server
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // MessageListener class to handle incoming messages from the server
    static class MessageListener implements Runnable {
        @Override
        public void run() {
            try {
                String message;
                while ((message = in.readUTF()) != null) {
                    System.out.println(message); // Print received message to the console
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }	
    }
}
