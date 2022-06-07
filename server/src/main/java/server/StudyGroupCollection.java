package server;

import data.StudyGroup;
import data.User;
import databaseInteraction.SQLStudyGroupCollectionManger;
import exceptions.NullValueException;
import exceptions.NumberOutOfBoundsException;
import server.ParserCSV;
import utils.CollectionManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class StudyGroupCollection implements CollectionManager {

    private Hashtable<Long, StudyGroup> studyGroupHashtable;
    private final LocalDateTime initializationDate;
    private HashSet<Long> usedId;
    private SQLStudyGroupCollectionManger sqlManager;
    private long curId;
    private User currentUser = null;

    public StudyGroupCollection(Connection dbConnection) throws FileNotFoundException, NumberOutOfBoundsException, SQLException, NullValueException {
        initializationDate = LocalDateTime.now();
        curId = 1;
        usedId = new HashSet<>();
        sqlManager = new SQLStudyGroupCollectionManger(dbConnection, this);
        studyGroupHashtable = sqlManager.getStudyGroupHashTable();
    }
    @Override
    public synchronized int clean() {
        int count = sqlManager.clean();
        if (count == -1){
            return -1;
        }
        else{
            Set<Long> set = studyGroupHashtable.keySet();
            for(Long key: set){
                if (studyGroupHashtable.get(key).getUser().getLogin().equals(currentUser.getLogin())){
                    studyGroupHashtable.remove(key);
                    count += 1;
                }
            }
            return count;
        }
    }

    @Override
    public Hashtable<Long, StudyGroup> getStudyGroupHashTable() {
        return studyGroupHashtable;
    }

    @Override
    public LocalDateTime getInitializationDate() {
        return initializationDate;
    }

    @Override
    public int getSize() {
        return studyGroupHashtable.size();
    }

    @Override
    public Class getType() {
        return studyGroupHashtable.getClass();
    }

    @Override
    public synchronized boolean add(Long key, StudyGroup studyGroup) {
        if(sqlManager.add(key, studyGroup)){
            studyGroupHashtable.put(key, studyGroup);
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean remove(Long key) {
        if (!studyGroupHashtable.containsKey(key)){
            return false;
        }
        studyGroupHashtable.remove(key);
        return true;
    }
    public void setFileFromCollection()  {}
    public void addIdToUsed(Long id){
        usedId.add(id);
    }
    public Long getMinFreeId(){
        while (usedId.contains(curId)){
            curId += 1;
        }
        return curId;
    }
    public boolean isIdUsed(Long id){
        return usedId.contains(id);
    }
    public void removeIdFromUsed(Long id){
        usedId.remove(id);
    }

    @Override
    public User createNewUser(User user) {
        return sqlManager.createNewUser(user);
    }

    @Override
    public User getUser(User user) {
        return sqlManager.getUser(user);
    }

    public void setCurrentUser(User user){
        this.currentUser = user;
        sqlManager.setCurrentUser(currentUser);
    }

    @Override
    public synchronized boolean updateStudyGroup(Long key, StudyGroup newGroup, StudyGroup oldGroup) {
        if(sqlManager.updateStudyGroup(key, newGroup, oldGroup)) {
            try {
                oldGroup.setName(newGroup.getName());
                oldGroup.setStudentsCount(newGroup.getStudentsCount());
                oldGroup.setExpelledStudents(newGroup.getExpelledStudents());
                oldGroup.setShouldBeExpelled(newGroup.getShouldBeExpelled());
                oldGroup.setSemesterEnum(newGroup.getSemesterEnum());
                studyGroupHashtable.put(key, oldGroup);
                return true;
            } catch (NumberOutOfBoundsException e) {
                e.printStackTrace();
            } catch (NullValueException e) {
                e.printStackTrace();
            }
            return false;
        }
        return false;
    }



}
