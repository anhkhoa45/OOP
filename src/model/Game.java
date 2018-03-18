package model;

public class Game {
    public static final int STATUS_EMPTY = 0;
    public static final int STATUS_WAITING = 1;
    public static final int STATUS_FULL = 2;

    public static int gameCount = 0;

    private int id;
    private Player[] players;
    private int playerCount;

    public Game(){
        this.id = gameCount++;
        this.players = null;
        this.playerCount = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public boolean isEmpty(){
        return this.playerCount == 0;
    }

    public boolean isWaiting(){
        return this.playerCount == 1;
    }

    public boolean isFull(){
        return this.playerCount == 2;
    }

    public void addPlayer(Player player){
        if(this.isFull()) return;

        if(this.isEmpty()){
            this.players = new Player[2];
            this.players[0] = player;
        } else if(this.isWaiting()) {
            if(this.players[1] == null) {
                this.players[1] = player;
            } else {
                this.players[0] = player;
            }
        }

        this.playerCount++;
    }

    public void removePlayer(Player player){
        if(this.players == null) return;

        if(this.players[0].equals(player)) {
            this.players[0] = null;
        } else if(this.players[1].equals(player)) {
            this.players[1] = null;
        }
        this.playerCount--;
    }
}
