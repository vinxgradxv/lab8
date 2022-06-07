package utils;

import data.Color;
import data.Country;
import data.Semester;
import exceptions.NumberOutOfBoundsException;
import exceptions.WrongAmountOfCoordinatesException;

/**
 * Класс, оценивающий корректность введенных данных
 */
public class ValidationClass {
    /**
     * Метод, проверяющий корректность Long значения
     * @param s пользовательский ввод
     * @param nullable пользовательский ввод
     * @param leftRange Устанавливается ли левая граница
     * @param range значение границы
     */
    public static void checkLong(String s, Boolean nullable, Boolean leftRange, Long range) throws NumberFormatException, NumberOutOfBoundsException{
        if(s.equals("") && !nullable){
            throw new NullPointerException();
        }

        Long n = Long.valueOf(s);

        if((leftRange && n<=range) | (!(leftRange) && n>range)){
            throw new NumberOutOfBoundsException();
        }
    }

    /**
     * Метод, проверяющий корректность Double значения
     * @param s пользовательский ввод
     * @param nullable пользовательский ввод
     */
    public static void checkDouble(String s, Boolean nullable) throws NumberFormatException{
        if(s.equals("") && !nullable){
            throw new NullPointerException();
        }

        Double n = Double.valueOf(s);
    }

    /**
     * Метод, проверяющий корректность Int значения
     * @param s пользовательский ввод
     * @param nullable пользовательский ввод
     * @param leftRange Устанавливается ли левая граница
     * @param range значение границы
     */
    public static void checkInt(String s, Boolean nullable, Boolean leftRange, Integer range) throws NumberFormatException, NumberOutOfBoundsException{
        if(s.equals("") && !nullable){
            throw new NullPointerException();
        }

        Integer n = Integer.valueOf(s);

        if((leftRange && n<=range) | (!(leftRange) && n>range)){
            throw new NumberOutOfBoundsException();
        }
    }

    /**
     * Метод, проверяющий корректность String значения
     * @param s пользовательский ввод
     * @param nullable пользовательский ввод
     */
    public static void checkString(String s, Boolean nullable){
        if (s.equals("") && !nullable){
            throw new NullPointerException();
        }
    }

    /**
     * Метод, проверяющий корректность Coordinates значения
     * @param s пользовательский ввод
     * @param nullable пользовательский ввод
     */
    public static void checkCoordinates(String s, Boolean nullable) throws NumberOutOfBoundsException, WrongAmountOfCoordinatesException{
        if (s.equals("") && !nullable){
            throw new NullPointerException();
        }
        String[] values = s.split(" ");
        if(values.length == 2){
            checkLong(values[0], false, false, 722L);
            checkLong(values[1], false, false, 102L);
        }
        else throw new WrongAmountOfCoordinatesException();
    }

    /**
     * Метод, проверяющий корректность Semester значения
     * @param s пользовательский ввод
     * @param nullable пользовательский ввод
     */
    public static void checkSemester(String s, Boolean nullable) throws IllegalArgumentException{
        if (s.equals("") && !nullable){
            throw new NullPointerException();
        }
        Semester semester = Semester.valueOf(s);
    }

    /**
     * Метод, проверяющий корректность Color значения
     * @param s пользовательский ввод
     * @param nullable пользовательский ввод
     */
    public static void checkColor(String s, Boolean nullable) throws IllegalArgumentException{
        if (s.equals("") && !nullable){
            throw new NullPointerException();
        }
        Color color = Color.valueOf(s);
    }

    /**
     * Метод, проверяющий корректность Country значения
     * @param s пользовательский ввод
     * @param nullable пользовательский ввод
     */
    public static void checkCountry(String s, Boolean nullable) throws IllegalArgumentException{
        if (s.equals("") && !nullable){
            throw new NullPointerException();
        }
        Country country = Country.valueOf(s);
    }

    /**
     * Метод, проверяющий корректность Location значения
     * @param s пользовательский ввод
     * @param nullable пользовательский ввод
     */
    public static void checkLocation(String s, Boolean nullable) throws WrongAmountOfCoordinatesException{
        if (s.equals("") && !nullable){
            throw new NullPointerException();
        }
        String[] values = s.split(" ");
        if(values.length == 3){
            for(int i = 0;i<values.length;i++){
                checkDouble(values[i], nullable);
            }
        }
        else throw new WrongAmountOfCoordinatesException();
    }


}
