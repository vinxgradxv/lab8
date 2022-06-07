package client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import jdk.jfr.Frequency;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

public class SumController implements Initializable {
    @FXML
    Label sumLabel;

    protected static int sum = 0;
    protected static ResourceBundle rb;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sumLabel.setText(NumberFormat.getInstance(rb.getLocale()).format(sum));
    }
}
