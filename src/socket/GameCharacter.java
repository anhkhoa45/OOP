package socket;

import model.*;

public class GameCharacter {
    public static final int KNIGHT = 0;
    public static final int MEDUSA = 1;
    public static final int HOT_GIRL = 2;
    public static final int DRACULA = 3;

    public static int getCharacterType(Player player){
        if(player instanceof KnightPlayer) return KNIGHT;
        if(player instanceof MedusaPlayer) return MEDUSA;
        if(player instanceof HotgirlPlayer) return HOT_GIRL;
        if(player instanceof DracularPlayer) return DRACULA;

        return -1;
    }
}
