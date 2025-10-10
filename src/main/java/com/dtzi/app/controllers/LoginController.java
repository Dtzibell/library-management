package com.dtzi.app.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.dtzi.app.MainController;
import com.dtzi.app.pgutils.PostgreSQL;
import com.dtzi.app.pgutils.PostgreSQL.ConnData;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {
  MainController mainContr = new MainController();

  @FXML
  TextField textIP;
  @FXML
  TextField textUsername;
  @FXML
  TextField textPassword;
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
  }

  public void login() {
    try {
      ConnData.setURI(textIP.getText());
      ConnData.setUser(textUsername.getText());
      ConnData.setPass(textPassword.getText());
      PostgreSQL.connect();
      Stage stage = new Stage();
      mainContr.start(stage);
      Stage curStage = (Stage) textIP.getScene().getWindow();
      curStage.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
