import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.ArrayList;

public class Galo extends UnicastRemoteObject implements GaloInterface {
	
	private Hashtable<String, IPInfo> presentIPs = new Hashtable<String, IPInfo>();
	private ArrayList<Game> presentGames = new ArrayList<Game>();
	
	public Galo() throws RemoteException {
		super();
		System.out.println("Servidor Iniciado");
	}

	public Vector<String> getPresences(String IPAddress, PlayerInterface cl) throws RemoteException {
		
		long actualTime = new Date().getTime();
		
		synchronized(this) {
			if (presentIPs.containsKey(IPAddress)) {
				IPInfo newIp = presentIPs.get(IPAddress);
				newIp.setLastSeen(actualTime);
				newIp.setCl(cl);
			}
			else {
				IPInfo newIP = new IPInfo(IPAddress, actualTime, cl, "l");
				presentIPs.put(IPAddress, newIP);
			}
		}
		return getIPList();
	}
	
	private Vector<String> getIPList(){
		Vector<String> result = new Vector<String>();
		for (Enumeration<IPInfo> e = presentIPs.elements(); e.hasMoreElements(); ) {
			IPInfo element = e.nextElement();
			if (!element.timeOutPassed(180*1000)) {
				result.add(element.getIP() + " " + element.getEstado());
			}
		}
		return result;
	}

	public void invitePlayer(String player1, String player2){
		presentIPs.get(player1).setEstado("ecc");
		if(presentIPs.containsKey(player2)){
			if(presentIPs.get(player2).getEstado().equals("l")){
				presentIPs.get(player2).setEstado("rc");
				try {
				presentIPs.get(player2).getCl().receiveInvite(player1);
				System.out.println("convite enviado");
				} catch( RemoteException exception) {
					System.err.println("Error");
					exception.printStackTrace();
				}
			}else{
				System.out.println("jogador nao livre");	
			}
		}else{
			System.out.println("jogador nao encontrado");
		}
	}

	public void answerInvite(String player1, String player2, boolean answer){
		if(answer){
			//startGame(player1, player2);
			try {
				presentIPs.get(player2).getCl().receiveAnswer(true);
				initializeGame(player1, player2);
				} catch( RemoteException exception) {
					System.err.println("Error");
					exception.printStackTrace();
				}
		}else{
			try {
				presentIPs.get(player2).getCl().receiveAnswer(false);
				presentIPs.get(player1).setEstado("l");
				presentIPs.get(player2).setEstado("l");
				} catch( RemoteException exception) {
					System.err.println("Error");
					exception.printStackTrace();
				}
		}
	}

	public void initializeGame(String player1, String player2){
		Game game = new Game(player1, player2);
		presentGames.add(game);
		try {
			presentIPs.get(player1).setEstado("psj");
		presentIPs.get(player1).getCl().receiveGameStarter(player2, game.getGrid());
		presentIPs.get(player2).setEstado("erj");
		presentIPs.get(player2).getCl().receiveGameWaiter(player1, game.getGrid());
		game.setCurrentPlayer(presentIPs.get(player1).getIP());
		game.setWaitingPlayer(presentIPs.get(player2).getIP());
		System.out.println("estados alterados");
			} catch( RemoteException exception) {
				System.err.println("Error");
				exception.printStackTrace();
			}

		// IPInfo trader;
		// IPInfo currentPlayer = presentIPs.get(player1);
		// IPInfo waitingPlayer = presentIPs.get(player2);
		// //presentGames.get(player1).play(player1, 1, 2);
		// try {
		// 	presentIPs.get(player1).getCl().receiveGame(player2);
		// 	presentIPs.get(player2).getCl().receiveGame(player1);
		// 	} catch( RemoteException exception) {
		// 		System.err.println("Error");
		// 		exception.printStackTrace();
		// 	}
		// while(presentGames.get(player1).getIsOn()){
		// 	if(presentGames.get(player1).getAllowed()){
		// 	try {
		// 		currentPlayer.getCl().makePlay();
		// 		waitingPlayer.getCl().waitPlay();
		// 		} catch( RemoteException exception) {
		// 			System.err.println("Error");
		// 			exception.printStackTrace();
		// 		}
		// 	currentPlayer.setEstado("psj");
		// 	waitingPlayer.setEstado("erj");
		// 	trader = currentPlayer;
		// 	currentPlayer = waitingPlayer;
		// 	waitingPlayer = trader;
		// 	presentGames.get(player1).setAllowed(false);
		// 	}
		// }
	}

	public void makePlay(String player, int i, int j){
		for(Game g: presentGames){
			System.out.println("ip " + g.getCurrentPlayer());
			if(g.getCurrentPlayer().equals(player)){
				g.play(player, i, j);
				System.out.println("jogada feita");
				if(g.checkWinner()){
					try {
						presentIPs.get(g.getCurrentPlayer()).getCl().gameOver(true);
						presentIPs.get(g.getWaitingPlayer()).getCl().gameOver(false);
						//g.setIsOn(false);
					} catch( RemoteException exception) {
						System.err.println("Error");
						exception.printStackTrace();
					}
				}else{
					try {
						String trader = g.getCurrentPlayer();
						g.setCurrentPlayer(g.waitingPlayer);
						g.setWaitingPlayer(trader);
						presentIPs.get(g.getCurrentPlayer()).getCl().receiveGameStarter(g.getWaitingPlayer(), g.getGrid());
						presentIPs.get(g.getCurrentPlayer()).setEstado("psj");
						presentIPs.get(g.getWaitingPlayer()).getCl().receiveGameWaiter(g.getCurrentPlayer(), g.getGrid());
						presentIPs.get(g.getWaitingPlayer()).setEstado("erj");
					} catch( RemoteException exception) {
						System.err.println("Error");
						exception.printStackTrace();
					}
				}
			}
		}
	}
}

class IPInfo {
	
	private String ip;
	private long lastSeen;
	private PlayerInterface cl;
	private String estado;

	public IPInfo(String ip, long lastSeen, PlayerInterface cl, String estado) {
		this.ip = ip;
		this.lastSeen = lastSeen;
		this.cl = cl;
		this.estado = estado;
	}

	public String getIP () {
		return this.ip;
	}

    public PlayerInterface getCl () {
		return this.cl;
	}

	public String getEstado(){
		return this.estado;
	}

	public void setLastSeen(long time){
		this.lastSeen = time;
	}

	public void setCl(PlayerInterface cl){
		this.cl = cl;
	}

	public void setEstado(String estado){
		this.estado = estado;
	}

	public boolean timeOutPassed(int timeout){
		boolean result = false;
		long timePassedSinceLastSeen = new Date().getTime() - this.lastSeen;
		if (timePassedSinceLastSeen >= timeout)
			result = true;
		return result;
	}
}