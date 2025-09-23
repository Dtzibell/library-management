package com.dtzi.app;

import com.dtzi.app.classes.Book;
import com.dtzi.app.classes.Member;
import com.dtzi.app.classes.Member.Email.EmailVerificationException;
import com.dtzi.app.classes.Member.Email;

import java.util.Set;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
    if (connection != null) 
      System.out.println(connection);
    else 
      System.out.println("Connection failed");
    return connection;
  }


  // --------------------------
  // BOOKS RELATED METHODS
  // --------------------------
  
  //public static function
  //
  // executes a query and iterates over the rows of the outgoing resultset
  // conn is the Connection that is connected to the relevant database
  //
  //@param String sql -> SQL query to execute
  //@param Connection conn -> Connection to execute the query on
  //returns nothing
  public static void iterateOverRows (String sql, Connection conn) throws SQLException {
    Statement statement = conn.createStatement();
    ResultSet queryOutput = statement.executeQuery("SELECT * FROM books");
    ResultSetMetaData metadata = queryOutput.getMetaData();
    int numberOfColumns = metadata.getColumnCount();
    // TODO: check if formatting by pulling specific values from a set is possible
    // Set<Integer> gaps = Set.of(60, 30, 4, 30);
    System.out.println("-------------------");
    for (int i = 1; i <= numberOfColumns; i++) {
      System.out.format("| %s |", metadata.getColumnName(i));
    }
    System.out.println("\n-------------------");
    while (queryOutput.next()) {
      for (int i = 1; i <= numberOfColumns; i++) {
        System.out.format("| %s |", queryOutput.getString(i));
      }
      System.out.print("\n");
    }
  }
  
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
    for (int i = 0; i < extractedBooks.size(); i++) {
      Book iteratedBook = extractedBooks.get(i);
      System.out.println(iteratedBook.getTitle() + " by " + iteratedBook.getAuthor());
    }
    return extractedBooks; 
  }
  
  public static void addBook (String title, String author, int publicationYear, String ISBN, 
      Connection conn) {
    StringBuilder sb = new StringBuilder("INSERT INTO books(title, author, pub_date, isbn) VALUES (");
    sb.append(title + ", ");
    sb.append(author + ", ");
    sb.append(publicationYear);
    sb.append(", ");
    sb.append(ISBN);
    String sql = sb.toString();
    try {
      Statement statement = conn.createStatement();
      statement.executeQuery(sql);
    } catch (SQLException e) {
      System.out.println("Adding a book failed");
    }
  }

  // ------------------------
  // MEMBER RELATED METHODS
  // ------------------------

  //public static function
  //@param String 
  //returns nothing 
  public static void addMember (String userName, String userSurname, 
      String userID, String userEmail, String userPhoneNumber, Connection conn) {
    StringBuilder sb = new StringBuilder("INSERT INTO members (name, surname, id, phone_no, email) VALUES (");
    sb.append(userName + ", ");
    sb.append(userSurname + ", ");
    sb.append(userID + ", ");
    try {
      Email email = new Email(userEmail);
      sb.append(userEmail + ", ");
    } catch (EmailVerificationException e) {
      userEmail = null;
    }
    sb.append(userEmail + ", ");
    sb.append(userPhoneNumber);
    String sql = sb.toString();
    try {
      Statement statement = conn.createStatement();
      statement.executeQuery(sql);
    } catch (SQLException e) {
      System.out.println("Adding a member failed");
    }
  }

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
        Member newMember = new Member(userName, userSurname, userID, userEmail, userPhoneNumber);
        extractedMembers.add(newMember);
      }
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    for (int i = 0; i < extractedMembers.size(); i++) {
      Member iteratedMember = extractedMembers.get(i);
      System.out.println(iteratedMember.getName());
    }
    return extractedMembers; 
  }
}
