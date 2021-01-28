import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Player extends UnicastRemoteObject implements PlayerInterface {

    String name;
    String otherPlayer;
    String estado;

	public Player(String name) throws RemoteException {
        super();
        this.name = name;
	}
    
    
    public String getEstado(){
        return estado;
    }

    public String getOtherPlayer(){
        return otherPlayer;
    }

	public void receiveInvite(String player1){
		otherPlayer = player1;
        System.out.println("O jogador " + otherPlayer + " acabou de o convidar para um jogo, quer aceitar? (s/n)");
        estado = "rc";
	}

	public void receiveAnswer(boolean answer){
		if(answer){
			System.out.println("O convite foi aceite");
		}else{
			System.out.println("O convite for recusado");
			otherPlayer = null;
		}
    }
    
    public void receiveGameStarter(String otherPlayer, String[][] grid){
        String a = " ";
        String b = " ";
        String c = " ";
        int i = 0;
        for(int d = 0; d < 3; d++){
            a = " ";
            b = " ";
            c = " ";
            if(grid[d][0].equals(name)){
                System.out.println(grid[d][0]);
                 a = "X";
            }else if(grid[d][0].equals(otherPlayer)){
                System.out.println(grid[d][0]);
                a = "O";
            }
            if(grid[d][1].equals(name)){
                System.out.println(grid[d][0]);
                b = "X";
            }else if(grid[d][1].equals(otherPlayer)){
                System.out.println(grid[d][1]);
                b = "O";
            }
            if(grid[d][2].equals(name)){
                System.out.println(grid[d][2]);
                c = "X";
            }else if(grid[d][2].equals(otherPlayer)){
                System.out.println(grid[d][2]);
                c = "O";
            }
            System.out.println(a + "|" + b + "|" + c);
            if(i < 2){
                System.out.println("------");
                i++;
            }
        }
        System.out.println("submeta a sua jogada");
    }

    public void receiveGameWaiter(String otherPlayer, String[][] grid){
        String a = " ";
        String b = " ";
        String c = " ";
        int i = 0;
        for(int d = 0; d < 3; d++){
            a = " ";
            b = " ";
            c = " ";
            if(grid[d][0].equals(name)){
                 a = "X";
            }else if(grid[d][0].equals(otherPlayer)){
                a = "O";
            }
            if(grid[d][1].equals(name)){
                b = "X";
            }else if(grid[d][1].equals(otherPlayer)){
                b = "O";
            }
            if(grid[d][2].equals(name)){
                c = "X";
            }else if(grid[d][2].equals(otherPlayer)){
                c = "O";
            }
            System.out.println(a + "|" + b + "|" + c);
            if(i < 2){
                System.out.println("------");
                i++;
            }
        }
        System.out.println("aguarde que o jogador " + otherPlayer + " submeta a sua jogada");
    }

    public void makePlay(){
        System.out.println("Make play!");
    }

    public void waitPlay(){
        System.out.println("Wait play!");
    }

    public void gameOver(boolean b){
        if(b){
            System.out.println("parabens ganhaste!");
        }else{
            System.out.println("da proxima vez corre melhor");
        }
    }

}