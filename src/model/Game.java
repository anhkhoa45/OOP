package model;

public class Game {
    public static final int MODE_NORMAL = 0;
    public static final int MODE_ATTACK = 1;

    public static int gameCount = 0;

    private int id;
    private int mode;
    private Player master;
    private Player guest;
    private Question question;

    public Game(){
        this.id = gameCount++;
    }

    public Game(Player player){
        this.id = gameCount++;
        this.master = player;
    }

    public Game(Player master, Player guest) {
        this.id = gameCount++;
        this.master = master;
        this.guest = guest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public Player[] getPlayers() {
        return new Player[]{this.master, this.guest};
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
        this.guest = player;
    }

    public void removeGuest(Player player){
        this.guest = null;
    }

    public void start() {
        //this.question = "This is a question";
        //return question.getListAnswer;
    }

    public void destroy(){

    }
}
