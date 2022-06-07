package commands;

import data.StudyGroup;
import data.User;
import exceptions.NumberOutOfBoundsException;
import exceptions.WrongAmountOfCoordinatesException;
import utils.CollectionManager;
import utils.Response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Абстрактный класс, описывающий поведение команд
 */
public abstract class Command implements Serializable {
    List<String> parameters = new ArrayList<>();
    /**
     * Строка для разделения выводов
     */
    public final String separatorString = "-------------------------";
    /**
     * Количество параметров
     */
    protected int parametersCount;

    public boolean isElementRequired = false;

    public boolean isUserRequired = false;

    boolean isAuthorizationRequired = true;

    /**
     * Метод возвращающий количество параметров
     * @return parametersCount
     */
    public int getParametersCount() {
        return parametersCount;
    }

    /**
     * Метод, возвращающий имя
     * @return name
     */
    public abstract String getName();

    /**
     * Метод, возвращающий информацию
     * @return info
     */
    public abstract String getInfo();


    public abstract Response execute(Object param, StudyGroup studyGroup, CollectionManager studyGroupCollection, User user) throws NumberOutOfBoundsException, WrongAmountOfCoordinatesException;

    void setParameters(String... param){
        parameters.addAll(Arrays.asList(param));
    }
    public String getParameters(){
        String conc = "";
        for (String s:parameters){
            conc = conc + s + " ";
        }
        return conc;
    }

    public boolean isAuthorizationRequired() {
        return isAuthorizationRequired;
    }
}
