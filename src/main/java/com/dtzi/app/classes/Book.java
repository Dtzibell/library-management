package com.dtzi.app.classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Book {
  int publicationYear;
  String title, author, ISBN;
  boolean availability;

  public Book(String title, String author, int publicationYear, String ISBN) {
    this.title = title;
    this.author = author;
    this.publicationYear = publicationYear;
    this.ISBN = ISBN;
  }

  public String getISBN() {
    return this.ISBN;
  }

  public int getPubYear() {
    return this.publicationYear;
  }

  public String getTitle() {
    return this.title;
  }

  public String getAuthor() {
    return this.author;
  }

  public boolean getAvail() {
    return this.availability;
  }

  public void setAvail(boolean newAvail) {
    this.availability = newAvail;
  }

  public void setISBN(String newISBN) {
    this.ISBN = newISBN;
  }

  public void setPubYear(int newPubYear) {
    this.publicationYear = newPubYear;
  }

  public void setTitle(String newTitle) {
    this.title = newTitle;
  }

  public void setAuthor(String newAuthor) {
    this.title = newAuthor;
  }
}
