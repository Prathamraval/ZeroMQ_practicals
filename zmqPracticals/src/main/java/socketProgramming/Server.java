package socketProgramming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws IOException, InterruptedException {

		System.out.println("Server is started");

		ServerSocket ss = new ServerSocket(5050);
		Socket s = ss.accept();
        System.out.println("New client connected: " + s.getInetAddress() + ":" + s.getPort());

		System.out.println("Client is connected");

		BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		String str = br.readLine();
		System.out.println("Client data recieved: " + str);
		Thread.currentThread().sleep(10000);

	}
}
