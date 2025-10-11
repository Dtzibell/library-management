package com.dtzi.app.ui;

import com.dtzi.app.classes.Book;
import com.dtzi.app.classes.Member;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class FilterScene<T> {
  Parent root;
  T t;

  public FilterScene(T object) {
    t = object;
  }

  public void start(Stage stage) throws Exception {
    switch (t) {
      case Member m -> {
        root = FXMLLoader.load(getClass().getResource("/view/memberFilterScreen.fxml"));
      }
      case Book b -> {
        root = FXMLLoader.load(getClass().getResource("/view/bookFilterScreen.fxml"));
      }
      default -> {
        root = FXMLLoader.load(getClass().getResource("/view/errorScreen.fxml"));
        System.err.println("No valid root for new scene");
      }
    }
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
