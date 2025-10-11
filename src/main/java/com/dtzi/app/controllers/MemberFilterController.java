package com.dtzi.app.controllers;

import com.dtzi.app.pgutils.PostgreSQL;
import com.dtzi.app.MainController;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class MemberFilterController implements Initializable {
  
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

  @Override
  public void initialize(URL url, ResourceBundle rb) {
  }

  public void resetFilter() throws Exception {
      Connection conn = PostgreSQL.connect();
      PreparedStatement prep = conn.prepareStatement("""
          SELECT * FROM members WHERE LOWER(name) SIMILAR TO LOWER(?)
          AND LOWER(surname) SIMILAR TO LOWER(?) AND LOWER(id::TEXT) SIMILAR TO LOWER(?)
          AND LOWER(phone_no) SIMILAR TO LOWER(?) AND LOWER(email) SIMILAR TO LOWER(?)""");
      prep.setString(1, "%"+nameField.getText()+"%");
      prep.setString(2, "%"+surnameField.getText()+"%");
      prep.setString(3, "%"+IDField.getText()+"%");
      prep.setString(4, "%"+phoneNoField.getText()+"%");
      prep.setString(5, "%"+emailField.getText()+"%");
      System.out.println(prep.toString());
      MainController.listOfMembers.clear();
      MainController.listOfMembers.addAll(PostgreSQL.retrieveMembers(prep.toString(), conn));
      Stage currentStage = (Stage) nameField.getScene().getWindow();
      currentStage.close();
  }
}
