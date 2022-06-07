package utils;

import data.StudyGroup;
import data.User;

import java.io.Serializable;

public class Response implements Serializable {
    private final ResponseType type;
    private final String message;
    private final User user;
    private final StudyGroup[] studyGroups;

    public Response(ResponseType type, String message, User user, StudyGroup[] studyGroups){
        this.type = type;
        this.message = message;
        this.user = user;
        this.studyGroups = studyGroups;
    }

    public ResponseType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public User getUser(){return user;}

    public StudyGroup[] getStudyGroups(){return studyGroups;}
}
