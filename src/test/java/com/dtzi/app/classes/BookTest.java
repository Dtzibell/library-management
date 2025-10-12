package com.dtzi.app.classes;

import java.lang.IO;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class BookTest {

  @Test
  public void testInsertion() throws SQLException {

    Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb");
    Statement statement = conn.createStatement();
    statement.execute("CREATE TABLE books(title varchar, author varchar, pub_date int, isbn varchar)");
    Book book = new Book("1", "2", 2024, "0123456789");
    book.createSQLRow(conn);

    ResultSet books = statement.executeQuery("SELECT * FROM books");
    assertTrue(books.next());
    assertEquals("1", books.getString("title"));
    assertEquals("2", books.getString("author"));
    assertEquals(2024, books.getInt("pub_date"));
    assertEquals("0123456789", books.getString("isbn"));
  }

  @Test
  public void testUpdate() throws SQLException {

    Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb");
    Statement statement = conn.createStatement();
    Book book = new Book("1", "2", 2024, "0123456789");
    book.update("2", "1", 2025, conn);

    ResultSet books = statement.executeQuery("SELECT * FROM books");
    assertTrue(books.next());
    assertEquals("2", books.getString("title"));
    assertEquals("1", books.getString("author"));
    assertEquals(2025, books.getInt("pub_date"));
    assertEquals("0123456789", books.getString("isbn"));
  }
}
