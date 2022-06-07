package client;

import data.Semester;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sun.rmi.runtime.Log;
import utils.Asker;
import utils.Response;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ResourceBundle;

public class StCountAskerController {
    protected static Long studentsCount;
    @FXML
    protected Label errorLabel = null;
    @FXML
    protected TextField parameterField;

    protected static ResourceBundle rb;

    @FXML
    public void onSendButtonAction(){
        try {
            Asker asker = new Asker();
            studentsCount = asker.ask(Long::valueOf, arg -> arg > 0, parameterField.getText(), false);
            if (!asker.response.equals("")){
                errorLabel.setText(rb.getString("wrong parameter"));
                errorLabel.setVisible(true);
            }
            else {
                errorLabel.setVisible(false);
                Response response = null;
                while (true) {
                    if (!TableController.isInRequest) {
                        response = LoginController.client.filterStudentsCount(studentsCount);
                        break;
                    }
                }
                    TableAnswerController.rb = rb;
                    System.out.println(response.getStudyGroups().length);
                    TableAnswerController.studyGroups = response.getStudyGroups();
                    Stage stage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("tableAnswer.fxml"), rb);
                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.show();


            }
        }catch (IllegalArgumentException e){
            errorLabel.setText(rb.getString("wrong parameter"));
            errorLabel.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
