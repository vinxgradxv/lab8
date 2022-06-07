package commands;



import data.StudyGroup;
import data.User;
import utils.CollectionManager;
import utils.Response;
import utils.ResponseType;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;
/**
 * Класс, описывающий команду info
 */
public class InfoCommand extends Command{
    /**
     * Конструктор
     */
    public InfoCommand(){
        parametersCount = 0;
        isAuthorizationRequired = false;
    }

    /**
     * Метод, возвращающий имя команды
     * @return name of the command
     */
    @Override
    public String getName() {
        return "info";
    }
    /**
     * Метод, возвращающий информацию о команде
     * @return information about the command
     */
    @Override
    public String getInfo() {
        return "Выводит информацию о коллекции";
    }


    public Response execute(Object param, StudyGroup studyGroup, CollectionManager studyGroupCollection, User user){
        return new Response(ResponseType.RESULT,
                "Дата инициализации: " + studyGroupCollection.getInitializationDate() + "\n"
        + "Размер коллекции " + studyGroupCollection.getSize() + "\n"
        + "Тип коллекции " + studyGroupCollection.getType(), user, null);
    }
}
