package databaseInteraction;

import data.*;
import exceptions.NullValueException;
import exceptions.NumberOutOfBoundsException;
import server.StudyGroupCollection;
import utils.CollectionManager;

import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Objects;

public class SQLStudyGroupCollectionManger implements CollectionManager {
    private final Connection connectionDB;
    private final SQLUserManager sqlUserManager;
    private User currentUser = null;
    private Hashtable<Long, StudyGroup> studyGroupHashtable;
    private CollectionManager collectionManager;

    public SQLStudyGroupCollectionManger(Connection connectionDB, CollectionManager collectionManager) throws SQLException, FileNotFoundException, NumberOutOfBoundsException, NullValueException {
        this.connectionDB = connectionDB;
        this.collectionManager = collectionManager;
        this.sqlUserManager = new SQLUserManager(connectionDB);
        CreateSQLTable.createCoordinatesTable(connectionDB);
        CreateSQLTable.createLocationTable(connectionDB);
        CreateSQLTable.createUserTable(connectionDB);
        //CreateSQLTable.createTypeColor(connectionDB);
        //CreateSQLTable.createTypeSemester(connectionDB);
        //CreateSQLTable.createTypeCountry(connectionDB);
        CreateSQLTable.createPersonTable(connectionDB);
        CreateSQLTable.createStudyGroup(connectionDB);
        studyGroupHashtable = deserialize();
    }

    private Hashtable<Long, StudyGroup> deserialize() throws SQLException, FileNotFoundException, NumberOutOfBoundsException, NullValueException {
        Hashtable<Long, StudyGroup> st = new Hashtable<>();
        Statement statement = connectionDB.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM studyGroups");
        while (resultSet.next()){
            StudyGroup studyGroup = mapRowToStudyGroup(resultSet);
            st.put(studyGroup.getId(), studyGroup);
        }
        return st;
    }

    private StudyGroup mapRowToStudyGroup(ResultSet resultSet) throws SQLException, NullValueException, NumberOutOfBoundsException {
        PreparedStatement prepStat;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StudyGroup studyGroup = new StudyGroup();

        studyGroup.setId(resultSet.getLong("id")); //точно long?
        studyGroup.setName(resultSet.getString("name"));

        String getCoordinates = "SELECT * FROM coord WHERE id=?";
        prepStat = connectionDB.prepareStatement(getCoordinates);
        prepStat.setInt(1, resultSet.getInt("coordinates"));
        ResultSet resCoord = prepStat.executeQuery();
        resCoord.next();
        studyGroup.setCoordinates(new Coordinates(resCoord.getLong("x"), resCoord.getLong("y")));

        studyGroup.setCreationDate(LocalDateTime.parse(resultSet.getString("creationDate"), formatter));
        studyGroup.setStudentsCount(resultSet.getLong("studentsCount"));
        studyGroup.setExpelledStudents(resultSet.getInt("expelledStudents"));
        studyGroup.setShouldBeExpelled(resultSet.getInt("shouldBeExpelled"));
        studyGroup.setSemesterEnum(Semester.valueOf(resultSet.getString("semesterEnum")));

        String getGroupAdmin = "SELECT * FROM persons WHERE id=?";
        prepStat = connectionDB.prepareStatement(getGroupAdmin);
        prepStat.setInt(1, resultSet.getInt("groupAdmin"));
        ResultSet resAdmin = prepStat.executeQuery();
        resAdmin.next();

        String getLocation = "SELECT * FROM locations WHERE id=?";
        prepStat = connectionDB.prepareStatement(getLocation);
        prepStat.setInt(1, resAdmin.getInt("location"));
        ResultSet resLoc = prepStat.executeQuery();
        resLoc.next();
        studyGroup.setGroupAdmin(new Person(
                resAdmin.getString("name"),
                resAdmin.getLong("height"),
                Color.valueOf(resAdmin.getString("hairColor")),
                Country.valueOf(resAdmin.getString("nationality")),
                new Location(resLoc.getDouble("x"), resLoc.getDouble("y"), resLoc.getDouble("z"))));

        String getUser = "SELECT * FROM users WHERE login=?";
        prepStat = connectionDB.prepareStatement(getUser);
        prepStat.setString(1, resultSet.getString("owner"));
        ResultSet resUser = prepStat.executeQuery();
        resUser.next();
        studyGroup.setUser(new User(resUser.getString("login"), resUser.getString("password")));
        return studyGroup;
    }

    private void prepareStatStudyGroup(PreparedStatement stat, StudyGroup sg) throws SQLException {
        final int indexName = 1;
        final int indexCoordinates = 2;
        final int indexCreation = 3;
        final int indexStCount = 4;
        final int indexExpelled = 5;
        final int indexShouldBeExp = 6;
        final int indexSemester = 7;
        final int indexAdmin = 8;
        final int indexOwner = 9;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        stat.setString(indexName, sg.getName());
        stat.setInt(indexCoordinates, sg.getCoordinates().getId());
        stat.setTimestamp(indexCreation, Timestamp.valueOf(sg.getCreationDate().format(formatter)));
        stat.setLong(indexStCount, sg.getStudentsCount());
        if (Objects.isNull(sg.getExpelledStudents())) {
            stat.setNull(indexExpelled, Types.NULL);
        }
        else {
            stat.setInt(indexExpelled, sg.getExpelledStudents());
        }
        stat.setInt(indexShouldBeExp, sg.getShouldBeExpelled());

        if (Objects.isNull(sg.getSemesterEnum())){
            stat.setNull(indexSemester, Types.NULL);
        }
        else {
            stat.setObject(indexSemester, sg.getSemesterEnum().name().toUpperCase(Locale.ROOT), Types.OTHER);
        }

        stat.setInt(indexAdmin, sg.getGroupAdmin().getId());
        stat.setString(indexOwner, sg.getUser().getLogin());
    }

    private void prepareStatCoordinates(PreparedStatement stat, Coordinates coordinates) throws SQLException {
        final int indexX = 1;
        final int indexY = 2;
        stat.setLong(indexX, coordinates.getX());
        stat.setLong(indexY, coordinates.getX());
    }

    private void prepareStatLocation(PreparedStatement stat, Location location) throws SQLException {
        final int indexX = 1;
        final int indexY = 2;
        final int indexZ = 3;
        stat.setDouble(indexX, location.getX());
        stat.setDouble(indexY, location.getY());
        stat.setDouble(indexZ, location.getZ());
    }

    private void prepareStatOwner(PreparedStatement stat, User owner) throws SQLException {
        final int indexLogin = 1;
        final int indexPassword = 2;
        stat.setString(indexLogin, owner.getLogin());
        stat.setString(indexPassword, owner.getPassword());
    }

    private void prepareStatAdmin(PreparedStatement stat, Person admin) throws SQLException {
        final int indexName = 1;
        final int indexHeight = 2;
        final int indexHairColor = 3;
        final int indexNationality = 4;
        final int indexLocation = 5;
        stat.setString(indexName, admin.getName());

        if (Objects.isNull(admin.getHeight())){
            stat.setNull(indexHeight, Types.NULL);
        }
        else {
            stat.setLong(indexHeight, admin.getHeight());
        }
        stat.setObject(indexHairColor, admin.getHairColor().name().toUpperCase(Locale.ROOT), Types.OTHER);
        if (Objects.isNull(admin.getNationality())){
            stat.setNull(indexNationality, Types.NULL);
        }
        else {
            stat.setObject(indexNationality, admin.getNationality().name().toUpperCase(Locale.ROOT), Types.OTHER);
        }
        stat.setInt(indexLocation, admin.getLocation().getId());
    }

    @Override
    public boolean add(Long key, StudyGroup studyGroup) {
        String insertStudyGroup = "INSERT INTO studyGroups VALUES (" +
                                    " default,?,?,?,?,?,?,?::semester,?,?) RETURNING id";
        String insertCoord = "INSERT INTO coord VALUES (" +
                                " default,?,?) RETURNING id";
        String insertPerson = "INSERT INTO persons VALUES (" +
                                " default,?,?,?::color,?::country,?) RETURNING id";
        String insertLocation = "INSERT INTO locations VALUES (" +
                                " default,?,?,?) RETURNING id";


        try (
            PreparedStatement statCoord = connectionDB.prepareStatement(insertCoord);
            PreparedStatement statLocation = connectionDB.prepareStatement(insertLocation);
            PreparedStatement statAdmin = connectionDB.prepareStatement(insertPerson);
            PreparedStatement statStudyGroup = connectionDB.prepareStatement(insertStudyGroup);) {

            prepareStatCoordinates(statCoord, studyGroup.getCoordinates());
            ResultSet resCoord = statCoord.executeQuery();
            resCoord.next();
            studyGroup.getCoordinates().setId(resCoord.getInt("id"));

            prepareStatLocation(statLocation, studyGroup.getGroupAdmin().getLocation());
            ResultSet resLocation = statLocation.executeQuery();
            resLocation.next();
            studyGroup.getGroupAdmin().getLocation().setId(resLocation.getInt("id"));

            prepareStatAdmin(statAdmin, studyGroup.getGroupAdmin());
            ResultSet resAdmin = statAdmin.executeQuery();
            resAdmin.next();
            studyGroup.getGroupAdmin().setId(resAdmin.getInt("id"));

            prepareStatStudyGroup(statStudyGroup, studyGroup);
            ResultSet resStudyGroup = statStudyGroup.executeQuery();
            resStudyGroup.next();
            studyGroup.setId(resStudyGroup.getLong("id"));
            return true;
        } catch (SQLException e){
            System.err.println("Не удалось добавить элемент в бд");
        } catch (NumberOutOfBoundsException e) {
            e.printStackTrace();
        } catch (NullValueException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(Long key) {
        return false;
    }

    @Override
    public void setFileFromCollection() throws FileNotFoundException {

    }

    @Override
    public Long getMinFreeId() {
        return null;
    }

    @Override
    public boolean isIdUsed(Long id) {
        return false;
    }

    @Override
    public void addIdToUsed(Long id) {

    }

    @Override
    public void removeIdFromUsed(Long id) {

    }

    @Override
    public User createNewUser(User user) {
        return sqlUserManager.register(user);
    }

    @Override
    public User getUser(User user) {
        if (sqlUserManager.authenticate(user)){
            return user;
        }
        return null;
    }

    @Override
    public int clean() {
        int count = -1;
        try {
            final String selectGroups = "DELETE FROM studyGroups WHERE owner=?";
            PreparedStatement preparedStatement = connectionDB.prepareStatement(selectGroups);
            preparedStatement.setString(1, currentUser.getLogin());
            int rs = preparedStatement.executeUpdate();
            return rs;
        }
        catch (SQLException e){
            e.printStackTrace();
            return count;
        }
    }

    @Override
    public Hashtable<Long, StudyGroup> getStudyGroupHashTable() {
        return studyGroupHashtable;
    }

    @Override
    public LocalDateTime getInitializationDate() {
        return null;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public Class getType() {
        return null;
    }

    public void setCurrentUser(User user){
        this.currentUser = user;
    }

    @Override
    public boolean updateStudyGroup(Long key, StudyGroup newGroup, StudyGroup oldGroup) {
        try {
            String updateCoordination = "UPDATE coord SET x=?, y=? WHERE id=?;";
            PreparedStatement coordSt= connectionDB.prepareStatement(updateCoordination);
            coordSt.setLong(1, newGroup.getCoordinates().getX());
            coordSt.setLong(2, newGroup.getCoordinates().getY());
            coordSt.setInt(3, oldGroup.getCoordinates().getId());
            coordSt.executeUpdate();

            String updateLocation = "UPDATE locations SET x=?, y=?, z=? WHERE id=?;";
            PreparedStatement locationSt = connectionDB.prepareStatement(updateLocation);
            locationSt.setDouble(1, newGroup.getGroupAdmin().getLocation().getX());
            locationSt.setDouble(2, newGroup.getGroupAdmin().getLocation().getY());
            locationSt.setDouble(3, newGroup.getGroupAdmin().getLocation().getZ());
            locationSt.setInt(4, oldGroup.getGroupAdmin().getLocation().getId());
            locationSt.executeUpdate();

            String updateAdmin = "UPDATE persons SET name=?, height=?, hairColor=?, nationality=? WHERE id=?;";
            PreparedStatement adminSt = connectionDB.prepareStatement(updateAdmin);
            adminSt.setString(1, newGroup.getGroupAdmin().getName());
            if (Objects.isNull(newGroup.getGroupAdmin().getHeight())){
                adminSt.setNull(2, Types.NULL);
            }
            else {
                adminSt.setLong(2, newGroup.getGroupAdmin().getHeight());
            }
            adminSt.setLong(2, newGroup.getGroupAdmin().getHeight());
            adminSt.setObject(3, newGroup.getGroupAdmin().getHairColor().name(), Types.OTHER);
            if (Objects.isNull(newGroup.getGroupAdmin().getNationality())){
                adminSt.setNull(4, Types.NULL);
            }
            else{
                adminSt.setObject(4, newGroup.getGroupAdmin().getNationality().name().toUpperCase(Locale.ROOT), Types.OTHER);
            }
            adminSt.setInt(5, oldGroup.getGroupAdmin().getId());
            adminSt.executeUpdate();

            String updateGroup = "UPDATE studyGroups SET name=?, studentsCount=?, expelledStudents=?, shouldBeExpelled=?, semesterEnum=? WHERE id=?;";
            PreparedStatement groupSt = connectionDB.prepareStatement(updateGroup);
            groupSt.setString(1, newGroup.getName());
            groupSt.setLong(2, newGroup.getStudentsCount());
            if (Objects.isNull(newGroup.getExpelledStudents())){
                groupSt.setNull(3, Types.NULL);
            }
            else  {groupSt.setInt(3, newGroup.getExpelledStudents());}

            groupSt.setInt(4, newGroup.getShouldBeExpelled());

            if (Objects.isNull(newGroup.getSemesterEnum())){
                groupSt.setNull(5, Types.NULL);
            }
            else {
                groupSt.setObject(5, newGroup.getSemesterEnum().name().toUpperCase(Locale.ROOT), Types.OTHER);
            }
            groupSt.setLong(6, oldGroup.getId());
            groupSt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
