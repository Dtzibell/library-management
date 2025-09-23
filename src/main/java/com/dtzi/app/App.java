package com.dtzi.app;

import com.dtzi.app.classes.Book;
import com.dtzi.app.classes.Member.Email;
import com.dtzi.app.classes.Member.Email.EmailVerificationException;
import com.dtzi.app.classes.Member;
import com.dtzi.app.PostgreSQL;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.lang.Math.*;

import com.fasterxml.jackson.core.JsonProcessingException;

public class App {
  public static void main(String[] args) {
    // // Test email verification
    // Email myEmail = new Email("zaleniakas.tauras@gmail.com");
    // Email yourEmail = new Email("zaleniakas.tauras@mymail.xyz");
    // Email ourEmail = new Email("wow@mymail.com");
    // Email emailll = new Email("wow,@mymail.com");

    // Test connection to database and iteration over rows
    try (Connection connection = PostgreSQL.connect()) {
      System.out.println("Connection successful," + connection);
      PostgreSQL.iterateOverMembers(connection);
      // List<Book> extractedBooks = PostgreSQL.retrieveBooks("SELECT * FROM books", connection);
    } catch (SQLException e) {
      System.err.format("SQL State: %s\n Message: %s", e.getSQLState(), e.getMessage());
    }
  }
}
