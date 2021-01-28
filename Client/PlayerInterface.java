import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PlayerInterface extends Remote {

	//public void setNewPresence(String IPAddress) throws RemoteException;
	public void receiveInvite(String player1) throws RemoteException;
	public void receiveAnswer(boolean answer) throws RemoteException;
	public String getOtherPlayer() throws RemoteException;
	public void receiveGameStarter(String otherPlayer, String[][] grid) throws RemoteException;
	public void receiveGameWaiter(String otherPlayer, String[][] grid) throws RemoteException;
	public void makePlay() throws RemoteException;
	public void waitPlay() throws RemoteException;
	public void gameOver(boolean b) throws RemoteException;

}