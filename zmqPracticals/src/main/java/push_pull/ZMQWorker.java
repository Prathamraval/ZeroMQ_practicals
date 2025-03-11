package push_pull;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

public class ZMQWorker {
    public static void main(String[] args) throws InterruptedException {
        try (ZContext context = new ZContext()) {
            Socket worker = context.createSocket(ZMQ.PULL);
            worker.connect("tcp://localhost:5557");

            while (true) {
                String task = worker.recvStr();
                System.out.println("Worker processing: " + task);
                Thread.sleep(2000); // Simulate task processing
            }
        }
    }
}
