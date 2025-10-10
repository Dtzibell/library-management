package com.dtzi.app.ui;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MainScene {
  Parent root;

  public MainScene() {
  }

  public void start(Stage stage) throws Exception {
    root = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
