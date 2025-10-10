package com.dtzi.app.controllers;

import com.dtzi.app.MainController;
import com.dtzi.app.classes.Member;
import com.dtzi.app.pgutils.PostgreSQL;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import java.util.UUID;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AddMemberController implements Initializable {
  
  @FXML
  private TextField nameField;
  @FXML
  private TextField surnameField;
  @FXML
  private Label IDField;
  @FXML
  private TextField phoneNoField;
  @FXML
  private TextField emailField;
  @FXML
  private Button addButton;
  @FXML
  private Label wrongEmail;
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    IDField.setText(UUID.randomUUID().toString());
    addButton.setDisable(true);
    emailField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.matches(".*@mymail.[a-z]{3}$")) {
        addButton.setDisable(false);
        wrongEmail.setVisible(false);
      }
      else {
        addButton.setDisable(true);
        wrongEmail.setVisible(true);
      }
    });
  }

  public void addMember() throws Exception {
    Connection conn = PostgreSQL.connect();
    Member newMem = new Member(nameField.getText(), surnameField.getText(), IDField.getText(), phoneNoField.getText(), emailField.getText(), conn);
    MainController.listOfMembers.add(newMem);
    Stage currentStage = (Stage) nameField.getScene().getWindow();
    currentStage.close();
  }
}
