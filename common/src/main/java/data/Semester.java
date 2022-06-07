package data;

import java.io.Serializable;

/**
 * Enum, в котором хранятся возможные варианты семестров
 */
public enum Semester implements Serializable {
    THIRD,
    FIFTH,
    SIXTH,
    SEVENTH;

    public static String stringOfValues(){
        return THIRD + ", " + FIFTH + ", " + SIXTH + ", " + SEVENTH;
    }
}