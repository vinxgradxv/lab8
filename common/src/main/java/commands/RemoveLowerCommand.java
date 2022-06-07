package commands;

import data.StudyGroup;
import data.User;
import exceptions.NumberOutOfBoundsException;
import exceptions.WrongAmountOfCoordinatesException;
import utils.CollectionManager;
import utils.Response;
import utils.ResponseType;


/**
 * Класс, описывающий команду remove_lower
 */
public class RemoveLowerCommand extends Command{
    /**
     * Конструктор
     */
    public RemoveLowerCommand(){
        parametersCount = 0;
        isElementRequired = true;
    }
    /**
     * Метод, возвращающий имя команды
     * @return name of the command
     */
    @Override
    public String getName() {
        return "remove_lower";
    }
    /**
     * Метод, возвращающий информацию о команде
     * @return information about the command
     */
    @Override
    public String getInfo() {
        return "Удаляет из коллекции элементы, меньшие заданного";
    }

    public Response execute(Object param, StudyGroup studyGroup, CollectionManager studyGroupCollection, User user) throws NumberOutOfBoundsException, WrongAmountOfCoordinatesException {
        int count = 0;
        for (Long key: studyGroupCollection.getStudyGroupHashTable().keySet()){
            if (studyGroup.compareTo(studyGroupCollection.getStudyGroupHashTable().get(key)) > 0 && studyGroupCollection.getStudyGroupHashTable().get(key).getUser().getLogin().equals(user.getLogin())){
                studyGroupCollection.removeIdFromUsed(studyGroupCollection.getStudyGroupHashTable().get(key).getId());
                studyGroupCollection.remove(key);
                count += 1;
            }
        }
        return new Response(ResponseType.RESULT, "Из коллекции было удалено " + count + " элементов", user, null);
    }
}
