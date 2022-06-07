package databaseInteraction;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateSQLTable {



    public CreateSQLTable(){}

    static void createUserTable(Connection connection) throws SQLException {
        final String createTableQuery = "CREATE TABLE IF NOT EXISTS users (" +
                                            " login varchar(50) NOT NULL PRIMARY KEY," +
                                            " password VARCHAR(50) NOT NULL," +
                                            " salt VARCHAR(50) NOT NULL);";
        Statement statement = connection.createStatement();
        statement.execute(createTableQuery);
    }

    static void createCoordinatesTable(Connection connection) throws SQLException {
        final String createTableQuery = "CREATE TABLE IF NOT EXISTS coord (" +
                                        " id SERIAL PRIMARY KEY," +
                                        " x BIGINT NOT NULL," +
                                        " y BIGINT NOT NULL," +
                                        " CHECK(x <= 722)," +
                                        " CHECK(y <= 102));";
        Statement statement = connection.createStatement();
        statement.execute(createTableQuery);
    }

    static void createLocationTable(Connection connection) throws SQLException {
        final String createTableQuery = "CREATE TABLE IF NOT EXISTS locations (" +
                                        " id SERIAL PRIMARY KEY," +
                                        " x double PRECISION NOT NULL," +
                                        " y double PRECISION NOT NULL," +
                                        " z double PRECISION NOT NULL);";

        Statement statement = connection.createStatement();
        statement.execute(createTableQuery);
    }

    static void createPersonTable(Connection connection) throws SQLException {
        final String createTableQuery = "CREATE TABLE IF NOT EXISTS persons(" +
                                    " id SERIAL PRIMARY KEY," +
                                    " name varchar(50) NOT NULL," +
                                    " height BIGINT," +
                                    " hairColor color NOT NULL,"+
                                    " nationality country," +
                                    " location int," +
                                    " CHECK(height > 0 or height = null)," +
                                    " FOREIGN KEY(location) REFERENCES locations(id));";

        Statement statement = connection.createStatement();
        statement.execute(createTableQuery);
    }

    static void createStudyGroup(Connection connection) throws SQLException {
        final String createTableQuery = "CREATE TABLE IF NOT EXISTS studyGroups (" +
                                        " id SERIAL PRIMARY KEY," +
                                        " name varchar(50) NOT NULL," +
                                        " coordinates int NOT NULL," +
                                        " creationDate TIMESTAMP NOT NULL," +
                                        " studentsCount bigint NOT NULL," +
                                        " expelledStudents int," +
                                        " shouldBeExpelled int NOT NULL," +
                                        " semesterEnum semester," +
                                        " groupAdmin int NOT NULL," +
                                        " owner varchar(50) NOT NULL," +
                                        " CHECK(expelledStudents > 0)," +
                                        " CHECK(studentsCount > 0)," +
                                        " CHECK(shouldBeExpelled > 0)," +
                                        " FOREIGN KEY(coordinates) REFERENCES coord(id)," +
                                        " FOREIGN KEY(groupAdmin) REFERENCES persons(id)," +
                                        " FOREIGN KEY(owner) REFERENCES users(login));";

        Statement statement = connection.createStatement();
        statement.execute(createTableQuery);
    }

    static void createTypeSemester(Connection connection) throws SQLException {
        final String createTableQuery = "CREATE TYPE semester AS ENUM (" +
                                        " 'THIRD'," +
                                        " 'FIFTH'," +
                                        " 'SIXTH'," +
                                        " 'SEVENTH');";

        Statement statement = connection.createStatement();
        statement.execute(createTableQuery);
    }

    static void createTypeCountry(Connection connection) throws SQLException {
        final String createTableQuery = "CREATE TYPE country AS ENUM (" +
                                        " 'UNITED_KINGDOM'," +
                                        "'GERMANY'," +
                                        "'NORTH_KOREA'," +
                                        "'JAPAN');";
        Statement statement = connection.createStatement();
        statement.execute(createTableQuery);
    }

    static void createTypeColor(Connection connection) throws SQLException {
        final String createTableQuery = "CREATE TYPE color AS ENUM (" +
                                        " 'BLUE'," +
                                        " 'WHITE'," +
                                        " 'BROWN');";

        Statement statement = connection.createStatement();
        statement.execute(createTableQuery);

    }
}
