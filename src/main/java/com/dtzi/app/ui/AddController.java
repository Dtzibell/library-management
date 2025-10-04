package com.dtzi.app.ui;

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

public class AddController implements Initializable {
  
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
    PreparedStatement prep = conn.prepareStatement("INSERT INTO members(name,surname,id,phone_no,email) VALUES (?,?,?::uuid,?,?)");
    prep.setString(1, newMem.firstNameProperty().get());
    prep.setString(2, newMem.surnameProperty().get());
    prep.setString(3, newMem.IDProperty().get());
    prep.setString(4, newMem.phoneNumberProperty().get());
    prep.setString(5, newMem.emailProperty().get());
    prep.executeUpdate();
    Stage currentStage = (Stage) nameField.getScene().getWindow();
    currentStage.close();
  }
}
