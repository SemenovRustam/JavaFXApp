package sample.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import sample.config.DatabaseHandler;
import sample.entity.User;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button authButton;

    @FXML
    private TextField country;

    @FXML
    private TextField login_field;

    @FXML
    private TextField login_name;

    @FXML
    private TextField login_surname;

    @FXML
    private TextField password_field;

    @FXML
    private CheckBox signUpCheckBoxFemale;

    @FXML
    private CheckBox signUpCheckBoxMale;

    @FXML
    void initialize() {

        authButton.setOnAction(event -> {
            signUpNewUser();
        });
    }

    private void signUpNewUser() {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        String firstname = login_name.getText();
        String lastname = login_surname.getText();
        String login = login_field.getText();
        String password = password_field.getText();
        String location = country.getText();
        String gender = "";
        if (signUpCheckBoxMale.isSelected()) {
            gender = "Male";
        } else {
            gender = "Female";
        }

        User user = new User(firstname, lastname, login, password, location, gender);

        databaseHandler.signUpUser(user);
    }
}

