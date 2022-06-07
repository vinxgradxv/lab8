package utils;

import data.*;
import exceptions.NumberOutOfBoundsException;
import exceptions.WrongAmountOfCoordinatesException;

/**
 * Класс, устанавливающий значения полей объекта класса StudyGroup
 */
public class FieldSetterClass {
    /**
     * Метод, устанавливающий значение groupName
     * @param s необработанная строка
     * @return groupName
     */
    public static String getGroupName(String s){
        ValidationClass.checkString(s, false);
        String name = new String(s);
        return name;
    }
    /**
     * Метод, устанавливающий значение groupCoordinates
     * @param s необработанная строка
     * @return groupCoordinates
     */
    public static Coordinates getGroupCoordinates(String s) throws NumberOutOfBoundsException, WrongAmountOfCoordinatesException {
        return null;
    }
    /**
     * Метод, устанавливающий значение groupStudentsCount
     * @param s необработанная строка
     * @return groupStudentsCount
     */
    public static Long getGroupStudentsCount(String s) throws NumberOutOfBoundsException{
        ValidationClass.checkLong(s, false, true, 0L);
        Long studentsCount = Long.valueOf(s);
        if (s.equals("")){return null;}
        return studentsCount;
    }
    /**
     * Метод, устанавливающий значение groupExpelledStudents
     * @param s необработанная строка
     * @return groupExpelledStudents
     */
    public static Integer getGroupExpelledStudents(String s) throws NumberOutOfBoundsException{
        ValidationClass.checkInt(s, true, true, 0);
        Integer expelledStudents = Integer.valueOf(s);
        if (s.equals("")){return null;}
        return expelledStudents;
    }
    /**
     * Метод, устанавливающий значение groupShouldBeExpelled
     * @param s необработанная строка
     * @return groupShouldBeExpelled
     */
    public static int getGroupShouldBeExpelled(String s) throws NumberOutOfBoundsException{
        ValidationClass.checkInt(s, false, true, 0);
        int shouldBeExpelled = Integer.valueOf(s);
        return shouldBeExpelled;
    }
    /**
     * Метод, устанавливающий значение groupSemesterEnum
     * @param s необработанная строка
     * @return groupSemesterEnum
     */
    public static Semester getGroupSemesterEnum(String s){
        ValidationClass.checkSemester(s, true);
        Semester semester = Semester.valueOf(s);
        if (s.equals("")){return null;}
        return semester;
    }
    /**
     * Метод, устанавливающий значение adminName
     * @param s необработанная строка
     * @return adminName
     */
    public static String getAdminName(String s){
        ValidationClass.checkString(s, false);
        if (s.equals("")){return null;}
        return s;
    }
    /**
     * Метод, устанавливающий значение adminHeight
     * @param s необработанная строка
     * @return adminHeight
     */
    public static Long getAdminHeight(String s) throws NumberOutOfBoundsException{
        ValidationClass.checkLong(s, true, true, 0L);
        Long height = Long.valueOf(s);
        if (s.equals("")){return null;}
        return height;
    }
    /**
     * Метод, устанавливающий значение adminHairColor
     * @param s необработанная строка
     * @return adminHairColor
     */
    public static Color getAdminHairColor(String s){
        ValidationClass.checkColor(s, false);
        Color hairColor = Color.valueOf(s);
        if (s.equals("")){return null;}
        return hairColor;
    }
    /**
     * Метод, устанавливающий значение adminNationality
     * @param s необработанная строка
     * @return adminNationality
     */
    public static Country getAdminNationality(String s){
        ValidationClass.checkCountry(s, true);
        Country nationality = Country.valueOf(s);
        if (s.equals("")){return null;}
        return nationality;
    }
    /**
     * Метод, устанавливающий значение adminLocation
     * @param s необработанная строка
     * @return adminLocation
     */
    public static Location getAdminLocation(String s) throws WrongAmountOfCoordinatesException {
        return null;
    }
}
