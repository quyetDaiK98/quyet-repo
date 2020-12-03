package buoi4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class DatagramClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		try {

			Scanner sc = new Scanner(System.in);
            DatagramSocket socket = new DatagramSocket();


            byte[] inData = new byte[1024];


            InetAddress IP = InetAddress.getByName("localhost");

            while (true) {
            	System.out.println("Nhap mang :");
            	String data = sc.nextLine();
            	if(data.equals("ok")) break;
            	byte[] outData = new byte[data.length()];
                outData = data.getBytes();
                


                DatagramPacket sendPkt = new DatagramPacket(outData, outData.length, IP, 2018);

                System.out.println("ready connect server");

                socket.send(sendPkt);

                System.out.println("connect server success");


                DatagramPacket recievePkt = new DatagramPacket(inData, inData.length);

                System.out.println("ready receive message from server)");

                socket.receive(recievePkt);

                System.out.println("receive messag");

                System.out.println("Replay from Server: " + new String(recievePkt.getData()));
			}
            
        } catch (Exception e) {

            System.out.println("error connect udp server");

        }
	}

}
