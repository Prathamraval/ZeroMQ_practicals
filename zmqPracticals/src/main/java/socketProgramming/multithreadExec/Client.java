package socketProgramming.multithreadExec;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Socket s = new Socket("",12345);
		
		OutputStream outputStream = s.getOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
		int i=0;
		while(i<100000000) {
			System.out.println("from clinet :"+i);
			dataOutputStream.writeUTF ("this is from client "+i);
			i++;
		}
	}
}
