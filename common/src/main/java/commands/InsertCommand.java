package commands;

import data.StudyGroup;
import data.User;
import exceptions.NullValueException;
import exceptions.NumberOutOfBoundsException;
import exceptions.WrongAmountOfCoordinatesException;
import utils.CollectionManager;
import utils.Response;
import utils.ResponseType;
import utils.ValidationClass;

import java.util.Scanner;
/**
 * Класс, описывающий команду insert
 */
public class InsertCommand extends Command{
    /**
     * Конструктор
     */
    public InsertCommand(){
        isElementRequired = true;
        parametersCount = 1;
        setParameters("key");
    }
    /**
     * Метод, возвращающий имя команды
     * @return name of the command
     */
    @Override
    public String getName() {
        return "insert";
    }
    /**
     * Метод, возвращающий информацию о команде
     * @return information about the command
     */
    @Override
    public String getInfo() {
        return "Добавляет новый элемент с заданным ключом";
    }

    public Response execute(Object param, StudyGroup studyGroup, CollectionManager studyGroupCollection, User user) throws NumberOutOfBoundsException, WrongAmountOfCoordinatesException
    {
            Long longParam = (Long) param;
            studyGroup.setUser(user);
            if (studyGroupCollection.getStudyGroupHashTable().containsKey(longParam)) {
                return new Response(ResponseType.ERROR, "element with this key already exists", user, null);
            }
            boolean res = studyGroupCollection.add(longParam, studyGroup);
            if (res) {
                return new Response(ResponseType.RESULT, "element added to the collection", user, null);
            }
            else return new Response(ResponseType.ERROR, "element was not added to the collection", user, null);
    }

}
