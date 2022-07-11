package sample.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.animation.Shake;
import sample.config.DatabaseHandler;
import sample.entity.User;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button auth;

    @FXML
    private Button login;

    @FXML
    private TextField login_field;

    @FXML
    private TextField password_field;

    @FXML
    void initialize() {

        auth.setOnAction(event -> {
            String login = login_field.getText().trim();
            String password = password_field.getText().trim();

            if (login.isEmpty() | password.isEmpty()) {
                System.out.println("Login or password empty");
            }
            loginUser(login, password);

        });

        login.setOnAction(event -> {
           openNewScene("/sample/view/signUp.fxml");
        });
    }

    private void loginUser(String login, String password) {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        User user = new User();
        user.setUserName(login);
        user.setPassword(password);
        ResultSet resultSet = databaseHandler.getUser(user);

        int counter = 0;
        try {
            while (resultSet.next()) {
                counter++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (counter >= 1) {
            openNewScene("/sample/view/app.fxml");
            System.out.println("User with login " + user.getUserName() + "  successfully log in.");
        } else {
            Shake loginShake = new Shake(login_field);
            Shake passwordShake = new Shake(password_field);
            loginShake.playAnimation();
            passwordShake.playAnimation();
        }
    }

    public void openNewScene(String window){
        login.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(window));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}
