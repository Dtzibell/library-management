package com.dtzi.app;

// import com.dtzi.app.classes.Book;
// import com.dtzi.app.classes.Member.Email;
// import com.dtzi.app.classes.Member.Email.EmailVerificationException;
// import com.dtzi.app.classes.Member;

// 
// import java.util.List;
// import java.util.Random;
// import java.util.UUID;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.time.LocalDate;
import java.sql.Connection;
// import java.sql.DatabaseMetaData;
// import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
// import java.sql.Statement;
import java.lang.Math.*;
import java.net.URL;

// public class App {
//    public static void main(String[] args) {
//      // Always test connection to the server before running other tests
//     try (Connection conn = PostgreSQL.connect()) {
//       
//     } catch (SQLException e) {
//       System.out.println(e.getErrorCode() + e.getMessage());
//       System.out.println("SERVER FAILED TO CONNECT");
//     }
//     // // Test email verification
//     // Email myEmail = new Email("zaleniakas.tauras@gmail.com");
//     // Email yourEmail = new Email("zaleniakas.tauras@mymail.xyz");
//     // Email ourEmail = new Email("wow@mymail.com");
//     // Email emailll = new Email("wow,@mymail.com");
// 
//     // Test connection to database and iteration over rows
//     //try (Connection connection = PostgreSQL.connect()) {
//     //  System.out.println("Connection successful," + connection);
//     //  PostgreSQL.iterateOverMembers(connection);
//     //  // List<Book> extractedBooks = PostgreSQL.retrieveBooks("SELECT * FROM books", connection);
//     //} catch (SQLException e) {
//     //  System.err.format("SQL State: %s\n Message: %s", e.getSQLState(), e.getMessage());
//     //}
//   }
// }

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.fxml.FXMLLoader;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
      Parent root = FXMLLoader.load(getClass().getResource("/view/screen.fxml"));
      Scene scene = new Scene(root, 400, 400, Color.ALICEBLUE);
      // scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
      stage.setTitle("test");
      stage.setScene(scene);
      stage.show();
    }

    public static void main(String[] args) {
      launch(args);
    }

}
