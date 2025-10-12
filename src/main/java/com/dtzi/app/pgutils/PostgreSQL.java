package com.dtzi.app.pgutils;

import com.dtzi.app.classes.Book;
import com.dtzi.app.classes.Member;
import com.dtzi.app.classes.Member.Email.EmailVerificationException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

public interface PostgreSQL {
  public class ConnData {
    static String uri;
    static String user;
    static String passw;

    public static void setURI(String newURI) {
      uri = "jdbc:postgresql://" + newURI + ":5432/postgres";
    }

    public static void setUser(String newUser) {
      user = newUser;
    }

    public static void setPass(String newPass) {
      passw = newPass;
    }
  }

  //public static function
  //
  // Establishes a connection with the driver, returns the connection obj.
  //
  //returns Connection connection
  public static Connection connect() throws SQLException {
    Connection connection = DriverManager.getConnection(
        ConnData.uri, ConnData.user, ConnData.passw);
    return connection;
  }

  // --------------------------
  // BOOKS RELATED METHODS
  // --------------------------
  
  //public static function
  //@param String sql
  //returns List<Book> extractedBooks
  public static List<Book> retrieveBooks(String sql, Connection conn) throws SQLException {
    List<Book> extractedBooks = new ArrayList<>();
    Statement statement = conn.createStatement();
    ResultSet books = statement.executeQuery(sql);
    while (books.next()) {
      String bookTitle = books.getString("title");
      String bookAuthor = books.getString("author");
      int bookPublicationYear = books.getInt("pub_date");
      String bookISBN = books.getString("ISBN");
      Book newBook = new Book(bookTitle, bookAuthor, bookPublicationYear, bookISBN);
      extractedBooks.add(newBook);
    }
    return extractedBooks;
  }
  
  // ------------------------
  // MEMBER RELATED METHODS
  // ------------------------

  //public static function
  //@param String sql
  //returns List<Book> extractedBooks
  public static List<Member> retrieveMembers (String sql, Connection conn) throws EmailVerificationException, SQLException {
    List<Member> extractedMembers = new ArrayList<>();
    Statement statement = conn.createStatement();
    ResultSet members = statement.executeQuery(sql);
    while (members.next()) {
      String userName = members.getString("name");
      String userSurname = members.getString("surname");
      String userID = members.getString("id");
      String userPhoneNumber = members.getString("phone_no");
      String userEmail = members.getString("email");
      Member newMember = new Member(userName, userSurname, userID, userPhoneNumber, userEmail);
      extractedMembers.add(newMember);
    }
    return extractedMembers;
  }
}
