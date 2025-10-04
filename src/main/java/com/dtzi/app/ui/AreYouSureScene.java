package com.dtzi.app.ui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class AreYouSureScene {

  public AreYouSureScene() {
  }

  public void start(Stage stage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/view/areYouSureScreen.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
