package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class DatagramServer {
    public static void main(String[] args) {
        DatagramPacket datapacket, returnpacket;
        int port = 2019;
        int len = 1024;
        try {
            DatagramSocket datasocket = new DatagramSocket(port);
            byte[] buf = new byte[len];
            datapacket = new DatagramPacket(buf, buf.length);

            while (true) {
                try {
                    datasocket.receive(datapacket);
                    String rcStr = new String(datapacket.getData(),0,datapacket.getLength());
                    System.out.println("Chuoi da nhan: " + rcStr);

                    String result = "";
                    String after = rcStr.replaceAll("\\s{2,}", " ").trim();
                    String[] splited = after.split(" ");

                    for (String s : splited) {
                        int temp = Integer.parseInt(s);
                        if (temp % 2 == 0) {
                            result = temp + " " + result;
                        } else {
                            result += temp + " ";
                        }
                    }

                    returnpacket = new DatagramPacket(result.getBytes(), result.getBytes().length,
                            datapacket.getAddress(), datapacket.getPort());
                    datasocket.send(returnpacket);
                } catch (IOException e) {
                    System.err.println(e);
                }
            }
        } catch (SocketException se) {
            System.err.println(se);
        }
    }
}