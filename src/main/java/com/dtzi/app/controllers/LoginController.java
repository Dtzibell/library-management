package com.dtzi.app.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.dtzi.app.pgutils.PostgreSQL;
import com.dtzi.app.pgutils.PostgreSQL.ConnData;
import com.dtzi.app.ui.MainScene;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {
  MainScene mainScene = new MainScene();

  @FXML
  TextField textIP;
  @FXML
  TextField textUsername;
  @FXML
  TextField textPassword;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
  }

  public void login() throws Exception {
    ConnData.setURI(textIP.getText());
    ConnData.setUser(textUsername.getText());
    ConnData.setPass(textPassword.getText());
    PostgreSQL.connect();
    Stage stage = new Stage();
    mainScene.start(stage);
    Stage curStage = (Stage) textIP.getScene().getWindow();
    curStage.close();
  }
}
