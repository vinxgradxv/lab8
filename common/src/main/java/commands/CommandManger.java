package commands;

import data.Semester;
import exceptions.CommandNotFoundException;
import exceptions.NumberOutOfBoundsException;

import java.util.*;

/**
 * Класс для взаимодействия с командами
 */

public class CommandManger {
    /**
     * Список всех команд
     */
    private static List<Command> commands = new ArrayList<>();

    /**
     * Метод, добавляющий команды в список
     */
    public static void setCommands(){
        commands.add(new HelpCommand());
        commands.add(new InfoCommand());
        commands.add(new ShowCommand());
        commands.add(new InsertCommand());
        commands.add(new UpdateCommand());
        commands.add(new RemoveKeyCommand());
        commands.add(new ClearCommand());
        commands.add(new ExecuteScriptCommand());
        commands.add(new ExitCommand());
        commands.add(new RemoveLowerCommand());
        commands.add(new ReplaceIfGraterCommand());
        commands.add(new RemoveGreaterKeyCommand());
        commands.add(new SumCommand());
        commands.add(new CountGreaterSemesterCommand());
        commands.add(new FilterLessStCountCommand());
        commands.add(new LogInCommand());
        commands.add(new LogOutCommand());
        commands.add(new SignUpCommand());
    }

    /**
     *Метод, возвращающий список всех команд
     * @return list of all commands
     */
    public static List<Command> getCommands(){
        return commands;
    }

    public Object getParameterObject(String input) throws IllegalArgumentException, NumberOutOfBoundsException{
        Object param = null;
        switch (input.split(" ")[0]) {
            case "count_greater_than_semester_enum":
                param = Semester.valueOf(input.split(" ")[1]);
                break;
                case "filter_less_than_students_count":
                case "update":
                    param = Long.valueOf(input.split(" ")[1]);
                    if ((Long) param < 1) {
                        throw new NumberOutOfBoundsException();
                    }
                    break;
                case "execute_script":
                    param = input.split(" ")[1];
                    break;
                default:
                    param = Long.valueOf(input.split(" ")[1]);
                    if ((Long) param < 1) {
                        throw new NumberOutOfBoundsException();
                    }
                    break;
            }
            return param;
    }


}
