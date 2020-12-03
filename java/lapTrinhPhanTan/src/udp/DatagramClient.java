package udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class DatagramClient {
    public static void main(String[] args) {
        String hostname;
        int port = 2019;
        int len = 1024;
        DatagramPacket sPacket, rPacket;
        if (args.length > 0)
            hostname = args[0];
        else
            hostname = "localhost";
        try {
            InetAddress ia = InetAddress.getByName(hostname);
            DatagramSocket datasocket = new DatagramSocket();
            BufferedReader stdinp = new BufferedReader(new InputStreamReader(System.in));
            while (true) {

                try {

                    String echoline = stdinp.readLine();
                    if (echoline.equals("done")) break;
                    byte[] buffer = echoline.getBytes();
                    System.out.println("Chuoi da nhap: " + new String(buffer));
                    sPacket = new DatagramPacket(buffer, buffer.length, ia, port);
                    datasocket.send(sPacket);

                    byte[] rbuffer = new byte[len];
                    rPacket = new DatagramPacket(rbuffer, rbuffer.length);
                    datasocket.receive(rPacket);
                    String retstring = new String(rPacket.getData());
                    System.out.println("Ket qua: " + retstring);

                } catch (IOException e) {
                    System.err.println(e);
                }
            } // while
        } catch (UnknownHostException | SocketException e) {
            System.err.println(e);
        }
    }  // end main
}
