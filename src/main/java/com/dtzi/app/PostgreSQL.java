package com.dtzi.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

public class PostgreSQL {
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
    Connection connection = DriverManager.getConnection(
        ConnData.uri, ConnData.user, ConnData.passw);
    if (connection != null) 
      System.out.println(connection);
    else 
      System.out.println("Connection failed");
    return connection;
  }

  //public static function
  //
  // executes a query and iterates over the rows of the outgoing resultset
  //
  //@param String sql -> SQL query to execute
  //@param Connection conn -> Connection to execute the query on
  //returns nothing
  public static void iterateOverRows (String sql, Connection conn) throws SQLException {
    Statement statement = conn.createStatement();
    ResultSet queryOutput = statement.executeQuery("SELECT * FROM books");
    ResultSetMetaData metadata = queryOutput.getMetaData();
    int numberOfColumns = metadata.getColumnCount();
    System.out.println("-------------------");
    for (int i = 1; i <= numberOfColumns; i++) {
      System.out.print(metadata.getColumnName(i) + " | ");
    }
    System.out.println("\n-------------------");
    while (queryOutput.next()) {
      for (int i = 1; i <= numberOfColumns; i++) {
        System.out.print(queryOutput.getString(i) + " | ");
      }
      System.out.print("\n");
    }
  }
}
