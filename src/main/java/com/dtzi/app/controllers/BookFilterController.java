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

public class BookFilterController implements Initializable {
  
  @FXML
  private TextField titleField;
  @FXML
  private TextField authorField;
  @FXML
  private TextField pubYearField;
  @FXML
  private TextField ISBNField;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
  }

  public void resetBookFilter() throws Exception {
      Connection conn = PostgreSQL.connect();
      PreparedStatement prep = conn.prepareStatement("""
          SELECT * FROM books WHERE LOWER(title) SIMILAR TO LOWER(?)
          AND LOWER(author) SIMILAR TO LOWER(?) AND LOWER(pub_date) SIMILAR TO LOWER(?)
          AND LOWER(ISBN) SIMILAR TO LOWER(?)""");
      prep.setString(1, titleField.getText()+"%");
      prep.setString(2, authorField.getText()+"%");
      prep.setString(3, pubYearField.getText()+"%");
      prep.setString(4, ISBNField.getText()+"%");
      MainController.listOfMembers.clear();
      MainController.listOfMembers.addAll(PostgreSQL.retrieveMembers(prep.toString(), conn));
      Stage currentStage = (Stage) titleField.getScene().getWindow();
      currentStage.close();
  }
}
