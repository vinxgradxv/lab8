package data;

import java.io.Serializable;

/**
 * Enum, хранящий возможные цвета волос человека
 */
public enum Color implements Serializable {
    BLUE,
    WHITE,
    BROWN;

    public static String stringOfValues(){
        return BLUE + ", " + WHITE + ", " + BROWN;
    }
}