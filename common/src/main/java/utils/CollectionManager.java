package utils;

import data.StudyGroup;
import data.User;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Hashtable;

public interface CollectionManager {
    int clean();
    Hashtable<Long, StudyGroup> getStudyGroupHashTable();
    LocalDateTime getInitializationDate();
    int getSize();
    Class getType();
    boolean add(Long key, StudyGroup studyGroup);
    boolean remove(Long key);
    void setFileFromCollection() throws FileNotFoundException;
    Long getMinFreeId();
    boolean isIdUsed(Long id);
    void addIdToUsed(Long id);
    void removeIdFromUsed(Long id);
    User createNewUser(User user);
    User getUser(User user);
    void setCurrentUser(User user);
    boolean updateStudyGroup(Long key, StudyGroup newGroup, StudyGroup oldGroup);
}
