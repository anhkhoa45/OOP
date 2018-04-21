package model;

public class Game {
    public static final int MODE_NORMAL = 0;
    public static final int MODE_ATTACK = 1;
    public static final int GUEST_READY = 0;
    public static final int STARTED = 1;
    public static final int GAME_OVER = 2;

    public static int gameCount = 0;

    private int id;
    private int mode;
    private Player master;
    private Player guest;
    private Question question;
    private int status;
    private long timeStarted;

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

    public boolean isGuestReady() {
        return (status == GUEST_READY);
    }

    public boolean isStarted() {
        return (status == STARTED);
    }

    public boolean isGameOver() {
        return (status == GAME_OVER);
    }

    public void addGuest(Player player){
        this.guest = player;
    }

    public void removeGuest(){
        this.guest = null;
    }

    public boolean start() {
        if(this.isFull() && this.isGuestReady()) {
            this.question = new Question(1, 1, "This is a question");
            this.status = STARTED;
            return true;
        } else {
            return false;
        }
    }

    public boolean destroy() {
        return (master==null && guest==null);
    }


    public long getRemainTime() {
        long currentTime = System.nanoTime();
        return (currentTime - this.timeStarted);
    }
}
