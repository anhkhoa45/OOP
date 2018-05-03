package socket;

import model.*;

public class GameCharacter {
    public static final int KNIGHT = 0;
    public static final int MEDUSA = 1;
    public static final int HOT_GIRL = 2;
    public static final int DRACULA = 3;

    public static int getCharacterType(model.Character player){
        if(player instanceof KnightCharacter) return KNIGHT;
        if(player instanceof MedusaCharacter) return MEDUSA;
        if(player instanceof HotgirlCharacter) return HOT_GIRL;
        if(player instanceof DraculaCharacter) return DRACULA;

        return -1;
    }
}
