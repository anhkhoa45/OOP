package socket;

import model.*;

import java.lang.Character;

public class GameCharacter {
    public static final int KNIGHT = 0;
    public static final int MEDUSA = 1;
    public static final int HOT_GIRL = 2;
    public static final int DRACULA = 3;

    public static int getCharacterType(AttackCharacter attackCharacter){
        if(attackCharacter instanceof KnightCharacter) return KNIGHT;
        if(attackCharacter instanceof MedusaCharacter) return MEDUSA;
        if(attackCharacter instanceof HotgirlCharacter) return HOT_GIRL;
        if(attackCharacter instanceof DraculaCharacter) return DRACULA;

        return -1;
    }
}
