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
 * Класс, описывающий команду execute_script
 */
public class ExecuteScriptCommand extends Command{
    /**
     * constructor
     */
    public ExecuteScriptCommand(){
        parametersCount = 1;
        setParameters("file_name");
    }

    /**
     * Метод, возвращающий имя команды
     * @return name of the command
     */
    @Override
    public String getName() {
        return "execute_script";
    }
    /**
     * Метод, возвращающий информацию о команде
     * @return information about the command
     */
    @Override
    public String getInfo() {
        return "исполняет скрипт из файла";
    }


    public Response execute(Object param, StudyGroup studyGroup, CollectionManager studyGroupCollection, User user){
        return new Response(ResponseType.SCRIPT, (String) param, user, null);
    }
}
