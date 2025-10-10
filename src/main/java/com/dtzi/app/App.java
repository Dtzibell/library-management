package com.dtzi.app;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.fxml.FXMLLoader;

public class App extends Application {

  MainController mainContr;

  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainScreen.fxml"));
    Parent root = loader.load();
    mainContr = loader.getController();
    Scene scene = new Scene(root, Color.ALICEBLUE);
    stage.setTitle("test");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

}
