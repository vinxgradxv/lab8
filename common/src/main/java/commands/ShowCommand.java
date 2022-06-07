package commands;

import data.StudyGroup;
import data.User;
import utils.CollectionManager;
import utils.Response;
import utils.ResponseType;

import java.util.Scanner;
/**
 * Класс, описывающий команду show
 */
public class ShowCommand extends Command{
    /**
     * Конструктор
     */
    public ShowCommand(){
        parametersCount = 0;
    }
    /**
     * Метод, возвращающий имя команды
     * @return name of the command
     */
    @Override
    public String getName() {
        return "show";
    }
    /**
     * Метод, возвращающий информацию о команде
     * @return information about the command
     */
    @Override
    public String getInfo() {
        return "Выводит все элементы коллекции в строковом представлении";
    }


    public Response execute(Object param, StudyGroup studyGroup, CollectionManager studyGroupCollection, User user){
        StudyGroup[] studyGroups = new StudyGroup[studyGroupCollection.getStudyGroupHashTable().size()];
        if (studyGroupCollection.getStudyGroupHashTable().isEmpty()){
            return new Response(ResponseType.ERROR, "В коллекции нет элементов", user, studyGroups);
        }
        String result = "";
        int i = 0;
        for (Long key: studyGroupCollection.getStudyGroupHashTable().keySet()){
            studyGroups[i] = studyGroupCollection.getStudyGroupHashTable().get(key);
            i += 1;
            result += "Ключ = " + key + '\n' + studyGroupCollection.getStudyGroupHashTable().get(key).toString()
                    + "\n" +  separatorString;
        }
        return new Response(ResponseType.RESULT, result, user, studyGroups);
    }
}
