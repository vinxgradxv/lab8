package data;

import java.io.Serializable;

/**
 * Enum, хранящий возможные страны происхождения человека
 */
public enum Country implements Serializable {
    UNITED_KINGDOM,
    GERMANY,
    NORTH_KOREA,
    JAPAN;

    public static String stringOfValues(){
        return UNITED_KINGDOM + ", " + GERMANY+ ", " + NORTH_KOREA + ", " + JAPAN;
    }
}