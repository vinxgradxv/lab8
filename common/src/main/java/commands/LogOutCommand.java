package commands;

import data.StudyGroup;
import data.User;
import exceptions.NumberOutOfBoundsException;
import exceptions.WrongAmountOfCoordinatesException;
import utils.CollectionManager;
import utils.Response;
import utils.ResponseType;

public class LogOutCommand extends Command{
    public LogOutCommand(){
        parametersCount = 0;
        isElementRequired = false;
    }

    @Override
    public String getName() {
        return "log_out";
    }

    @Override
    public String getInfo() {
        return "Выйти из аккаунта пользователя";
    }

    @Override
    public Response execute(Object param, StudyGroup studyGroup, CollectionManager studyGroupCollection, User user) throws NumberOutOfBoundsException, WrongAmountOfCoordinatesException {
        return new Response(ResponseType.USER, "Выход из аккаунта произведен успешно", null, null);
    }
}
