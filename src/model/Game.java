package model;

public class Game {
    public static final int STATUS_EMPTY = 0;
    public static final int STATUS_WAITING = 1;
    public static final int STATUS_FULL = 2;

    public static int gameCount = 0;

    private int id;
    private Player master;
    private Player guest;

    public Game(){
        this.id = gameCount++;
    }

    public Game(Player player){
        this.id = gameCount++;
        this.master = player;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player[] getPlayers() {
        Player[] players = {this.master, this.guest};
        return players;
    }

    public boolean isEmpty(){
        return this.master == null && this.guest == null;
    }

    public boolean isWaiting(){
        return this.guest == null;
    }

    public boolean isFull(){
        return this.guest != null;
    }

    public void addGuest(Player player){

    }

    public void removeGuest(Player player){

    }

    public void destroy(){

    }
}
