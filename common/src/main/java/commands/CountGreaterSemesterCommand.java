package commands;

import data.Semester;
import data.StudyGroup;

import data.User;
import utils.CollectionManager;
import utils.Response;
import utils.ResponseType;


import java.util.Scanner;

/**
 * Класс, описывающий команду count_greater_than_semester_enum
 */
public class CountGreaterSemesterCommand extends Command{
    /**
     * Конструктор
     */
    public CountGreaterSemesterCommand(){
        parametersCount = 1;
        setParameters("semesterEnum");
    }

    /**
     * Метод, возвращающий имя команды
     * @return name of the command
     */
    @Override
    public String getName() {
        return "count_greater_than_semester_enum";
    }
    /**
     * Метод, возвращающий информацию о команде
     * @return information about the command
     */
    @Override
    public String getInfo() {
        return "Выводит количество элементов, у которых semesterEnum больше заданного";
    }


    public Response execute(Object param, StudyGroup studyGroup, CollectionManager studyGroupCollection, User user){
        Semester semesterParam = (Semester) param;
        int count = 0;
        for (StudyGroup st: studyGroupCollection.getStudyGroupHashTable().values()){
            if (st.getSemesterEnum().compareTo(semesterParam) > 0){
                count += 1;
            }
        }
        return new Response(ResponseType.RESULT, String.valueOf(count), user, null);
    }
}
