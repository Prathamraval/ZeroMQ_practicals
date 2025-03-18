package socketProgramming;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Socket s = new Socket("",5050);
		
		OutputStreamWriter os = new OutputStreamWriter(s.getOutputStream());
		os.write("this is from client");
		
		//other way 
//		OutputStream outputStream = socket.getOutputStream();
//		DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
//		dataOutputStream.writeUTF("Hello, Server!");

		os.flush();
	}
}
