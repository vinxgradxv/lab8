package server;

import commands.Command;
import exceptions.NumberOutOfBoundsException;
import exceptions.WrongAmountOfCoordinatesException;
import org.apache.logging.log4j.Logger;
import utils.Message;
import utils.Response;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommandExecutor implements Runnable{

    private final Message message;
    private final SendManager sm;
    private StudyGroupCollection studyGroupCollection;
    private final ExecutorService newCachedThreadPool;
    private final Logger logger;

    public CommandExecutor(Message message, SendManager sm, StudyGroupCollection studyGroupCollection, Logger logger){
        this.message = message;
        this.sm = sm;
        this.studyGroupCollection = studyGroupCollection;
        newCachedThreadPool = Executors.newCachedThreadPool();
        this.logger = logger;
    }

    @Override
    public void run() {
        try {
            Response response = message.getCommand().execute(message.getParam(), message.getStudyGroup(),
                    studyGroupCollection, message.getUser());
            logger.info("Результат выполнения команды получен");
            newCachedThreadPool.submit(() -> {
                try {
                    sm.sendResponse(response);
                } catch (IOException e) {
                    logger.error("Произошла ошибка при отправке ответа клиенту");
                }
            }).get();
        } catch (NumberOutOfBoundsException e) {
        } catch (WrongAmountOfCoordinatesException e) {
        } catch (ExecutionException e) {
            logger.error("При исполнении потока произошла ошибка");
        } catch (InterruptedException e) {
            logger.error("Выполнение потока неожиданно прервалось");
        }
    }
}
