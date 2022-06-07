package utils;

import commands.Command;
import data.StudyGroup;
import data.User;

import java.io.Serializable;

public class Message implements Serializable {
    private final Command command;
    private final Object param;
    private final StudyGroup studyGroup;
    private final User user;

    public Message(Command command, Object param, StudyGroup studyGroup, User user){
        this.command = command;
        this.param = param;
        this.studyGroup = studyGroup;
        this.user = user;
    }

    public Command getCommand(){return command;}
    public Object getParam(){return param;}
    public StudyGroup getStudyGroup(){return studyGroup;}
    public User getUser(){return user;}
}
