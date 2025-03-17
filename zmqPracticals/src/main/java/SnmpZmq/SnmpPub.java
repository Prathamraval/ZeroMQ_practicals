package SnmpZmq;

import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.*;
import org.snmp4j.mp.*;
import org.snmp4j.transport.*;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

import java.io.IOException;
import java.util.List;

public class SnmpPub {

    public static void main(String[] args) {
        String ipAddress = "10.20.41.5"; // Replace with your SNMP device IP
        String community = "public"; // SNMP community string

        try (ZContext context = new ZContext()) {
            Socket publisher = context.createSocket(ZMQ.PUB);
            publisher.bind("tcp://*:5556");

            Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
            snmp.listen();

            Address targetAddress = new UdpAddress(ipAddress + "/161");
            CommunityTarget target = new CommunityTarget();
            target.setCommunity(new OctetString(community));
            target.setVersion(SnmpConstants.version2c);
            target.setAddress(targetAddress);
            target.setRetries(2);
            target.setTimeout(1500);

            OID startOID = new OID(".1.3.6.1");
            PDU pdu = new PDU();
            pdu.setType(PDU.GETNEXT);
            pdu.add(new VariableBinding(startOID));

            while (true) {
                ResponseEvent response = snmp.getNext(pdu, target);
                if (response != null) {
                    PDU responsePDU = response.getResponse();
                    if (responsePDU != null && responsePDU.getErrorStatus() == PDU.noError) {
                        List<VariableBinding> varBinds = (List<VariableBinding>) responsePDU.getVariableBindings();
                        for (VariableBinding varBind : varBinds) {
                            String snmpData = "OID: " + varBind.getOid() + " -> " + varBind.getVariable();
                            System.out.println("Publishing: " + snmpData);
                            publisher.send(snmpData);
                        }
                        OID lastOID = varBinds.get(varBinds.size() - 1).getOid();
                        // Set the new OID for the next walk
                        pdu.clear();
                        pdu.add(new VariableBinding(lastOID)); // Add the next OID to the PDU

                    }
                }
                Thread.sleep(2000); // Simulate delay
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
