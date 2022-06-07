package commands;

import data.StudyGroup;
import data.User;
import utils.CollectionManager;
import utils.Response;
import utils.ResponseType;

import java.util.Scanner;
/**
 * Класс, описывающий команду sum_of_students_count
 */
public class SumCommand extends Command{
    /**
     * Конструктор
     */
    public SumCommand(){
        parametersCount = 0;
    }
    /**
     * Метод, возвращающий имя команды
     * @return name of the command
     */
    @Override
    public String getName() {
        return "sum_of_students_count";
    }
    /**
     * Метод, возвращающий информацию о команде
     * @return information about the command
     */
    @Override
    public String getInfo() {
        return "Выводит сумму значений поля studentsCount всех элементов коллекции";
    }


    public Response execute(Object param, StudyGroup studyGroup, CollectionManager studyGroupCollection, User user){
        long sum = 0;
        if (studyGroupCollection.getSize() == 0){
            return new Response(ResponseType.ERROR, "В коллекции нет элементов", user, null);
        }
        for (StudyGroup st: studyGroupCollection.getStudyGroupHashTable().values()){
            sum += st.getStudentsCount();
        }
        return new Response(ResponseType.RESULT, "Сумма полей studentsCount всех элементов = " + sum, user, null);
    }
}
