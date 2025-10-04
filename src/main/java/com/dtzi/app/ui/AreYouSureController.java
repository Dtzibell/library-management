package com.dtzi.app.ui;

import java.net.URL;
import java.sql.Connection;
import com.dtzi.app.pgutils.PostgreSQL;
import com.dtzi.app.classes.Member;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class AreYouSureController implements Initializable {

  @FXML
  private Label question;

  public void initialize (URL url, ResourceBundle rb) {
  }

  @FXML
  private void remove() throws Exception {
    Connection conn = PostgreSQL.connect();
    Member toDelete = MainController.listOfMembers.get(MainController.index);
    PreparedStatement prep = conn.prepareStatement("DELETE FROM members WHERE id = ?::uuid");
    prep.setString(1, toDelete.IDProperty().get());
    prep.executeUpdate();
    System.out.println(prep.toString());
    MainController.listOfMembers.remove(toDelete);
    closeWindow();
  }

  @FXML
  private void closeWindow() throws Exception {
    Stage stage = (Stage) question.getScene().getWindow();
    stage.close();
  }
}
