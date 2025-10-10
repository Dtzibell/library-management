package com.dtzi.app.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Book {
  
  SimpleIntegerProperty publicationYear;
  SimpleStringProperty title, author, ISBN;
  SimpleBooleanProperty available;

  public Book(){
  }

  public Book(String title, String author, int publicationYear, String ISBN) {
    this.title = new SimpleStringProperty(title);
    this.author = new SimpleStringProperty(author);
    this.publicationYear = new SimpleIntegerProperty(publicationYear);
    this.ISBN = new SimpleStringProperty(ISBN);
  }

  public Book(String title, String author, int publicationYear, String ISBN, Connection conn) {
    this.title = new SimpleStringProperty(title);
    this.author = new SimpleStringProperty(author);
    this.publicationYear = new SimpleIntegerProperty(publicationYear);
    this.ISBN = new SimpleStringProperty(ISBN);

    try{
      PreparedStatement prep = conn.prepareStatement("INSERT INTO books(title, author, pub_date, isbn) VALUES(?,?,?,?)");
      prep.setString(1,title);
      prep.setString(2,author);
      prep.setInt(3,publicationYear);
      prep.setString(4,ISBN);
      System.out.println(prep.toString());
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public SimpleStringProperty ISBNProperty() {
    return this.ISBN;
  }

  public SimpleIntegerProperty pubYearProperty() {
    return this.publicationYear;
  }

  public SimpleStringProperty titleProperty() {
    return this.title;
  }

  public SimpleStringProperty authorProperty() {
    return this.author;
  }

  public void setISBN(String newISBN) {
    this.ISBN.set(newISBN);
  }

  public void setPubYear(int newPubYear) {
    this.publicationYear.set(newPubYear);
  }

  public void setTitle(String newTitle) {
    this.title.set(newTitle);
  }

  public void setAuthor(String newAuthor) {
    this.title.set(newAuthor);
  }
}
