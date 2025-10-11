package com.dtzi.app.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book implements DBObject {
  
  SimpleIntegerProperty publicationYear;
  // TODO: make ISBN final. Cant have empty constructor though
  SimpleStringProperty title, author, ISBN;
  // SimpleBooleanProperty available;

  public Book(){
  }

  public Book(String title, String author, int publicationYear, String ISBN) {
    this.title = new SimpleStringProperty(title);
    this.author = new SimpleStringProperty(author);
    this.publicationYear = new SimpleIntegerProperty(publicationYear);
    this.ISBN = new SimpleStringProperty(ISBN);
  }

  public Book(String title, String author, int publicationYear, String ISBN, Connection conn) throws SQLException {
    this(title, author, publicationYear, ISBN);
    createSQLRow(conn);
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

  public String getTitle() {
    return this.title.get();
  }

  public String getAuthor() {
    return this.author.get();
  }

  public String getISBN() {
    return this.ISBN.get();
  }

  public int getPubYear() {
    return this.publicationYear.get();
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
    this.author.set(newAuthor);
  }
  
  public void createSQLRow(Connection conn) throws SQLException {
    PreparedStatement prep = conn.prepareStatement("INSERT INTO books(title, author, pub_date, isbn) VALUES(?,?,?,?)");
    prep.setString(1, this.getTitle());
    prep.setString(2, this.getAuthor());
    prep.setInt(3, this.getPubYear());
    prep.setString(4, this.getISBN());
    prep.executeUpdate();
    System.out.println(prep.toString());
  }

  public void update(String newTitle, String newAuthor, int newPubYear, Connection conn) throws SQLException {
      PreparedStatement prep = conn.prepareStatement("UPDATE books SET " +
          "title = ?, author = ?, pub_date = ? WHERE isbn = ?");
      prep.setString(1, newTitle);
      prep.setString(2, newAuthor);
      prep.setInt(3, newPubYear);
      prep.setString(4, this.getISBN());
      prep.executeUpdate();
      this.setTitle(newTitle);
      this.setAuthor(newAuthor);
      this.setPubYear(newPubYear);
  }
}
