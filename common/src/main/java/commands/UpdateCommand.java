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

import java.util.Hashtable;
import java.util.Objects;
import java.util.Scanner;
/**
 * Класс, описывающий команду update
 */
public class UpdateCommand extends Command{
    /**
     * Конструктор
     */
    public UpdateCommand(){
        isElementRequired = true;
        parametersCount = 1;
        setParameters("id");
    }
    /**
     * Метод, возвращающий имя команды
     * @return name of the command
     */
    @Override
    public String getName() {
        return "update";
    }
    /**
     * Метод, возвращающий информацию о команде
     * @return information about the command
     */
    @Override
    public String getInfo() {
        return "Обновляет значение коллекции с заданным id";
    }


    public Response execute(Object param, StudyGroup studyGroup, CollectionManager studyGroupCollection, User user) throws NumberOutOfBoundsException, WrongAmountOfCoordinatesException {
        Long longParam = (Long) param;
        StudyGroup st = null;
        Long kk = 0L;
        for (Long key : studyGroupCollection.getStudyGroupHashTable().keySet()) {
            if (studyGroupCollection.getStudyGroupHashTable().get(key).getId().equals(longParam)) {
                st = studyGroupCollection.getStudyGroupHashTable().get(key);
                kk = key;
                break;
            }
        }
        if (st != null) {
            if (st.getUser().getLogin().equals(user.getLogin())) {
                if(studyGroupCollection.updateStudyGroup(kk, studyGroup, st)){
                    return new Response(ResponseType.RESULT, "Элемент успешно изменен", user, null);
                }
                else return new Response(ResponseType.ERROR, "Элемент не был изменен", user, null);
            } else {
                return new Response(ResponseType.ERROR, "У вас нет прав на изменение данного элемента", user, null);
            }
        }
        else{
            return new Response(ResponseType.ERROR, "Элемента с таким ключом нет", user, null);
        }
    }
}
