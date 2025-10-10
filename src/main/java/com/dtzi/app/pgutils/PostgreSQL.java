package com.dtzi.app.pgutils;

import com.dtzi.app.classes.Book;
import com.dtzi.app.classes.Member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

public interface PostgreSQL {
  public interface ConnData {
    static String uri = "jdbc:postgresql://localhost:5432/postgres";
    static String user = "postgres";
    static String passw = "grespost";
  }

  //public static function
  //
  // Establishes a connection with the driver, returns the connection obj.
  //
  //returns Connection connection
  public static Connection connect() throws SQLException {
    final Connection connection = DriverManager.getConnection(
        ConnData.uri, ConnData.user, ConnData.passw);
    return connection;
  }

  // --------------------------
  // BOOKS RELATED METHODS
  // --------------------------
  
  //public static function
  //@param String sql
  //returns List<Book> extractedBooks
  public static List<Book> retrieveBooks (String sql, Connection conn) {
    List<Book> extractedBooks = new ArrayList<>(); 
    try {
      Statement statement = conn.createStatement();
      ResultSet allBooks = statement.executeQuery(sql);
      while (allBooks.next()) {
        String bookTitle = allBooks.getString("title");
        String bookAuthor = allBooks.getString("author");
        int bookPublicationYear = allBooks.getInt("pub_date");
        String bookISBN = allBooks.getString("ISBN");
        Book newBook = new Book(bookTitle, bookAuthor, bookPublicationYear, bookISBN);
        extractedBooks.add(newBook);
      }
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    System.out.println(extractedBooks);
    return extractedBooks; 
  }
  
  // ------------------------
  // MEMBER RELATED METHODS
  // ------------------------

  //public static function
  //@param String sql
  //returns List<Book> extractedBooks
  public static List<Member> retrieveMembers (String sql, Connection conn) {
    List<Member> extractedMembers = new ArrayList<>(); 
    try {
      Statement statement = conn.createStatement();
      ResultSet allMembers = statement.executeQuery(sql);
      while (allMembers.next()) {
        String userName = allMembers.getString("name");
        String userSurname = allMembers.getString("surname");
        String userID = allMembers.getString("id");
        String userPhoneNumber = allMembers.getString("phone_no");
        String userEmail = allMembers.getString("email");
        Member newMember = new Member(userName, userSurname, userID, userPhoneNumber, userEmail);
        extractedMembers.add(newMember);
      }
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return extractedMembers; 
  }
}
