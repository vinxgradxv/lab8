package data;

import exceptions.NullValueException;
import exceptions.WrongAmountOfValuesException;

import java.io.Serializable;

/**
 * Класс, описывающий локацию
 */
public class Location implements Comparable<Location>, Serializable {

    private int id;
    /**
     * Координата x. Поле не может быть null
     */
    private Double x;
    /**
     * Координата y. Поле не может быть null
     */
    private Double y;
    /**
     * Координата z
     */
    private double z;

    /**
     * Конструктор без параметров
     */
    public Location(){}

    /**
     * Конструктор со всеми необходимыми параметрами
     * @param x Координата
     * @param y Координата
     * @param z Координата
     */
    public Location(Double x, Double y, double z) throws NullValueException {
        setX(x);
        setY(y);
        setZ(z);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Метод, возвращающий координату x
     * @return x
     */
    public Double getX() {
        return x;
    }

    /**
     * Метод, возвращающий координату y
     * @return y
     */
    public Double getY() {
        return y;
    }

    /**
     * Метод, возвращающий координату z
     * @return z
     */
    public double getZ() {
        return z;
    }

    /**
     * Метод, устанавливающий значение координаты x
     * @param x координата
     */
    public void setX(Double x) throws NullValueException {
        if (x == null){
            throw new NullValueException();
        }
        this.x = x;
    }

    /**
     * Метод, устанавливающий значение координаты y
     * @param y координата
     */
    public void setY(Double y) throws NullValueException {
        if (y == null){
            throw new NullValueException();
        }
        this.y = y;
    }

    /**
     * Метод, устанавливающий значение координаты z
     * @param z координата
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     * Метод, возвращающий строковое представление объекта класса Location
     * @return string value of Location object
     */
    @Override
    public String toString() {
        return x + " " + y + " " + z;
    }
    /**
     * Метод, сравнивающий 2 объекта класса Coordinates
     * @param o объект для сравнения
     * @return 1; 0; -1 в зависимости от отношения объектов друг к другу
     */
    @Override
    public int compareTo(Location o) {
        if(Math.pow(this.x,2)+Math.pow(this.y,2)+Math.pow(this.z,2) > Math.pow(o.x,2)+Math.pow(o.y,2)+Math.pow(o.z,2)){
            return 1;
        }
        else if(Math.pow(this.x,2)+Math.pow(this.y,2)+Math.pow(this.z,2) < Math.pow(o.x,2)+Math.pow(o.y,2)+Math.pow(o.z,2)){
            return -1;
        }
        else {
            return 0;
        }
    }

    public static Location valueOf(String... arr) throws WrongAmountOfValuesException, NullValueException {
        if (arr.length != 3){
            throw new WrongAmountOfValuesException();
        }
        return new Location(Double.valueOf(arr[0]), Double.valueOf(arr[1]), Double.valueOf(arr[2]));
    }
}