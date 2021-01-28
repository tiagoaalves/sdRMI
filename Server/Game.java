public class Game{

    String player1;
    String player2;
    String[][] grid;
    boolean isOn;
    String currentPLayer;
    String waitingPlayer;
    boolean allowed;

    public Game(String player1, String player2){
        this.player1 = player1;
        this.player2 = player2;
        grid = new String[3][3];
        isOn = true;
        allowed = true;
        populate();
    }

    public String getCurrentPlayer(){
        return currentPLayer;
    }

    public String getWaitingPlayer(){
        return waitingPlayer;
    }

    public void setCurrentPlayer(String i){
        currentPLayer = i;
    }

    public void setWaitingPlayer(String i){
        waitingPlayer = i;
    }

    public boolean getIsOn(){
        return isOn;
    }

    public void setIsOn(boolean b){
        isOn = b;
    }

    public boolean getAllowed(){
        return allowed;
    }

    public void setAllowed(boolean b){
        allowed = b;
    }

    public String[][] getGrid(){
        return grid;
    }

    public void play(String player, int i, int j){
        int k = 0;
        if(grid[i][j] != null){
            
        }
        grid[i][j] = player;
        for(int d = 0; d < 3; d++){
          System.out.println(grid[d][0] + "|" + grid[d][1] + "|" + grid[d][2]);
            if(k < 2){
                System.out.println("------");
                k++;
            }
        }
    }

    public void populate(){
        for(int d = 0; d < 3; d++){
            for(int e = 0; e < 3; e++){
                grid[d][e] = " ";
            }
        }
    }

    public boolean checkWinner(){
        boolean result = false;
        for(int i = 0; i < 3; i++){
            if(grid[i][0].equals(grid[i][1]) && grid[i][1].equals(grid[i][2])){
                //result = grid[i][0];
                if(grid[i][0] != " "){
                    result = true;
                }
            }else if(grid[0][i].equals(grid[1][i]) && grid[1][i].equals(grid[2][i])){
                //result = grid[0][i];
                if(grid[0][i] != " "){
                    result = true;
                }
            }else if(grid[0][0].equals(grid[1][1]) && grid[1][1].equals(grid[2][2])){
                //result = grid[0][0];
                if(grid[0][0] != " "){
                    result = true;
                }
            }else if(grid[0][2].equals(grid[1][1]) && grid[1][1].equals(grid[2][0])){
                //result = grid[0][2];
                if(grid[0][2] != " "){
                result = true;
                }
            }
        }
        return result;
    }
    
}