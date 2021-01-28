import java.rmi.RemoteException;

public class ClientApp {	
	public static void main(String[] args) {
			Client client;
			try {
				client = new Client(args);
			} catch (Exception e) {
				System.err.println("Error");
				e.printStackTrace();
			}
	}

}