package client;

import data.Coordinates;
import data.Location;
import data.Person;
import data.StudyGroup;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.text.NumberFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

public class TableAnswerController implements Initializable {

    public static ResourceBundle rb;
    @FXML
    protected TableView<StudyGroup> table;

    public static StudyGroup[] studyGroups;

    @FXML
    protected TableColumn<StudyGroup, String> id;
    @FXML
    protected TableColumn<StudyGroup, String> name;
    @FXML
    protected TableColumn<StudyGroup, Coordinates> coordinates;
    @FXML
    protected TableColumn<StudyGroup, String> coordinatesX;
    @FXML
    protected TableColumn<StudyGroup, String> coordinatesY;
    @FXML
    protected TableColumn<StudyGroup, String> creationDate;
    @FXML
    protected TableColumn<StudyGroup, String> studentsCount;
    @FXML
    protected TableColumn<StudyGroup, String> expelledStudents;
    @FXML
    protected TableColumn<StudyGroup, String> shouldBeExpelled;
    @FXML
    protected TableColumn<StudyGroup, String> semester;
    @FXML
    protected TableColumn<StudyGroup, Person> admin;
    @FXML
    protected TableColumn<StudyGroup, String> adminName;
    @FXML
    protected TableColumn<StudyGroup, String> height;
    @FXML
    protected TableColumn<StudyGroup, String> hairColor;
    @FXML
    protected TableColumn<StudyGroup, String> nationality;
    @FXML
    protected TableColumn<StudyGroup, Location> location;
    @FXML
    protected TableColumn<StudyGroup, String> locationX;
    @FXML
    protected TableColumn<StudyGroup, String> locationY;
    @FXML
    protected TableColumn<StudyGroup, String> locationZ;
    @FXML
    protected TableColumn<StudyGroup, String> user;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.getItems().removeAll(table.getItems());
        table.refresh();
        id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(NumberFormat.getInstance(rb.getLocale()).format(cellData.getValue().getId())));
        name.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));
        coordinatesX.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(NumberFormat.getInstance(rb.getLocale()).format(cellData.getValue().getCoordinates().getX())));
        coordinatesY.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(NumberFormat.getInstance(rb.getLocale()).format(cellData.getValue().getCoordinates().getY())));
        creationDate.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(rb.getLocale()).format(cellData.getValue().getCreationDate().atZone(ZoneId.systemDefault()))));
        studentsCount.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(NumberFormat.getInstance(rb.getLocale()).format(cellData.getValue().getStudentsCount())));
        expelledStudents.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(NumberFormat.getInstance(rb.getLocale()).format(cellData.getValue().getExpelledStudents())));
        shouldBeExpelled.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(NumberFormat.getInstance(rb.getLocale()).format(cellData.getValue().getShouldBeExpelled())));
        semester.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(String.valueOf(cellData.getValue().getSemesterEnum())));
        adminName.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getGroupAdmin().getName()));
        height.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(NumberFormat.getInstance(rb.getLocale()).format(cellData.getValue().getGroupAdmin().getHeight())));
        hairColor.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(String.valueOf(cellData.getValue().getGroupAdmin().getHairColor())));
        nationality.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(String.valueOf(cellData.getValue().getGroupAdmin().getNationality())));
        locationX.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(NumberFormat.getInstance(rb.getLocale()).format(cellData.getValue().getGroupAdmin().getLocation().getX())));
        locationY.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(NumberFormat.getInstance(rb.getLocale()).format(cellData.getValue().getGroupAdmin().getLocation().getY())));
        locationZ.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(NumberFormat.getInstance(rb.getLocale()).format(cellData.getValue().getGroupAdmin().getLocation().getZ())));
        user.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getUser().getLogin()));


        for(StudyGroup st:studyGroups) {
            table.getItems().add(st);
        }
    }


}
