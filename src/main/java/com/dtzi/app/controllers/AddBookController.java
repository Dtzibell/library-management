package com.dtzi.app.controllers;

import com.dtzi.app.MainController;
import com.dtzi.app.classes.Book;
import com.dtzi.app.pgutils.PostgreSQL;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class AddBookController implements Initializable {
  
  @FXML
  private TextField titleField;
  @FXML
  private TextField authorField;
  @FXML
  private TextField ISBNField;
  @FXML
  private TextField pubDateField;
  @FXML
  private Button addButton;
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    addButton.setDisable(true);
    pubDateField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.matches("[0-9]*"))
        addButton.setDisable(false);
      else
        addButton.setDisable(true);
    });
  }

  public void addBook() throws Exception {
    Connection conn = PostgreSQL.connect();
    String title = titleField.getText();
    String author = authorField.getText();
    String pubDate = pubDateField.getText();
    String ISBN = ISBNField.getText();
    PreparedStatement prep = conn.prepareStatement("INSERT INTO books(title, author, pub_date, isbn) VALUES (?,?,?,?);");
    prep.setString(1, titleField.getText());
    prep.setString(2, authorField.getText());
    prep.setInt(3, Integer.parseInt(pubDateField.getText()));
    prep.setString(4, ISBNField.getText());
    Book newBook = new Book(titleField.getText(), authorField.getText(), Integer.parseInt(pubDateField.getText()), ISBNField.getText(), conn);
    MainController.listOfBooks.add(newBook);
    System.out.println(prep.toString());
    Stage currentStage = (Stage) titleField.getScene().getWindow();
    currentStage.close();
  }
}
