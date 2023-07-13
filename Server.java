import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.io.*;

public class Server extends UnicastRemoteObject implements ServerInterface {
	public Server() throws RemoteException {
	}

	public String getDate() {
		return new Date().toString();
	}

	public String getFile(String filePath) {
		String[] parts = filePath.split("/");
		ZipFile zipFile;
		try {
			zipFile = new ZipFile(parts[0]+".zip");
			ZipEntry entry = zipFile.getEntry(parts[1]);
			if(entry != null) {
				String content = "";
				try {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(zipFile.getInputStream(entry)));
					String line = "";
					while ((line = br.readLine()) != null) {
						content += line + "\n";
					}
					br.close();
					return content;
				} catch (IOException e) {
					zipFile.close();
					return "Error reading file";
				}
			}
			else {

			}
			zipFile.close();
		} catch (IOException e1) {
			return "Zip file does not exist!";
		}
		return "";
	}

	public static void main(String[] args) {
		try {
			Server server = new Server();
			LocateRegistry.createRegistry(1099);
			Naming.rebind("rmi://localhost:1099/Server", server);
			System.out.println("Server is running...");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
