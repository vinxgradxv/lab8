package commands;

import data.StudyGroup;
import data.User;
import exceptions.NumberOutOfBoundsException;
import exceptions.WrongAmountOfCoordinatesException;
import utils.CollectionManager;
import utils.Response;
import utils.ResponseType;

public class SignUpCommand extends Command{

    public SignUpCommand(){
        parametersCount = 0;
        isElementRequired = false;
        isUserRequired = true;
        isAuthorizationRequired = false;
    }

    @Override
    public String getName() {
        return "sign_up";
    }

    @Override
    public String getInfo() {
        return "Зарегистрировать нового пользователя";
    }

    @Override
    public Response execute(Object param, StudyGroup studyGroup, CollectionManager studyGroupCollection, User user) throws NumberOutOfBoundsException, WrongAmountOfCoordinatesException {
        User aUser = studyGroupCollection.createNewUser(user);
        if(aUser != null)
        return new Response(ResponseType.USER, "Новый пользователь " + aUser.getLogin() + " создан и произведена аутентификация", aUser, null);
        else return new Response(ResponseType.USER, "Пользователь с таким логином уже существует", aUser, null);
    }
}
