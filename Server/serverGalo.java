import java.rmi.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class serverGalo {
	
	String SERVICE_NAME="/PresencesRemote";

	private void bindRMI(Galo galo) throws RemoteException {
		
		System.getProperties().put( "java.security.policy", "./server.policy");

		if( System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try { 
			LocateRegistry.createRegistry(1099);
		} catch( RemoteException e) {
			
		}
		try {
		  LocateRegistry.getRegistry("127.0.0.1",1099).rebind(SERVICE_NAME, galo);
		  } catch( RemoteException e) {
		  	System.out.println("Registry not found");
		  }
	}

	public serverGalo() {
		super();
	}
	
	public void createGalo() {
		
		Galo galo = null;
		try {
			galo = new Galo();
		} catch (RemoteException e1) {
			System.err.println("unexpected error...");
			e1.printStackTrace();
		}
		
		try {
			bindRMI(galo);
		} catch (RemoteException e1) {
			System.err.println("erro ao registar o stub...");
			e1.printStackTrace();
		}
		
	}
}