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
    if (t instanceof Book) {
      root = FXMLLoader.load(getClass().getResource("/view/filterScreen.fxml"));
    } else if (t instanceof Member) {
      root = FXMLLoader.load(getClass().getResource("/view/filterScreen.fxml"));
    } else {
      root = FXMLLoader.load(getClass().getResource("/view/errorScreen.fxml"));
    }
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
