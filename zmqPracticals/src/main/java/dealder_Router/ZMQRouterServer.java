package dealder_Router;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

public class ZMQRouterServer {
    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            Socket router = context.createSocket(ZMQ.ROUTER);
            router.bind("tcp://*:5558");

            while (true) {
                byte[] clientAddress = router.recv(0);
                String request = router.recvStr();

                System.out.println("Server received: " + request);

                String response = "Reply to: " + request;
                router.sendMore(clientAddress);
                router.send(response);
            }
        }
    }
}
