package sample.config;

import sample.entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;


public class DatabaseHandler extends ServerConfig {
    Connection dbConnection;

    public Connection getDbConnection() throws  SQLException {
        String connectionString = "jdbc:mysql://"
                + dbHost + ":"
                + dbPort + "/"
                + dbName;
        return DriverManager.getConnection(connectionString,
                dbUser, dbPass);
    }

    public void signUpUser(User user) {
        String insert = "INSERT INTO " + Const.USER_TABLE + " (" +
                Const.USERS_FIRSTNAME + "," + Const.USERS_LASTNAME + "," +
                Const.USERS_USERNAME + "," + Const.USERS_PASSWORD + "," +
                Const.USERS_LOCATION + "," + Const.USERS_GENDER + ")" +
                "VALUES(?,?,?,?,?,?)";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getLocation());
            preparedStatement.setString(6, user.getGender());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet getUser(User user) {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_USERNAME + "=? AND " + Const.USERS_PASSWORD + "=?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getDbConnection().prepareStatement(select);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            resultSet = preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
