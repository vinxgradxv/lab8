package commands;

import data.StudyGroup;
import data.User;
import exceptions.NumberOutOfBoundsException;
import exceptions.WrongAmountOfCoordinatesException;
import utils.CollectionManager;
import utils.Response;
import utils.ResponseType;

public class LogInCommand extends Command{

    public LogInCommand(){
        parametersCount = 0;
        isElementRequired = false;
        isUserRequired = true;
        isAuthorizationRequired = false;
    }

    @Override
    public String getName() {
        return "log_in";
    }

    @Override
    public String getInfo() {
        return "Авторизация пользователя";
    }

    @Override
    public Response execute(Object param, StudyGroup studyGroup, CollectionManager studyGroupCollection, User user) throws NumberOutOfBoundsException, WrongAmountOfCoordinatesException {
        if (user == null){
            return new Response(ResponseType.ERROR, "Вы уже авторизованы в системе", user, null);
        }
        User aUser = studyGroupCollection.getUser(user);
        if (aUser == null){
            return new Response(ResponseType.ERROR, "Неверное имя пользователя или пароль", user, null);
        }
        return new Response(ResponseType.USER, "Авторизация прошла успешно", user, null);
    }
}
