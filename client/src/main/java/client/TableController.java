package client;

import data.Coordinates;
import data.Location;
import data.Person;
import data.StudyGroup;
import javafx.animation.ScaleTransition;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import jdk.jfr.internal.PlatformRecorder;
import sun.security.provider.Sun;
import utils.Response;
import javafx.application.Platform;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

public class TableController implements Initializable {

    protected static ResourceBundle rb;

    @FXML
    protected TableView<StudyGroup> table;

    @FXML
    protected TableView<StudyGroup> objectTable;

    @FXML
    protected Tab mapTab;

    @FXML
    protected TabPane tabPane;

    @FXML
    protected AnchorPane mapPane;

    @FXML
    protected Button logOutButton;
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
    @FXML
    protected TableColumn<StudyGroup, String> id1;
    @FXML
    protected TableColumn<StudyGroup, String> name1;
    @FXML
    protected TableColumn<StudyGroup, String> coordinatesX1;
    @FXML
    protected TableColumn<StudyGroup, String> coordinatesY1;
    @FXML
    protected TableColumn<StudyGroup, String> creationDate1;
    @FXML
    protected TableColumn<StudyGroup, String> studentsCount1;
    @FXML
    protected TableColumn<StudyGroup, String> expelledStudents1;
    @FXML
    protected TableColumn<StudyGroup, String> shouldBeExpelled1;
    @FXML
    protected TableColumn<StudyGroup, String> semester1;
    @FXML
    protected TableColumn<StudyGroup, String> adminName1;
    @FXML
    protected TableColumn<StudyGroup, String> height1;
    @FXML
    protected TableColumn<StudyGroup, String> hairColor1;
    @FXML
    protected TableColumn<StudyGroup, String> nationality1;
    @FXML
    protected TableColumn<StudyGroup, String> locationX1;
    @FXML
    protected TableColumn<StudyGroup, String> locationY1;
    @FXML
    protected TableColumn<StudyGroup, String> locationZ1;
    @FXML
    protected TableColumn<StudyGroup, String> user1;

    private boolean firstTime = true;

    private final HashMap<Shape, Long> shapeMap = new HashMap<>();
    private boolean flag = true;
    private final HashMap<String, Color> colorHashMap = new HashMap<>();
    private final List<Long> idList = new ArrayList<>();
    private final Canvas canvas = new Canvas(1280, 980);

    private StudyGroup[] studyGroups;

    public static boolean stop = false;

    public static boolean isInRequest = false;


    public Thread thread = new Thread(this::updateTable);


    @Override
    public void initialize(URL loc, ResourceBundle resources) {
        studyGroups = LoginController.client.getElements().getStudyGroups();
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

        id1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(NumberFormat.getInstance(rb.getLocale()).format(cellData.getValue().getId())));
        name1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));
        coordinatesX1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(NumberFormat.getInstance(rb.getLocale()).format(cellData.getValue().getCoordinates().getX())));
        coordinatesY1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(NumberFormat.getInstance(rb.getLocale()).format(cellData.getValue().getCoordinates().getY())));
        creationDate1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(rb.getLocale()).format(cellData.getValue().getCreationDate().atZone(ZoneId.systemDefault()))));
        studentsCount1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(NumberFormat.getInstance(rb.getLocale()).format(cellData.getValue().getStudentsCount())));
        expelledStudents1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(NumberFormat.getInstance(rb.getLocale()).format(cellData.getValue().getExpelledStudents())));
        shouldBeExpelled1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(NumberFormat.getInstance(rb.getLocale()).format(cellData.getValue().getShouldBeExpelled())));
        semester1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(String.valueOf(cellData.getValue().getSemesterEnum())));
        adminName1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getGroupAdmin().getName()));
        height1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(NumberFormat.getInstance(rb.getLocale()).format(cellData.getValue().getGroupAdmin().getHeight())));
        hairColor1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(String.valueOf(cellData.getValue().getGroupAdmin().getHairColor())));
        nationality1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(String.valueOf(cellData.getValue().getGroupAdmin().getNationality())));
        locationX1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(NumberFormat.getInstance(rb.getLocale()).format(cellData.getValue().getGroupAdmin().getLocation().getX())));
        locationY1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(NumberFormat.getInstance(rb.getLocale()).format(cellData.getValue().getGroupAdmin().getLocation().getY())));
        locationZ1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(NumberFormat.getInstance(rb.getLocale()).format(cellData.getValue().getGroupAdmin().getLocation().getZ())));
        user1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getUser().getLogin()));

        for(StudyGroup st:studyGroups) {
            table.getItems().add(st);
        }

        createMap();

        thread.start();

    }

    @FXML
    public void onHelpButtonClick(){
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("help.fxml"), rb);
            stage.setScene(new Scene(root));
            stage.setTitle(rb.getString("help"));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onSumButtonAction() {
        try {

            int sum = 0;
            for (StudyGroup st : studyGroups) {
                sum += st.getStudentsCount();
            }
            SumController.rb = rb;
            SumController.sum = sum;
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("sum.fxml"), rb);
            stage.setScene(new Scene(root));
            stage.setTitle(rb.getString("sum of students count"));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Stage infoStage;

    @FXML
    public void onInfoButtonAction() {
        Task<Void> infoTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Response response = LoginController.client.getInfo();
                Platform.runLater(() -> {
                            try {
                                infoStage = new Stage();
                                InfoController.text = response.getMessage();
                                Parent root = FXMLLoader.load(getClass().getResource("info.fxml"), rb);
                                infoStage.setScene(new Scene(root));
                                infoStage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                );
                return null;
            }
        };
        Thread th = new Thread(infoTask);
        th.setDaemon(true);
        th.start();
    }

    @FXML
    public void onShowButtonAction(){
        tabPane.getSelectionModel().select(mapTab);
    }

    @FXML
    public void onCountGreaterSemesterButtonAction(){
        try {
            ParameterAskerController.rb = rb;
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("parameterAsker.fxml"), rb);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateTable(){

            while (true) {

                try {
                StudyGroup[] studyGroups1 = null;
                if (!isInRequest) {
                    isInRequest = true;
                    studyGroups1 = LoginController.client.getElements().getStudyGroups();
                    isInRequest = false;
                    table.getItems().removeAll(studyGroups);
                    for (StudyGroup st : studyGroups1) {
                        table.getItems().add(st);
                    }
                    studyGroups = studyGroups1;
                    Thread.sleep(10000);
                    if (stop){ Thread.currentThread().interrupt();
                        return;}
                }
            } catch(InterruptedException e){
                e.printStackTrace();
            }catch (Exception e){

                }
        }
    }

    @FXML
    public void onClearButtonAction() {
        Task<Void> clearTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Response response;
                response = LoginController.client.clear();

                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(rb.getString("clear"));
                    alert.setHeaderText(rb.getString("answer:"));
                    alert.setContentText(rb.getString("success"));
                    alert.showAndWait();
                });
                return null;
            }
        };
        Thread th = new Thread(clearTask);
        th.setDaemon(true);
        th.start();
    }

    @FXML
    public void onInsertButtonAction(){
        try {
            GroupAskerController.rb = rb;
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("groupAsker.fxml"), rb);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onFilterStudentsCountAction(){
        try {
            StCountAskerController.rb = rb;
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("parameterAskerStCount.fxml"), rb);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void onRemoveGreaterKeyButtonAction(){
        try {
            KeyAskerController.rb = rb;
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("parameterAskerKey.fxml"), rb);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createMap() {
        boolean contains = false;
        Set<Shape> keys = shapeMap.keySet();

        for (Object key : keys) {
            mapPane.getChildren().remove(key);
        }

        shapeMap.clear();
        if (flag) {
            for (int i = 0; i < studyGroups.length; i++) {
                if (colorHashMap.get(studyGroups[i].getUser()) == null) {
                    javafx.scene.paint.Color c = javafx.scene.paint.Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255),
                            (int) (Math.random() * 255));
                    while (colorHashMap.containsValue(c)) {
                        c = javafx.scene.paint.Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255),
                                (int) (Math.random() * 255));
                    }
                    colorHashMap.put(studyGroups[i].getUser().getLogin(), c);
                }
            }
        }
        StudyGroup[] list = studyGroups.clone();
        Arrays.stream(list).sorted(Comparator.comparing(StudyGroup::getStudentsCount).reversed());
        for (StudyGroup studyGroup : list) {
            Shape circle = new Circle(((float) studyGroup.getStudentsCount() / 50) * canvas.getScaleX(), colorHashMap.get(studyGroup.getUser().getLogin()));
            circle.setLayoutX(mapPane.widthProperty().subtract(0).getValue() / 4 + studyGroup.getCoordinates().getX() * canvas.getScaleX());
            circle.setLayoutY(mapPane.widthProperty().subtract(0).getValue() / 4 + studyGroup.getCoordinates().getY() * canvas.getScaleY());
            circle.setVisible(true);
            shapeMap.put(circle,studyGroup.getId());
            if(!idList.contains(studyGroup.getId())){
                idList.add(studyGroup.getId());
                contains=true;
            }
            circle.setStroke(javafx.scene.paint.Color.BLACK);
            mapPane.setOnScroll(this::mouseScroll);
            circle.setOnMouseClicked(this::mouseClicked);
            circle.setOnMouseEntered(this::mouseEntered);
            if(circle.getLayoutY()-(float) studyGroup.getStudentsCount()/50*canvas.getScaleX()-82>0){
                mapPane.getChildren().addAll(circle);
            }
            if (flag && contains){
                ScaleTransition circleAnimation = new ScaleTransition(Duration.millis(5000), circle);
                circleAnimation.setFromX(0);
                circleAnimation.setToX(1);
                circleAnimation.setFromY(0);
                circleAnimation.setToY(1);
                circleAnimation.play();
                circleAnimation.jumpTo(Duration.millis(5000));
            }
        }
    }

    private void mouseEntered(javafx.scene.input.MouseEvent mouseEvent) {
        Shape shape = (Shape) mouseEvent.getSource();
        long id = shapeMap.get(shape);
        for (StudyGroup studyGroup : studyGroups) {
            if (studyGroup.getId() == id) objectTable.getItems().setAll(studyGroup);
        }
    }

    private void mouseClicked(javafx.scene.input.MouseEvent mouseEvent) {
        int count = 0;
        Shape shape = (Shape) mouseEvent.getSource();
        long id2 = shapeMap.get(shape);
        for (StudyGroup studyGroup : studyGroups) {
            count++;
            if (studyGroup.getId() == id2) {
                tabPane.getSelectionModel().select(0);
                table.getSelectionModel().select(count-1);
            }
        }
    }

    private void mouseScroll(ScrollEvent event) {
        double k = (event.getDeltaY() < 0 ? 0.9 : 1.1);
        double zr = canvas.getScaleX() * k;
        canvas.setScaleX(zr);
        canvas.setScaleY(zr);
        flag = false;
        createMap();
        flag = true;
    }

    @FXML
    public void logOutOnAction() throws IOException {
        logOutButton.getScene().getWindow().hide();
        LoginController.rb = rb;
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("table.fxml"), rb);
        stage.setScene(new Scene(root));
        stage.show();
        stage.setOnCloseRequest(windowEvent -> System.exit(0));
    }

    @FXML
    private void onUpdateButtonAction(){
        GroupAskerController.updateMode = true;
        GroupAskerController.studyGroup = table.getItems().get(table.getSelectionModel().getFocusedIndex());
        onInsertButtonAction();
    }

    @FXML
    public void removeKeyButtonAction(){

        Task<Void> removeTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                StudyGroup studyGroup = table.getItems().get(table.getSelectionModel().getFocusedIndex());
                Response response;
                response = LoginController.client.removeKey(studyGroup.getId());
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(rb.getString("clear"));
                    alert.setHeaderText(rb.getString("answer:"));
                    alert.setContentText(rb.getString(response.getMessage()));
                    alert.showAndWait();
                });
                return null;
            }
        };
    }

    @FXML
    public void replaceIfGreaterOnAction(){
        GroupAskerController.updateMode = true;
        GroupAskerController.ifGreaterMode = true;
        GroupAskerController.studyGroup = table.getItems().get(table.getSelectionModel().getFocusedIndex());
        onInsertButtonAction();
    }


    @FXML
    public void removeIfLowerOnAction(){
        try {
            RemoveLowerController.rb = rb;
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("removeLower.fxml"), rb);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void changeLanguageAction(){
        try {
            SettingsController.changeLanguage = true;
            logOutButton.getScene().getWindow().hide();
            LoginController.rb = rb;
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("settings.fxml"), rb);
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnCloseRequest(windowEvent -> System.exit(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void filtrationOnAction(){}

    public void infoInterface(){
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("info.fxml"), rb);
            stage.setScene(new Scene(root));
            stage.setTitle(rb.getString("info"));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
