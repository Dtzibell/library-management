package com.dtzi.app.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

import com.dtzi.app.MainController;
import com.dtzi.app.classes.Book;
import com.dtzi.app.classes.Member;
import com.dtzi.app.pgutils.PostgreSQL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ConfirmationController implements Initializable {

  @FXML
  private Label question;
  private Runnable runnable;

  public void initialize(URL url, ResourceBundle rb) {
  }

  @FXML
  public void onConfirmAction() {
    System.out.println(runnable);
    if (this.runnable != null) {
      this.runnable.run();
    } else
      System.out.println("No runnable action attached to confirmation button");
  }

  public void setRunnable(Runnable func) {
    this.runnable = func;
  }

  public void removeMember() {
    try {
      Connection conn = PostgreSQL.connect();
      Member toDelete = MainController.listOfMembers.get(MainController.memberIndex);
      PreparedStatement prep = conn.prepareStatement("DELETE FROM members WHERE id = ?::uuid");
      prep.setString(1, toDelete.IDProperty().get());
      prep.executeUpdate();
      System.out.println(prep.toString());
      MainController.listOfMembers.remove(toDelete);
      closeWindow();
    } catch (Exception e) {
      System.out.print(e.getMessage());
    }
  }

  public void removeBook() {
    try {
      Connection conn = PostgreSQL.connect();
      Book toDelete = MainController.listOfBooks.get(MainController.bookIndex);
      PreparedStatement prep = conn.prepareStatement("DELETE FROM books WHERE isbn = ?");
      prep.setString(1, toDelete.ISBNProperty().get());
      prep.executeUpdate();
      MainController.listOfBooks.remove(toDelete);
      closeWindow();
    } catch (Exception e) {
      System.out.print(e.getMessage());
    }
  }

  @FXML
  public void closeWindow() throws Exception {
    Stage stage = (Stage) question.getScene().getWindow();
    stage.close();
  }
}
