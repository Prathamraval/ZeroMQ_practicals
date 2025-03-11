package pub_sub;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

public class ZMQPublisher {
    public static void main(String[] args) throws InterruptedException {
        try (ZContext context = new ZContext()) {
            Socket publisher = context.createSocket(ZMQ.PUB);
            publisher.bind("tcp://*:5556");

            while (true) {
                String message = "Weather Update: Sunny 25°C";
                System.out.println("Publishing: " + message);
                publisher.send(message);
                Thread.sleep(2000); // Simulate delay
            }
        }
    }
}
