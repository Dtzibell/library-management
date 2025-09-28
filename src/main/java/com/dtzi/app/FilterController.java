package com.dtzi.app;

import com.dtzi.app.classes.Member;
import com.dtzi.app.pgutils.PostgreSQL;
import com.dtzi.app.MainController.ListPayload;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class FilterController implements Initializable {
  
  @FXML
  private TextField nameField;
  @FXML
  private TextField surnameField;
  @FXML
  private TextField IDField;
  @FXML
  private TextField phoneNoField;
  @FXML
  private TextField emailField;
  @FXML
  private Button updateButton;
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    updateButton.setOnAction(p -> {
      try (Connection conn = PostgreSQL.connect()) {
        StringBuilder sb = new StringBuilder("SELECT * FROM members");
        sb.append(" WHERE LOWER(name) SIMILAR TO LOWER('" + nameField.getText() + "%')");
        sb.append(" AND LOWER(surname) SIMILAR TO LOWER('" + surnameField.getText() + "%')");
        sb.append(" AND LOWER(id::TEXT) SIMILAR TO LOWER('" + IDField.getText() + "%')");
        sb.append(" AND LOWER(phone_no) SIMILAR TO LOWER('" + phoneNoField.getText() + "%')");
        sb.append(" AND LOWER(email) SIMILAR TO LOWER('" + emailField.getText() + "%')");
        sb.append(";");
        String sql = sb.toString();
        ObservableList<Member> listOfMembers = FXCollections.observableArrayList(PostgreSQL.retrieveMembers(sql, conn));         
        ListPayload.set(listOfMembers);
        Stage currentStage = (Stage) updateButton.getScene().getWindow();
        currentStage.close();
      } catch ( SQLException e) {
        System.out.println(e.getMessage());
      }
    });

  }


}
