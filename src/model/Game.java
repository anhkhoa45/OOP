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
    private boolean guestReady = false;
    private boolean started = false;

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

    public Player getMaster() {
        return master;
    }

    public void setMaster(Player master) {
        this.master = master;
    }

    public Player getGuest() {
        return guest;
    }

    public void setGuest(Player guest) {
        this.guest = guest;
    }

    public Player[] getPlayers() {
        return new Player[]{this.master, this.guest};
    }

    public boolean checkMaster(Player player){
        return this.master.equals(player);
    }
    public boolean checkGuest(Player player){
        return this.guest.equals(player);
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

    public void removeGuest(){
        this.guest = null;
    }

    public boolean start() {
        if(this.isFull() && this.guestReady) {
            this.question = new Question(1, 1, "This is a question");
            this.started = true;
            return true;
        } else {
            return false;
        }
    }

    public void destroy(){

    }
}
