import java.rmi.registry.LocateRegistry;
import java.util.Iterator;
import java.util.Vector;
import java.util.Scanner;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class Client {

	String SERVICE_NAME = "/PresencesRemote";
	String[] args;
	Scanner scan = new Scanner(System.in);
	String estado;   
	boolean alive;
	PlayerInterface cl;
	GaloInterface presences;
	Vector<String> presencesList;
	String otherPlayer;


	public Client(String[] args) throws RemoteException {
		super();
		if (args.length != 2) {
			System.out.println("Erro: use java ClientApp <ipClient> <ipNameServer>");
			System.exit(-1);
		}
		this.args = args;
		estado = "l";
		alive = true;
		try {
			cl = new Player(args[0]);
			presences = (GaloInterface) LocateRegistry.getRegistry(args[1]).lookup(SERVICE_NAME);
			presencesList = presences.getPresences(args[0], cl);
		} catch (Exception e) {
			System.err.println("Error");
			e.printStackTrace();
		}
		//showPlayers();
		System.out.println("Bem vindo ao jogo do Galo!");
		System.out.println("Os comandos do jogo são os seguintes:");
		System.out.println("'list' para mostrar os jogadores e os seus respetivos estados");
		System.out.println("'iniciar' para convidar um jogador para um jogo de Galo ");
		run();
		
	}

	public void showPlayers(Boolean print) {
		try {
			presencesList = presences.getPresences(args[0], cl);
			Iterator<String> it = presencesList.iterator();
			//System.out.println(presencesList);
			while(it.hasNext()){
				String player = it.next();
				if(player.contains(args[0])){
					estado = player.substring(args[0].length() + 1);
				}
				if(print){
					System.out.println(player);
				}
			}

		} catch (Exception e) {
			System.err.println("Error");
			e.printStackTrace();
		}
	
	}

	public void run(){
		while(alive = true){
			// showPlayers(false);
			// System.out.println("estado: " + estado);
			String msg = scan.nextLine();
			showPlayers(false);
			//showPlayers();
			if(estado.equals("l")){
				//System.out.println("Escolha um jogador para iniciar um jogo (o jogador tem que estar livre)");
				//showPlayers();
				//String msg = scan.nextLine();
				 if(msg.equals("list")){
					showPlayers(true);
				}else if(msg.contains("iniciar")){
					try {
					presences.invitePlayer(args[0], msg.substring(8));
				} catch (Exception e) {
					System.err.println("Error");
					e.printStackTrace();
				}
				}
			}else if(estado.equals("ecc")){
				//String msg = scan.nextLine();
				System.out.println("Aguardando que o convite seja aceite");
			}else if(estado.equals("rc")){
				System.out.println("estado rc");
				//String msg = scan.nextLine();
				if(msg.equals("s")){
					try {
						presences.answerInvite(args[0], cl.getOtherPlayer(), true);
					} catch (Exception e) {
						System.err.println("Error");
						e.printStackTrace();
					}
				}else if(msg.equals("n")){
					try {
						presences.answerInvite(args[0], cl.getOtherPlayer(), false);
						otherPlayer = null;
					} catch (Exception e) {
						System.err.println("Error");
						e.printStackTrace();
					}
				}else{
					System.out.println("Por favor introduza 's' ou 'n'");
				}
			}else if(estado.equals("psj")){
				try {
					presences.makePlay(args[0], Integer.parseInt(msg.substring(0,1)), Integer.parseInt(msg.substring(2)));
				} catch (Exception e) {
					System.err.println("Error");
					e.printStackTrace();
				}
			}else if(estado.equals("erj")){
				try {
					System.out.println("aguarde que o jogador " + cl.getOtherPlayer() + " submeta a sua jogada");
				} catch (Exception e) {
					System.err.println("Error");
					e.printStackTrace();
				}
			}else{
				System.out.println("estado nao encontrado");
			}
		}
	}

	
}