package server;

import commands.CommandManger;
import exceptions.NullValueException;
import exceptions.NumberOutOfBoundsException;
import exceptions.WrongAmountOfCoordinatesException;
import utils.Message;

import java.io.FileNotFoundException;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ResponseType;


public class Server {
    private final static int PORT = 5432;
    private static DatagramSocket socket;
    private static byte[] buf = new byte[2048];
    private static boolean running;
    private static StudyGroupCollection studyGroupCollection;
    private static Logger logger;
    private static UserManager userManager;
    private static Connection dbConnection;
    private static ExecutorService newCashedThreadPool;
    private static ExecutorService newFixedThreadPool;


    public static void main(String[] args) throws ClassNotFoundException, InterruptedException, NumberOutOfBoundsException, WrongAmountOfCoordinatesException {
        String path = "";
        try {
            userManager = new UserManager();
            logger = LogManager.getLogger("server");
            socket = new DatagramSocket(PORT);
            CommandManger.setCommands();
            logger.info("Установили команды, которые может исполнить сервер.");
            Class.forName("org.postgresql.Driver");
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите логин для подключения к БД");
            String login = scanner.nextLine();
            System.out.println("Введите пароль для подключения к БД");
            String password = scanner.nextLine();
            dbConnection = DriverManager.getConnection("jdbc:postgresql://localhost:1099/studs", login, password);
            studyGroupCollection = new StudyGroupCollection(dbConnection);
            logger.info("Инициализировали коллекцию по значениям из базы данных");
            logger.info("Сервер начинает работу");
            newCashedThreadPool = Executors.newCachedThreadPool();
            run();
        }
        catch (SocketException e){
            logger.error("Не получилось создать сокет");
        }
        catch (NullPointerException e) {
            logger.fatal("Нет такой переменной окружения");
        }
        catch (FileNotFoundException e){
            if (e.getMessage().contains("(Отказано в доступе)"))
                logger.fatal(e.getMessage() + ", получите права на чтения файла или запустите программу с другим файлом");
            else logger.fatal("Файл " + path + "Не найден");
        } catch (SQLException e) {
            logger.error("Произошла ошибка при подключении к базе данных");
        } catch (NullValueException e) {
            e.printStackTrace();
        }
    }

    public static void run() {
        running = true;
        while (running){
            try {
                newFixedThreadPool = Executors.newFixedThreadPool(10);
                ReceiveManager receiveManager = new ReceiveManager(socket, logger);
                logger.info("receiveManager инициализирован");
                execute(newCashedThreadPool.submit(receiveManager::receiveMessage).get(), receiveManager, studyGroupCollection);


                logger.info("Получен порт и адрес клиента");


                logger.info("Получен результат выполнения команды");


            }
             catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                logger.error("Ошибка при исполнении потока");
            }
        }
    }
    public static void execute(Message message, ReceiveManager receiveManager, StudyGroupCollection studyGroupCollection){
        InetAddress clientAddress = receiveManager.getAddress();
        int clientPort = receiveManager.getPort();
        logger.info("Получен порт и адрес клиента");
        SendManager sendManager = new SendManager(socket, clientAddress, clientPort);
        CommandExecutor commandExecutor = new CommandExecutor(message, sendManager, studyGroupCollection, logger);
        newFixedThreadPool.submit(commandExecutor);
    }
}
