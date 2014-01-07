package Interface;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileInterface extends Remote 
{      
	public byte[] downloadFile(String fileName) throws    RemoteException;
	
	public String ShowFiles() throws    RemoteException;
	
	public String uploadFile(byte[] fileData, String fileName) throws    RemoteException;
}