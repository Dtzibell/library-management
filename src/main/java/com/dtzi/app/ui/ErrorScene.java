package com.dtzi.app.ui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.dtzi.app.classes.Member;
import com.dtzi.app.pgutils.PostgreSQL;
import com.dtzi.app.classes.Book;
import com.dtzi.app.MainController;
import com.dtzi.app.controllers.ConfirmationController;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class ErrorScene {
  Parent root;

  public ErrorScene() {
  }

  public void start(Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/errorScreen.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
