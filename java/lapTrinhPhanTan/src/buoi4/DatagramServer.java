package buoi4;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatagramServer {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DatagramPacket datapacket, returnpacket;
        int port = 2018;
        int len = 1024;
        try {
            DatagramSocket datasocket = new DatagramSocket(port);
            byte[] buf = new byte[len];
            byte[] outData = new byte[len];
            datapacket = new DatagramPacket(buf, buf.length);
            while (true) {
                try {
                	
                	datasocket.receive(datapacket);
                	
                	int lenth = 0;
                	byte[] receivedData = datapacket.getData();
                	for (int i = 0; i < receivedData.length; i++) 
						if(receivedData[i] == 0) {
							lenth = i;
							break;
						}
                	
                    String[] h = new String(datapacket.getData(),0,lenth).replaceAll("\\s{2,}", " ").trim().split(" ");
                    
                    List<Integer> mangChan = new ArrayList<Integer>();
                    List<Integer> mangLe= new ArrayList<Integer>();
                    
                    for (int i = 0; i < h.length; i++) {
						int value = Integer.parseInt(h[i]);
						if(value %2 == 0)
							mangChan.add(value);
						else mangLe.add(value);
					}

                    mangChan.addAll(mangLe);
                    String data = "";
                    for (int i : mangChan) {
						data += i + " ";
					}
                    
                    outData = data.getBytes();
                    datapacket.setData(outData);
                    returnpacket = new DatagramPacket(
                            datapacket.getData(),
                            datapacket.getData().length,
                            datapacket.getAddress(),
                            datapacket.getPort());
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
