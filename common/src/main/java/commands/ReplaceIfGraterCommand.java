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
 * Класс, описывающий команду replace_if_greater
 */
public class ReplaceIfGraterCommand extends Command{
    /**
     * Конструктор
     */
    public ReplaceIfGraterCommand(){
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
        return "replace_if_greater";
    }
    /**
     * Метод, возвращающий информацию о команде
     * @return information about the command
     */
    @Override
    public String getInfo() {
        return "Заменяет значение по ключу, если новое значение больше старого";
    }


    public Response execute(Object param, StudyGroup studyGroup, CollectionManager studyGroupCollection, User user) throws NumberOutOfBoundsException, WrongAmountOfCoordinatesException {
        Long longParam = (Long) param;
        if (studyGroupCollection.getStudyGroupHashTable().get(longParam).getUser().getLogin().equals(user.getLogin())) {
            if (!studyGroupCollection.getStudyGroupHashTable().containsKey(longParam)) {
                return new Response(ResponseType.ERROR, "В коллекции нет элемента с таким ключом", user, null);
            }

            if (studyGroupCollection.getStudyGroupHashTable().get(longParam).compareTo(studyGroup) < 0) {
                try {
                    studyGroup.setId(studyGroupCollection.getStudyGroupHashTable().get(longParam).getId());
                } catch (NullValueException e) {
                    System.err.println("id не может быть null");
                }
                studyGroupCollection.add(longParam, studyGroup);
                return new Response(ResponseType.RESULT, "Элемент был обновлен", user, null);
            }
            return new Response(ResponseType.RESULT, "Элемент не был обновлен", user, null);
        }
        return new Response(ResponseType.ERROR, "У вас нет прав на изменение этого элемента", user, null);
    }
}
