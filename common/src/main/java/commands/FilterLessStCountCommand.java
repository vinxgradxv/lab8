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
 * Класс, описывающий команду filter_less_than_students_count
 */
public class FilterLessStCountCommand extends Command{
    /**
     * Конструктор
     */
    public FilterLessStCountCommand(){
        parametersCount = 1;
        setParameters("studentsCount");
    }
    /**
     * Метод, возвращающий имя команды
     * @return name of the command
     */
    @Override
    public String getName() {
        return "filter_less_than_students_count";
    }
    /**
     * Метод, возвращающий информацию о команде
     * @return information about the command
     */
    @Override
    public String getInfo() {
        return "Выводит все элементы коллекции, значение поле studentsCount меньше, чем заданное";
    }

    public Response execute(Object param, StudyGroup studyGroup, CollectionManager studyGroupCollection, User user) throws NumberOutOfBoundsException {
        Long longParam = (Long) param;
        int count = 0;
        String result = "";
        for(StudyGroup st: studyGroupCollection.getStudyGroupHashTable().values()){
            if (st.getStudentsCount() < longParam){
                result += st.toString();
            }
        }

        return new Response(ResponseType.RESULT, result, user, null);

    }
}
