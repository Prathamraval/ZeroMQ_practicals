package SnmpZmq;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

public class SnmpSub {
	public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            Socket subscriber = context.createSocket(ZMQ.SUB);
            subscriber.connect("tcp://localhost:5556");
            subscriber.subscribe(""); // Subscribe to all messages

            while (true) {
                String message = subscriber.recvStr();
                System.out.println("Received: " + message);
            }
        }
	}
}
