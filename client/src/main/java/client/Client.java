package client;

import commands.*;
import data.Semester;
import data.StudyGroup;
import data.User;
import exceptions.NullValueException;
import exceptions.NumberOutOfBoundsException;
import sun.nio.ch.ThreadPool;
import utils.*;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    private final static int PORT = 5432;
    private static IOManager ioManager;
    private static DatagramSocket socket;
    private static InetAddress address;
    private static RequestMaker requestMaker;
    private static CommandManger commandManger = new CommandManger();
    private static SendManager sendManager;
    private static ReceiveManager receiveManager;
    private static User currentUser = null;



    private static byte[] buf;

    public Client() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter writer = new PrintWriter(System.out, true);
            ioManager = new IOManager(reader, writer, "$");
            socket = new DatagramSocket();
            socket.setSoTimeout(15 * 1000);
            address = InetAddress.getByName("localhost");
            sendManager = new SendManager(address, socket, PORT);
            requestMaker = new RequestMaker();
            receiveManager = new ReceiveManager(socket);
            CommandManger.setCommands();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public Response logInUser(String login, String password){
        try {
            User user = new User(login, password);
            Message message = new Message(new LogInCommand(), null, null, user);
            sendManager.sendMessage(message);
            Response response = receiveManager.receiveMessage();
            currentUser = user;
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response signUpUser(String login, String password){
        try {
            User user = new User(login, password);
            Message message = new Message(new SignUpCommand(), null, null, user);
            sendManager.sendMessage(message);
            Response response = receiveManager.receiveMessage();
            currentUser = user;
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response getElements(){
        try {
            Message message = new Message(new ShowCommand(), null, null, currentUser);
            sendManager.sendMessage(message);
            Response response = receiveManager.receiveMessage();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response getInfo(){
        try {
            Message message = new Message(new InfoCommand(), null, null, currentUser);
            sendManager.sendMessage(message);
            return receiveManager.receiveMessage();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response getSemesterGreaterCount(Semester semester){
        try {
            Message message = new Message(new CountGreaterSemesterCommand(), semester, null, currentUser);
            sendManager.sendMessage(message);
            return receiveManager.receiveMessage();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response clear(){
        try {
            Message message = new Message(new ClearCommand(), null, null, currentUser);
            sendManager.sendMessage(message);
            return receiveManager.receiveMessage();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response insert(Long key, StudyGroup studyGroup){
        try {
            Message message = new Message(new InsertCommand(), key, studyGroup, currentUser);
            sendManager.sendMessage(message);
            return receiveManager.receiveMessage();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response update(Long key, StudyGroup studyGroup){
        try {
            Message message = new Message(new UpdateCommand(), key, studyGroup, currentUser);
            sendManager.sendMessage(message);
            return receiveManager.receiveMessage();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response filterStudentsCount(Long studentsCount){
        try {
            Message message = new Message(new FilterLessStCountCommand(), studentsCount, null, currentUser);
            sendManager.sendMessage(message);
            return receiveManager.receiveMessage();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response removeKey(Long key){
        try {
            Message message = new Message(new RemoveKeyCommand(), key, null, currentUser);
            sendManager.sendMessage(message);
            return receiveManager.receiveMessage();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Response removeGreaterKey(Long key){
        try {
            Message message = new Message(new RemoveGreaterKeyCommand(), key, null, currentUser);
            sendManager.sendMessage(message);
            return receiveManager.receiveMessage();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response removeLower(StudyGroup studyGroup){
        try {
            Message message = new Message(new RemoveLowerCommand(), null, studyGroup, currentUser);
            sendManager.sendMessage(message);
            return receiveManager.receiveMessage();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }




    public static void run() throws IOException, NumberOutOfBoundsException, NullValueException, ClassNotFoundException {
        boolean running = true;
        String input = "";
        while (running){
            try {
                ioManager.prompt();
                if (ioManager.getFileMode()) {
                    input = ioManager.readFile();
                }
                if (!ioManager.getFileMode()) {
                    input = ioManager.readLine();
                }
                Message message = requestMaker.getCommandFromInput(input, commandManger, ioManager);
                if (message != null) {
                    sendManager.sendMessage(message);
                    Response response = receiveManager.receiveMessage();
                    if (response.getType() == ResponseType.USER){
                        currentUser = response.getUser();
                        ioManager.println("sasat");
                        ioManager.println(response.getMessage());
                    } else
                    if (response.getType() == ResponseType.SCRIPT) {
                        ioManager.turnOnFileMode(response.getMessage());
                    } else if (response.getType() == ResponseType.EXIT) {
                        ioManager.println(response.getMessage());
                        ioManager.println("Работа клиента прекращена");
                        running = false;
                    } else if (response.getType() == ResponseType.ERROR) {
                        ioManager.printerr(response.getMessage());
                    } else {
                        ioManager.println(response.getMessage());
                    }
                }

            }catch (NullPointerException e){
                ioManager.printerr("Такой команды нет");
            }catch (SocketTimeoutException e){
                ioManager.printerr("Не удалось связаться с сервером, попробуйте ввести команду еще раз");
            }
        }
    }

    public static User getCurrentUser(){
        return currentUser;
    }



}
