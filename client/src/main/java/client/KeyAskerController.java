package client;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utils.Asker;
import utils.Response;

import java.io.IOException;
import java.util.ResourceBundle;

public class KeyAskerController {

    protected static Long key;
    @FXML
    protected Label errorLabel = null;
    @FXML
    protected TextField parameterField;

    public static ResourceBundle rb;

    @FXML
    public void onSendButtonAction() {
        try {
            Asker asker = new Asker();
            key = asker.ask(Long::valueOf, arg -> arg > 0, parameterField.getText(), false);
            if (!asker.response.equals("")) {
                errorLabel.setText(rb.getString("wrong parameter"));
                errorLabel.setVisible(true);
            } else {
                errorLabel.setVisible(false);
                Response response = null;
                response = LoginController.client.removeGreaterKey(key);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(rb.getString("remove greater key"));
                alert.setHeaderText(rb.getString("answer:"));
                alert.setContentText(rb.getString("success"));
                alert.showAndWait();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
