package commands;

import data.StudyGroup;
import data.User;
import exceptions.NumberOutOfBoundsException;
import utils.CollectionManager;
import utils.Response;
import utils.ResponseType;
import utils.ValidationClass;

import java.util.Scanner;
/**
 * Класс, описывающий команду remove_grater_key
 */
public class RemoveGreaterKeyCommand extends Command{
    /**
     * Конструктор
     */
    public RemoveGreaterKeyCommand(){
        parametersCount = 1;
        setParameters("key");
    }
    /**
     * Метод, возвращающий имя команды
     * @return name of the command
     */
    @Override
    public String getName() {
        return "remove_greater_key";
    }
    /**
     * Метод, возвращающий информацию о команде
     * @return information about the command
     */
    @Override
    public String getInfo() {
        return "Удаляет из коллекции все элементы, ключ которых превышает заданный";
    }


    public Response execute(Object param, StudyGroup studyGroup, CollectionManager studyGroupCollection, User user) throws NumberOutOfBoundsException {
        Long longParam = (Long) param;
        String result = "";
        int count = 0;
        for (Long key: studyGroupCollection.getStudyGroupHashTable().keySet()){
            if (key > longParam && studyGroupCollection.getStudyGroupHashTable().get(key).getUser().getLogin().equals(user.getLogin())){
                count += 1;
                result += "Элемент с id = " + studyGroupCollection.getStudyGroupHashTable().get(key).getId() + " удален из коллекции\n";
                studyGroupCollection.remove(key);
            }
            else if (key > longParam){
                result += "Элемент с id = " + studyGroupCollection.getStudyGroupHashTable().get(key).getId() + " не удален из-за отсутствия прав владельца\n";
            }
        }
        return new Response(ResponseType.RESULT, result + "Из коллекции было удалено " + count + " элементов", user, null);
    }
}
