package com.dtzi.app.classes;

import com.dtzi.app.classes.Book;
import java.util.List;
import java.time.LocalDate;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.fasterxml.jackson.core.JsonProcessingException;

public class Dataset {

  public List<Book> books;
  public LocalDate lastChanged;
  public ObjectMapper mapper = new ObjectMapper();
  Scanner dataScanner = new Scanner("data.json");

  public List<Book> getBooks() {
    return this.books;
  }

  public LocalDate getLastChanged() {
    return lastChanged;
  }

  public Dataset (List<Book> books) {
    this.books = books;
    this.lastChanged = LocalDate.now();
    mapper.registerModule(new JavaTimeModule());
  }

  public void addBook(Book bookToAdd) {
    books.add(bookToAdd);
    lastChanged = LocalDate.now();
    // try {
    //   FileWriter dataFile = new FileWriter("data.json");
    //   String serializedBook = mapper.writeValueAsString(bookToAdd);

    // }
    // catch (JsonProcessingException e) {
    //   System.out.println(e);
    // }
    // catch (IOException e) {
    //   System.out.println("Data file is inaccessible");
    // }
  }

  public void removeBook(Book bookToRemove) {
    books.remove(bookToRemove);
    lastChanged = LocalDate.now();
  }

}
