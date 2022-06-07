package data;

import exceptions.NullValueException;
import exceptions.NumberOutOfBoundsException;
import exceptions.WrongAmountOfValuesException;

import java.io.Serializable;

/**
 * Класс, описывающий человека
 */
public class Person implements Comparable<Person>, Serializable {

    private int id;
    /**
     *
     *  Имя человека. Поле не может быть null, Строка не может быть пустой
     */
    private String name;
    /**
     * Рост человека. Поле может быть null, Значение поля должно быть больше 0
     */
    private Long height;
    /**
     * Цвет волос человека. Поле не может быть null
     */
    private Color hairColor;
    /**
     * Национальность человека. Поле может быть null
     */
    private Country nationality;
    /**
     * Локация человека. Поле может быть null
     */
    private Location location;


    /**
     * Конструктор
     * @param name имя
     * @param height рост
     * @param hairColor цвет волос
     * @param nationality национальность
     * @param location локация
     */
    public Person(String name, Long height, Color hairColor, Country nationality, Location location) throws NullValueException, NumberOutOfBoundsException {
        setId(id); setName(name); setHeight(height); setHairColor(hairColor);
        setNationality(nationality); setLocation(location);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) throws NullValueException {
        if (name == null){
            throw new NullValueException();
        }
        this.name = name;
    }

    public void setHeight(Long height) throws NumberOutOfBoundsException {
        if (height == null) this.height = height;
        else
        if (height <= 0){
            throw new NumberOutOfBoundsException();
        }
        else this.height = height;
    }

    public void setHairColor(Color hairColor) throws NullValueException {
        if (hairColor == null){
            throw new NullValueException();
        }
        this.hairColor = hairColor;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Метод, возвращающий строковое представление объекта Person
     * @return string value of Person object
     */
    @Override
    public String toString() {
        String nameS = "name = " + name;
        String heightS = "height = " + height;
        String hairColorS = "hairColor = " + hairColor;
        String nationalityS = "nationality = " + nationality;
        String locationS = "location = " + location;
        return nameS + '\n' + heightS + '\n' + hairColorS + '\n' + nationalityS + '\n' + locationS;
    }

    /**
     * Метод, возвращающий имя человека
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Метод, возвращающий рост человека
     * @return height
     */
    public Long getHeight() {
        return height;
    }

    /**
     * Метод, возвращающий национальность человека
     * @return nationality
     */
    public Country getNationality() {
        return nationality;
    }

    /**
     * Метод, возвращающий локацию человека
     * @return location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Метод, возвращающий цвет волос человека
     * @return hairColor
     */
    public Color getHairColor() {
        return hairColor;
    }

    public static Person valueOf(String... arr) throws WrongAmountOfValuesException, NullValueException, NumberOutOfBoundsException {
        Long height;
        Country nationality;
        if (arr[1].equals("")){
            height = null;
        }
        else {
            height = Long.valueOf(arr[1]);
        }
        if (arr[3].equals("")){
            nationality = null;
        }
        else {
            nationality = Country.valueOf(arr[3]);
        }
        if (arr.length != 7){
            throw new WrongAmountOfValuesException();
        }
        return new Person(arr[0], height, Color.valueOf(arr[2]), nationality, Location.valueOf(arr[4], arr[5], arr[6]));
    }

    /**
     * Метод, сравнивающий 2 объекта класса Coordinates
     * @param obj объект для сравнения
     * @return 1; 0; -1 в зависимости от отношения объектов друг к другу
     */
    @Override
    public int compareTo(Person obj) {
        if (getNotNullHeight() - obj.getNotNullHeight() != 0){
            return (int) (getHeight() - obj.getHeight());
        }
        else if(this.getName().compareTo(obj.getName()) > 0){
            return 1;
        }
        else if(this.getName().compareTo(obj.getName()) < 0){
            return -1;
        }
        else if(this.getHairColor().compareTo(obj.getHairColor()) > 0){
            return 1;
        }
        else if(this.getHairColor().compareTo(obj.getHairColor()) < 0){
            return -1;
        }
        else if(this.getNotNullNationality().compareTo(obj.getNotNullNationality()) > 0){
            return 1;
        }
        else if(this.getNotNullNationality().compareTo(obj.getNotNullNationality()) < 0){
            return -1;
        }
        else if(this.getLocation().compareTo(obj.getLocation()) > 0){
            return 1;
        }
        else if(this.getLocation().compareTo(obj.getLocation()) < 0){
            return -1;
        }
        else{
            return 0;
        }
    }
    public Long getNotNullHeight(){
        if (height == null){
            return 0L;
        }
        return height;
    }
    public Country getNotNullNationality(){
        if (nationality == null){
            return Country.values()[0];
        }
        return nationality;
    }
}