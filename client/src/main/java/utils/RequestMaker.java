package utils;

import client.Client;
import commands.Command;
import commands.CommandManger;
import data.StudyGroup;
import data.User;
import exceptions.AuthorizationException;
import exceptions.NullValueException;
import exceptions.NumberOutOfBoundsException;

import java.io.IOException;

public class RequestMaker {
    public Message getCommandFromInput(String input, CommandManger commandManger, IOManager ioManager) throws NumberOutOfBoundsException, IOException, NullValueException {
        Command properCommand = null;
        Object parameter = null;
        StudyGroup studyGroup = null;
        User user = Client.getCurrentUser();
        try {
            for (Command command : CommandManger.getCommands()) {
                if (input.split(" ")[0].equals(command.getName())) {
                    properCommand = command;
                    break;
                }
            }

            if (properCommand.isAuthorizationRequired() && user == null){
                throw new AuthorizationException();
            }

            if (ioManager.getFileMode()){
                ioManager.println("Производится выполнение команды " + input + " из скрипта");
            }

            if (properCommand.isUserRequired == true){
                if (properCommand.getName().equals("log_in") && Client.getCurrentUser() != null){
                    user = null;
                }
                //else user = new AskUser(ioManager).getUserFromInput();
            }


            if (properCommand.getParametersCount() == 1) {
                parameter = commandManger.getParameterObject(input);
            }

            if (properCommand.isElementRequired) {
                // studyGroup = new AskStudyGroup(ioManager).getStudyGroupFromUser();
                studyGroup.setUser(user);
            }



            return new Message(properCommand, parameter, studyGroup, user);
        }catch (NumberFormatException e){
            ioManager.printerr("Параметр не входит в установленные рамки");
        }
        catch (IndexOutOfBoundsException e){
            ioManager.printerr("Для этой команды необходим параметр");
        }catch (IllegalArgumentException e){
            ioManager.printerr("Неверный формат параметра");
        }catch (AuthorizationException e){
            ioManager.printerr("Для выполнения команды необходимо авторизоваться");
        }
        return null;
    }

}
