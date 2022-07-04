package client;

import data.Semester;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Response;

import java.text.NumberFormat;
import java.util.ResourceBundle;


public class ParameterAskerController {
    protected static Semester semester;
    @FXML
    protected Label errorLabel = null;
    @FXML
    protected TextField parameterField;

    protected static ResourceBundle rb;

    @FXML
    public void onSendButtonAction(){
        Task<Void> onSendAction = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    semester = Semester.valueOf(parameterField.getText());
                    Response response = LoginController.client.getSemesterGreaterCount(semester);
                    Platform.runLater(() -> {
                        errorLabel.setText(rb.getString("answer:") + " " + NumberFormat.getInstance(rb.getLocale()).format(Integer.valueOf(response.getMessage())));
                        errorLabel.setVisible(true);
                        Stage stage = (Stage) errorLabel.getScene().getWindow();

                    });
                } catch (IllegalArgumentException e) {
                    errorLabel.setText(rb.getString("wrong parameter"));
                    errorLabel.setVisible(true);
                }
                return null;

            }
        };
        Thread th = new Thread(onSendAction);
        th.setDaemon(true);
        th.start();
    }
}
