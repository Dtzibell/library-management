package com.dtzi.app;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class FilterScene {

  public FilterScene() {
  }

  public void start(Stage stage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/view/filterScreen.fxml"));
    Scene scene = new Scene(root, 200, 200);
    stage.setScene(scene);
    stage.show();
  }
}
