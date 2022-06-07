package commands;

import data.StudyGroup;
import data.User;
import utils.CollectionManager;
import utils.Response;
import utils.ResponseType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * Класс, описывающий команду exit
 */
public class ExitCommand extends Command{
    /**
     * Конструктор
     */
    public ExitCommand(){
        parametersCount = 0;
    }
    /**
     * Метод, возвращающий имя команды
     * @return name of the command
     */
    @Override
    public String getName() {
        return "exit";
    }
    /**
     * Метод, возвращающий информацию о команде
     * @return information about the command
     */
    @Override

    public String getInfo() {
        return "Прекращает выполнение программы";
    }


    public Response execute(Object param, StudyGroup studyGroup, CollectionManager studyGroupCollection, User user) {
        try {
            studyGroupCollection.setFileFromCollection();
            return new Response(ResponseType.EXIT, "Коллекция сохранена в файл", user, null);
        }catch (FileNotFoundException e){
            return new Response(ResponseType.EXIT, "Файл в который должна была записаться коллекция недоступен", user, null);
        }
    }
}
