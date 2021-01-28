import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;


public interface GaloInterface extends Remote {

	public Vector<String> getPresences(String IPAddress, PlayerInterface cl) throws RemoteException;
	public void invitePlayer(String IPAdress, String player2Ip) throws RemoteException;
	public void answerInvite(String player1, String player2, boolean answer) throws RemoteException;
	public void makePlay(String player, int i, int j) throws RemoteException;

}
