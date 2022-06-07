package client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class InfoController implements Initializable {

    @FXML
    protected Label infoLabel;
    protected static String text;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        infoLabel.setText(text);
    }
}
