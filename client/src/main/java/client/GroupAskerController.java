package client;

import data.*;
import exceptions.NullValueException;
import exceptions.NumberOutOfBoundsException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import utils.Asker;
import utils.Response;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

public class GroupAskerController implements Initializable {
    @FXML
    public TextField keyField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField coordinatesXField;
    @FXML
    public TextField coordinatesYField;
    @FXML
    public TextField studentsCountField;
    @FXML
    public TextField expelledStudentsField;
    @FXML
    public TextField shouldBeExpelledField;
    @FXML
    public TextField semesterField;
    @FXML
    public TextField adminNameField;
    @FXML
    public TextField heightField;
    @FXML
    public TextField hairColorField;
    @FXML
    public TextField nationalityField;
    @FXML
    public TextField locationXField;
    @FXML
    public TextField locationYField;
    @FXML
    public TextField locationZField;

    @FXML
    public Label keyError;
    @FXML
    public Label nameError;
    @FXML
    public Label coordinatesXError;
    @FXML
    public Label coordinatesYError;
    @FXML
    public Label studentsCountError;
    @FXML
    public Label expelledStudentsError;
    @FXML
    public Label shouldBeExpelledError;
    @FXML
    public Label semesterError;
    @FXML
    public Label adminNameError;
    @FXML
    public Label heightError;
    @FXML
    public Label hairColorError;
    @FXML
    public Label nationalityError;
    @FXML
    public Label locationXError;
    @FXML
    public Label locationYError;
    @FXML
    public Label locationZError;



    public static boolean updateMode;

    public static ResourceBundle rb;

    public static StudyGroup studyGroup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(updateMode){
            nameField.setText(studyGroup.getName());
            coordinatesXField.setText(NumberFormat.getInstance(rb.getLocale()).format(studyGroup.getCoordinates().getX()));
            coordinatesYField.setText(NumberFormat.getInstance(rb.getLocale()).format(studyGroup.getCoordinates().getY()));
            studentsCountField.setText(NumberFormat.getInstance(rb.getLocale()).format(studyGroup.getStudentsCount()));
            expelledStudentsField.setText(NumberFormat.getInstance(rb.getLocale()).format(studyGroup.getExpelledStudents()));
            shouldBeExpelledField.setText(NumberFormat.getInstance(rb.getLocale()).format(studyGroup.getShouldBeExpelled()));
            semesterField.setText(String.valueOf(studyGroup.getSemesterEnum()));
            adminNameField.setText(studyGroup.getGroupAdmin().getName());
            heightField.setText(NumberFormat.getInstance(rb.getLocale()).format(studyGroup.getGroupAdmin().getHeight()));
            hairColorField.setText(String.valueOf(studyGroup.getGroupAdmin().getHairColor()));
            nationalityField.setText(String.valueOf(studyGroup.getGroupAdmin().getNationality()));
            locationXField.setText(NumberFormat.getInstance(rb.getLocale()).format(studyGroup.getGroupAdmin().getLocation().getX()));
            locationYField.setText(NumberFormat.getInstance(rb.getLocale()).format(studyGroup.getGroupAdmin().getLocation().getY()));
            locationZField.setText(NumberFormat.getInstance(rb.getLocale()).format(studyGroup.getGroupAdmin().getLocation().getZ()));
        }
    }

    @FXML
    public void onSendButtonAction(){
        try {
            boolean perfectInput = true;
            Asker asker = new Asker();
            Long key = asker.ask(Long::valueOf, arg -> arg > 0, keyField.getText(), false);
            if (!asker.response.equals("")){
                perfectInput = false;
                keyError.setText(rb.getString("wrong parameter"));
                keyError.setVisible(true);
            }
            else keyError.setVisible(false);
            String name = asker.ask(arg -> arg, arg -> arg.length() > 0, nameField.getText(), false);
            if (!asker.response.equals("")){
                perfectInput = false;
                nameError.setText(rb.getString("wrong parameter"));
                nameError.setVisible(true);
            }
            else nameError.setVisible(false);
            Long coordinatesX = asker.ask(Long::valueOf, arg -> arg <= 722, coordinatesXField.getText(), false);
            if (!asker.response.equals("")){
                perfectInput = false;
                coordinatesXError.setText(rb.getString("wrong parameter"));
                coordinatesXError.setVisible(true);
            }
            else coordinatesXError.setVisible(false);
            Long coordinatesY = asker.ask(Long::valueOf, arg -> arg <= 102, coordinatesYField.getText(), false);
            if (!asker.response.equals("")){
                perfectInput = false;
                coordinatesYError.setText(rb.getString("wrong parameter"));
                coordinatesYError.setVisible(true);
            }
            else coordinatesYError.setVisible(false);
            Long studentsCount = asker.ask(Long::valueOf, arg -> arg > 0, studentsCountField.getText(), false);
            if (!asker.response.equals("")){
                perfectInput = false;
                studentsCountError.setText(rb.getString("wrong parameter"));
                studentsCountError.setVisible(true);
            }
            else studentsCountError.setVisible(false);
            Integer expelledStudents = asker.ask(Integer::valueOf, arg -> arg > 0, expelledStudentsField.getText(), true);
            if (!asker.response.equals("")){
                perfectInput = false;
                expelledStudentsError.setText(rb.getString("wrong parameter"));
                expelledStudentsError.setVisible(true);
            }
            else expelledStudentsError.setVisible(false);
            Integer shouldBeExpelled = asker.ask(Integer::valueOf, arg -> arg > 0, shouldBeExpelledField.getText(), false);
            if (!asker.response.equals("")){
                perfectInput = false;
                shouldBeExpelledError.setText(rb.getString("wrong parameter"));
                shouldBeExpelledError.setVisible(true);
            }
            else shouldBeExpelledError.setVisible(false);
            Semester semester = asker.ask(Semester::valueOf, arg -> true, semesterField.getText(), true);
            if (!asker.response.equals("")){
                perfectInput = false;
                semesterError.setText(rb.getString("wrong parameter"));
                semesterError.setVisible(true);
            }
            else semesterError.setVisible(false);
            String adminName = asker.ask(arg -> arg, arg -> arg.length() > 0, adminNameField.getText(), false);
            if (!asker.response.equals("")){
                perfectInput = false;
                adminNameError.setText(rb.getString("wrong parameter"));
                adminNameError.setVisible(true);
            }
            else adminNameError.setVisible(false);
            Long height = asker.ask(Long::valueOf, arg -> arg > 0, heightField.getText(), true);
            if (!asker.response.equals("")){
                perfectInput = false;
                heightError.setText(rb.getString("wrong parameter"));
                heightError.setVisible(true);
            }
            else heightError.setVisible(false);
            Color hairColor = asker.ask(Color::valueOf, arg -> true, hairColorField.getText(), false);
            if (!asker.response.equals("")){
                perfectInput = false;
                hairColorError.setText(rb.getString("wrong parameter"));
                hairColorError.setVisible(true);
            }
            else hairColorError.setVisible(false);
            Country nationality = asker.ask(Country::valueOf, arg -> true, nationalityField.getText(), true);
            if (!asker.response.equals("")){
                perfectInput = false;
                nationalityError.setText(rb.getString("wrong parameter"));
                nationalityError.setVisible(true);
            }
            else nationalityError.setVisible(false);
            Double locationX = asker.ask(Double::valueOf, arg -> true, locationXField.getText(), false);
            if (!asker.response.equals("")){
                perfectInput = false;
                locationXError.setText(rb.getString("wrong parameter"));
                locationXError.setVisible(true);
            }
            else locationXError.setVisible(false);
            Double locationY = asker.ask(Double::valueOf, arg -> true, locationYField.getText(), false);
            if (!asker.response.equals("")){
                perfectInput = false;
                locationYError.setText(rb.getString("wrong parameter"));
                locationYError.setVisible(true);
            }
            else locationYError.setVisible(false);
            double locationZ = asker.ask(Double::valueOf, arg -> true, locationZField.getText(), false);
            if (!asker.response.equals("")){
                perfectInput = false;
                locationZError.setText(rb.getString("wrong parameter"));
                locationZError.setVisible(true);
            }
            else locationZError.setVisible(false);
            if (perfectInput) {
                StudyGroup st = new StudyGroup(name, new Coordinates(coordinatesX, coordinatesY), studentsCount, expelledStudents,
                        shouldBeExpelled, semester, new Person(adminName, height, hairColor, nationality, new Location(locationX, locationY, locationZ)));

                Response response = null;
                if(!updateMode){
                    while (true) {
                        if (!TableController.isInRequest) {
                            TableController.isInRequest = true;
                            response = LoginController.client.insert(key, st);
                            TableController.isInRequest = false;
                            break;
                        }
                    }
                }
                else {
                    while (true) {
                        if (!TableController.isInRequest) {
                            TableController.isInRequest = true;
                            response = LoginController.client.update(key, studyGroup);
                            TableController.isInRequest = false;
                            break;
                        }
                    }
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(rb.getString("insert"));
                alert.setHeaderText(rb.getString("answer:"));
                alert.setContentText(rb.getString(response.getMessage()));
                alert.showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberOutOfBoundsException e) {
            e.printStackTrace();
        } catch (NullValueException e) {
            e.printStackTrace();
        }
    }
}
