package data;


import exceptions.NullValueException;
import exceptions.NumberOutOfBoundsException;
import exceptions.WrongAmountOfValuesException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс хранимых объектов
 */

public class StudyGroup implements Comparable<StudyGroup>, Serializable {
    /**
     * Уникальный идентификатор группы. Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным,
     * Значение этого поля должно генерироваться автоматически
     */
    private Long id;
    /**
     * Имя группы. Поле не может быть null, Строка не может быть пустой
     */
    private String name;
    /**
     * Координаты группы. Поле не может быть null
     */
    private Coordinates coordinates;
    /**
     * Дата создания группы. Поле не может быть null, Значение этого поля должно генерироваться автоматически
     */
    private LocalDateTime creationDate;
    /**
     * Количество студентов. Значение поля должно быть больше 0, Поле не может быть null
     */
    private Long studentsCount;
    /**
     * Количество отчисленных студентов. Значение поля должно быть больше 0, Поле может быть null
     */
    private Integer expelledStudents;
    /**
     * Количество студентов, представленных к отчислению. Значение поля должно быть больше 0
     */
    private int shouldBeExpelled;
    /**
     * Текущий семестр. Поле может быть null
     */
    private Semester semesterEnum;
    /**
     * Админ группы. Поле не может быть null
     */
    private Person groupAdmin;

    private User user;

    /**
     * Конструктор без параметров
     */
    public StudyGroup(){};

    /**
     * Конструктор со всеми нужными параметрами
     * @param name имя
     * @param coordinates координаты
     * @param studentsCount количество студентов
     * @param expelledStudents отчисленные студенты
     * @param shouldBeExpelled студенты, которых стоит отчислить
     * @param semesterEnum семестр
     * @param groupAdmin админ группы
     */
    public StudyGroup(String name, Coordinates coordinates, Long studentsCount,
                      Integer expelledStudents, int shouldBeExpelled, Semester semesterEnum, Person groupAdmin) throws NullValueException, NumberOutOfBoundsException {
        setName(name); setCoordinates(coordinates); setStudentsCount(studentsCount);
        setExpelledStudents(expelledStudents); setShouldBeExpelled(shouldBeExpelled);
        setSemesterEnum(semesterEnum); setGroupAdmin(groupAdmin);
        creationDate = LocalDateTime.now();
        id = 0L;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    /**
     * Метод, возвращающий id группы
     * @return id
     */
    public Long getId() {
        return id;
    }
    /**
     * Метод, возвращающий координаты группы
     * @return coordinates
     */
    public Coordinates getCoordinates(){
        return  coordinates;
    }

    /**
     * Метод, возвращающий имя группы
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Метод, возвращающий дату создания группы
     * @return creationDate
     */
    public LocalDateTime getCreationDate(){
        return creationDate;
    }

    /**
     * Метод, возвращающий количество студентов
     * @return studentsCount
     */
    public Long getStudentsCount() {
        return studentsCount;
    }

    /**
     * Метод, возвращающий количество отчисленных студентов
     * @return expelledStudents
     */
    public Integer getExpelledStudents() {
        return expelledStudents;
    }

    /**
     * Метод, возвращающий количество студентов, которые должны быть отчислены
     * @return shouldBeExpelled
     */
    public int getShouldBeExpelled() {
        return shouldBeExpelled;
    }

    /**
     * Метод, возвращающий текущий семестр
     * @return semesterEnum
     */
    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    /**
     * Метод, возвращающий админа группы
     * @return groupAdmin
     */
    public Person getGroupAdmin() {
        return groupAdmin;
    }

    /**
     * Метод, устанавливающий значение id
     * @param id уникальный идентификатор
     */
    public void setId(Long id) throws NullValueException, NumberOutOfBoundsException{
        if (id == null){
            throw new NullValueException();
        }
        if (id <= 0){
            throw new NumberOutOfBoundsException();
        }
        this.id = id;
    }

    public void setCreationDate(LocalDateTime localDateTime){
        creationDate = localDateTime;
    }

    /**
     * Метод, устанавливающий значение name
     * @param name имя
     */
    public void setName(String name) throws NullValueException {
        if (name.equals("")){
            throw new NullValueException();
        }
        this.name = name;
    }

    /**
     * Метод, устанавливающий значение coordinates
     * @param coordinates координаты
     */
    public void setCoordinates(Coordinates coordinates) throws NullValueException {
        if (coordinates == null){
            throw new NullValueException();
        }
        this.coordinates = coordinates;
    }


    /**
     * Метод, устанавливающий значение studentsCount
     * @param studentsCount количество студентов
     */
    public void setStudentsCount(Long studentsCount) throws NullValueException, NumberOutOfBoundsException {
        if (studentsCount == null){
            throw new NullValueException();
        }
        if (studentsCount <= 0){
            throw new NumberOutOfBoundsException();
        }
        this.studentsCount = studentsCount;
    }

    /**
     * Метод, устанавливающий значение expelledStudents
     * @param expelledStudents отчисленные студенты
     */
    public void setExpelledStudents(Integer expelledStudents) throws NumberOutOfBoundsException {
        if (expelledStudents == null){
            this.expelledStudents = expelledStudents;
        } else
        if (expelledStudents <= 0){
            throw new NumberOutOfBoundsException();
        }else this.expelledStudents = expelledStudents;
    }

    /**
     * Метод, устанавливающий значение shouldBeExpelled
     * @param shouldBeExpelled студенты, которых стоит отчислить
     */
    public void setShouldBeExpelled(int shouldBeExpelled) throws NumberOutOfBoundsException {
        if (shouldBeExpelled <= 0){
            throw new NumberOutOfBoundsException();
        }
        this.shouldBeExpelled = shouldBeExpelled;
    }

    /**
     * Метод, устанавливающий значение semesterEnum
     * @param semesterEnum семестр
     */

    public void setSemesterEnum(Semester semesterEnum) {
        this.semesterEnum = semesterEnum;
    }

    /**
     * Метод, устанавливающий значение groupAdmin
     * @param groupAdmin админ группы
     */
    public void setGroupAdmin(Person groupAdmin) throws NullValueException {
        if (groupAdmin == null){
            throw new NullValueException();
        }
        this.groupAdmin = groupAdmin;
    }

    /**
     * Метод, возвращающий строковое представление StudyGroup
     * @return string value of StudyGroup object
     */
    @Override
    public String toString() {
        String idS = "id = " + id;
        String nameS = "name = " + name;
        String coordinatesS = "coordinates = " + coordinates.toString();
        String creationDateS = "creationDate = " + creationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String studentsCountS = "studentsCount = " + studentsCount;
        String expelledStudentsS = "expelledStudents = " + expelledStudents;
        String shouldBeExpelledS = "shouldBeExpelled = " + shouldBeExpelled;
        String semesterEnumS = "semesterEnum = " + semesterEnum;
        String groupAdminS = "groupAdmin: " + groupAdmin.toString();
        String groupOwnerS = "groupOwner: " + user.getLogin();
        return idS + '\n' + nameS + '\n' + coordinatesS + '\n' + creationDateS + '\n' + studentsCountS + '\n' +
                expelledStudentsS + '\n' + shouldBeExpelledS + '\n' + semesterEnumS + '\n' + groupAdminS + '\n' + groupOwnerS;
    }

    /**
     * Метод, сравнивающий объект StudyGroup с другим объектом того же класса
     * @param obj объект для сравнения
     * @return 1; 0; -1 в зависимости от их отношения друг к другу
     */
    @Override
    public int compareTo(StudyGroup obj) {
        if ((getStudentsCount() - getNotNullExpelledStudents() - getShouldBeExpelled() * 0.5) - (obj.getStudentsCount() - obj.getExpelledStudents() - obj.getShouldBeExpelled() * 0.5) != 0){
            return (int) ((getStudentsCount() - getNotNullExpelledStudents() - getShouldBeExpelled() * 0.5) - (obj.getStudentsCount() - obj.getExpelledStudents() - obj.getShouldBeExpelled() * 0.5));
        }
        else if (this.getName().compareTo(obj.getName()) > 0) {
            return 1;
        } else if (this.getName().compareTo(obj.getName()) < 0) {
            return -1;
        } else if (this.getNotNullSemester().compareTo(obj.getNotNullSemester()) > 0) {
            return 1;
        } else if (this.getNotNullSemester().compareTo(obj.getNotNullSemester()) < 0) {
            return -1;
        } else if (this.getCreationDate().compareTo(obj.getCreationDate()) > 0) {
            return 1;
        } else if (this.getCreationDate().compareTo(obj.getCreationDate()) < 0) {
            return -1;
        } else if (this.getCoordinates().compareTo(obj.getCoordinates()) > 0) {
            return 1;
        } else if (this.getCoordinates().compareTo(obj.getCoordinates()) < 0) {
            return -1;
        } else return Integer.compare(this.getGroupAdmin().compareTo(obj.getGroupAdmin()), 0);
    }

    public static StudyGroup valueOf(String... arr) throws WrongAmountOfValuesException, NumberOutOfBoundsException, NullValueException {
        StudyGroup studyGroup = null;
        Integer expelled;
        Semester semester;
        if (arr.length == 16){
            if (arr[6].equals("")){
                expelled = null;
            }
            else {
                expelled = Integer.valueOf(arr[6]);
            }
            if (arr[8].equals("")){
                semester = null;
            }
            else {
                semester = Semester.valueOf(arr[8]);
            }
            studyGroup = new StudyGroup(arr[1],
                    Coordinates.valueOf(arr[2], arr[3]),
                    Long.valueOf(arr[5]), expelled,
                    Integer.parseInt(arr[7]), semester,
                    Person.valueOf(arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15]));
            studyGroup.setId(Long.valueOf(arr[0]));
            studyGroup.setCreationDate(LocalDateTime.parse(arr[4], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }

        if(arr.length == 14){
            if (arr[4].equals("")){
                expelled = null;
            }
            else {
                expelled = Integer.valueOf(arr[4]);
            }
            if (arr[6].equals("")){
                semester = null;
            }
            else {
                semester = Semester.valueOf(arr[6]);
            }
            studyGroup = new StudyGroup(arr[0],
                    Coordinates.valueOf(arr[1], arr[2]),
                    Long.valueOf(arr[3]), expelled,
                    Integer.parseInt(arr[5]), semester,
                    Person.valueOf(arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13]));
        }

        if (arr.length != 14 && arr.length != 16){
            throw new WrongAmountOfValuesException();
        }
        return studyGroup;
    }

    private Integer getNotNullExpelledStudents(){
        if (expelledStudents == null){
            return 0;
        }
        return expelledStudents;
    }

    private Semester getNotNullSemester(){
        if (semesterEnum == null){
            return Semester.values()[0];
        }
        return semesterEnum;
    }
}