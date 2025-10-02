package com.dtzi.app;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class AddScene {

  public AddScene() {
  }

  public void start(Stage stage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/view/addScreen.fxml"));
    Scene scene = new Scene(root, 400, 200);
    stage.setScene(scene);
    stage.show();
  }
}
