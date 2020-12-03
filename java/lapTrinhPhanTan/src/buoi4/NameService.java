package buoi4;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NameService extends Remote {
	public int search(String s) throws RemoteException;
    public int insert(String s, String hostName, int portNumber)
            throws RemoteException;
    public int getPort(int index) throws RemoteException;
    public String getHostName(int index) throws RemoteException;
}
