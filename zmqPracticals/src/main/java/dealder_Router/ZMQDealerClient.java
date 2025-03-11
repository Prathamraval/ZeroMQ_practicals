package dealder_Router;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

public class ZMQDealerClient {
    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            Socket dealer = context.createSocket(ZMQ.DEALER);
            dealer.connect("tcp://localhost:5558");

            String request = "Hello from Client!";
            System.out.println("Client sending: " + request);
            dealer.send(request);

            String response = dealer.recvStr();
            System.out.println("Client received: " + response);
        }
    }
}
