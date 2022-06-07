package data;

import exceptions.NullValueException;
import exceptions.NumberOutOfBoundsException;
import exceptions.WrongAmountOfValuesException;

import java.io.Serializable;

/**
 * Класс, описывающий координаты
 */
public class Coordinates implements Comparable<Coordinates>, Serializable {

    private int id;
    /**
     * Координата x. Максимальное значение поля: 722, Поле не может быть null
     */
    private Long x;
    /**
     * Координата y. Максимальное значение поля: 102, Поле не может быть null
     */
    private Long y;

    /**
     * Конструктор класса
     * @param x Координата
     * @param y Координата
     */
    public Coordinates(Long x, Long y) throws NumberOutOfBoundsException, NullValueException {
        setX(x); setY(y);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    /**
     * Метод, устанавливающий значение координаты x
     * @param x Координата
     */
    public void setX(Long x) throws NumberOutOfBoundsException, NullValueException{
        if (x == null){
            throw new NullValueException();
        }
        if (x > 722){
            throw new NumberOutOfBoundsException();
        }
        this.x = x;
    }

    /**
     * Метод, устанавливающий значение координаты y
     * @param y Координата
     */
    public void setY(Long y) throws NullValueException, NumberOutOfBoundsException {
        if (y == null){
            throw new NullValueException();
        }
        if (y > 102){
            throw new NumberOutOfBoundsException();
        }
        this.y = y;
    }

    /**
     * Метод, возвращающий значение координаты x
     * @return x
     */
    public Long getX() {
        return x;
    }

    /**
     * Метод, возвращающий значение координаты y
     * @return y
     */
    public Long getY() {
        return y;
    }

    /**
     * Метод, возвращающий строковое представление объекта класса Coordinates
     * @return string value of Coordinates object
     */
    @Override
    public String toString() {
        return String.valueOf(x) + " " + String.valueOf(y);
    }


    public static Coordinates valueOf(String... arr) throws WrongAmountOfValuesException, NumberOutOfBoundsException, NullValueException {
        if (arr.length != 2){
            throw new WrongAmountOfValuesException();
        }
        return new Coordinates(Long.valueOf(arr[0]), Long.valueOf(arr[1]));
    }

    /**
     * Метод, сравнивающий 2 объекта класса Coordinates
     * @param o2 объект для сравнения
     * @return 1; 0; -1 в зависимости от отношения объектов друг к другу
     */
    public int compareTo(Coordinates o2){
        if (this.getX() * this.getX() + this.getY()*this.getY() > o2.getX()*o2.getX()+o2.getY()*o2.getY()){
            return 1;
        }
        else if(this.getX() * this.getX() + this.getY()*this.getY() < o2.getX()*o2.getX()+o2.getY()*o2.getY()){
            return -1;
        }
        else {return 0;}
    }

}