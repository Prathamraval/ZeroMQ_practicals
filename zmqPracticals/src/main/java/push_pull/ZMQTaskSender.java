package push_pull;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

public class ZMQTaskSender {
    public static void main(String[] args) throws InterruptedException {
        try (ZContext context = new ZContext()) {
            Socket sender = context.createSocket(ZMQ.PUSH);
            sender.bind("tcp://*:5557");

            for (int i = 1; i <= 5; i++) {
                String task = "Task " + i;
                System.out.println("Sending: " + task);
                sender.send(task);
                Thread.sleep(1000);
            }
        }
    }
}
