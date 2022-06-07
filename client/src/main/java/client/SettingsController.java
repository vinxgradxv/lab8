package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

public class SettingsController {


    @FXML
    public ToggleGroup languange;

    @FXML
    public RadioButton ruButton;
    @FXML
    public RadioButton enButton;
    @FXML
    public RadioButton esButton;
    @FXML
    public RadioButton itButton;
    @FXML
    public RadioButton srButton;

    private final RadioButton[] radioButtons = {ruButton, enButton, srButton, itButton, esButton};
    private final Locale[] locales = {Locale.US, new Locale("ru", "RU"), new Locale("sr", "SR"),Locale.ITALY, new Locale("es", "NI")};
    private final String[] languages = {"Русский", "English", "Српски jезик", "Italiano", "Español (Nicaragua)"};

    @FXML
    protected void onContButtonAction(ActionEvent event){
                try {
                    Locale locale = getPickedLocale();
                    ResourceBundle rb = ResourceBundle.getBundle("localization.Resource", locale);
                    Parent root = FXMLLoader.load(getClass().getResource("table.fxml"), rb);
                    Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setMinHeight(400);
                    stage.setMinWidth(600);
                    LoginController.rb = rb;
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }


    }

    private Locale getPickedLocale(){
        if (ruButton.isSelected()){
            return locales[1];
        }
        if (enButton.isSelected()){
            return locales[0];
        }
        if (itButton.isSelected()){
            return locales[3];
        }
        if (esButton.isSelected()){
            return locales[4];
        }
        if (srButton.isSelected()){
            return locales[2];
        }
        return null;
    }

}
