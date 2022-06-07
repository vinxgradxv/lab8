package client;

import data.StudyGroup;
import javafx.concurrent.Task;

public class UpdateTask extends Task<StudyGroup[]> {
    @Override
    protected StudyGroup[] call() throws Exception {
        StudyGroup[] studyGroups  = LoginController.client.getElements().getStudyGroups();
        return studyGroups;
    }
}
