package socketProgramming.chatApp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.net.ServerSocket;

public class Server {
	Socket socket;
	ServerSocket serverSocket;
	DataOutputStream dataOutputStream;
	DataInputStream dataInputStream;

	public void listen() {
		try {
			serverSocket = new ServerSocket(5050);
			socket = serverSocket.accept();
			System.out.println("server is listening at port 5050 .....");
			OutputStream outputStream = socket.getOutputStream();
			dataOutputStream = new DataOutputStream(outputStream);
			InputStream inputStream = socket.getInputStream();
			dataInputStream = new DataInputStream(inputStream);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void sendMessage() {
		while (true) {
			Scanner sc = new Scanner(System.in);
			String cmd = sc.nextLine();
			if (cmd.equals("exit")) {
				closeConnection();
				break;
			}
			try {
				dataOutputStream.writeUTF(cmd);
				dataOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void recMessage() {
		while (true) {
			try {
				String receivedMessage = dataInputStream.readUTF();
				System.out.println("Server: " + receivedMessage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void closeConnection() {
		try {
			socket.close();
		} catch (Exception e) {
			System.out.println("Connection closed...");
		}
	}

	public static void main(String[] args) {
		Client client = new Client();
		client.createConnection();

		// Thread for receiving messages from the server
		Thread receiveThread = new Thread(new Runnable() {
			@Override
			public void run() {
				client.recMessage();
			}
		});
		receiveThread.start();

		// Send messages from user input
		client.sendMessage();

	}
}
