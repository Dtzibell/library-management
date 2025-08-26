package com.dtzi.app.classes;

import java.time.LocalDate;

public class Book {
  final int ISBN, publicationYear;
  final String title, author, genre;
  boolean availability;
  final LocalDate dateAdded;
  
  public Book (int ISBN, int publicationYear, String title, String author, String genre,
      boolean availability, LocalDate dateAdded) {
  this.ISBN = ISBN;
  this.publicationYear = publicationYear;
  this.title = title;
  this.author = author;
  this.genre = genre;
  this.availability = availability;
  this.dateAdded = dateAdded;
  }
  
  public int getISBN() {
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

  public String getGenre() {
    return this.genre;
  }

  public boolean getAvail() {
    return this.availability;
  }

  public LocalDate getDateAdded() {
    return this.dateAdded;
  }

  public void setAvail(boolean newAvail) {
    this.availability = newAvail;
  }
}
