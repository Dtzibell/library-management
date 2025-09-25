package com.dtzi.app;

// import com.dtzi.app.classes.Book;
// import com.dtzi.app.classes.Member.Email;
// import com.dtzi.app.classes.Member.Email.EmailVerificationException;
// import com.dtzi.app.classes.Member;
import com.dtzi.app.pgutils.PostgreSQL;

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
// import java.sql.Statement;
import java.lang.Math.*;

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

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Insets;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
      // Ellipse myEllipse = new Ellipse(110, 78);
      // myEllipse.setFill(Color.AZURE);
      // Text myText = new Text("my shapes");
      // myText.setFont(new Font("DejaVu Sans Mono", 24));
      // StackPane pane = new StackPane();
      // pane.getChildren().addAll(myEllipse,myText);
      // StackPane.setAlignment(myText, Pos.TOP_LEFT);
      // StackPane.setAlignment(myEllipse, Pos.TOP_LEFT);
      // AnchorPane pane2 = new AnchorPane();
      // Label myLabel = new Label("My Label");
      // pane2.getChildren().add(myLabel);
      // AnchorPane.setLeftAnchor(myLabel, 10.0);
      // AnchorPane.setRightAnchor(myLabel, 10.0);
      // pane.getChildren().add(pane2);
      BorderPane borderPane = new BorderPane();
      Label colorLabel = new Label("Color: Lightblue");
      colorLabel.setFont(new Font("Verdana", 18));
      borderPane.setTop(colorLabel);
      Stop[] stops = new Stop[]{new Stop(0, Color.BLACK), new Stop(0.5, Color.AQUAMARINE), new Stop(1, Color.WHITE)};
      LinearGradient gradient = new LinearGradient(0,0,50,50,false,CycleMethod.REFLECT, stops);
      Rectangle rectangle = new Rectangle(100, 50, gradient);
      Rectangle rectangle2 = new Rectangle(100, 50, gradient);
      Rectangle rectangle3 = new Rectangle(100, 50, gradient);
      RotateTransition rotation = new RotateTransition(Duration.millis(2500), rectangle2);
      rotation.setFromAngle(0);
      rotation.setToAngle(360);
      rotation.setInterpolator(Interpolator.LINEAR);
      RotateTransition rotation2 = new RotateTransition(Duration.millis(2500), rectangle3);
      rotation2.setFromAngle(0);
      rotation2.setToAngle(360);
      rotation2.setInterpolator(Interpolator.EASE_BOTH);
      rectangle.setEffect(new DropShadow(30, 10, 10, Color.BLACK));
      rectangle2.setEffect(new DropShadow(30, 10, 10, Color.AQUAMARINE));
      rectangle3.setEffect(new DropShadow(10, 10, 10, Color.BLACK));
      rectangle.setOnMouseClicked(mouseEvent -> {
          System.out.println(mouseEvent.getSource().getClass() + " clicked.");
          if (rotation.getStatus().equals(Animation.Status.RUNNING))
            rotation.pause();
          else
            rotation.play();
          if (rotation2.getStatus().equals(Animation.Status.RUNNING))
            rotation2.pause();
          else
            rotation2.play();
      });
      borderPane.setCenter(rectangle);
      borderPane.setRight(rectangle2);
      borderPane.setLeft(rectangle3);
      BorderPane.setAlignment(colorLabel, Pos.CENTER);
      BorderPane.setMargin(colorLabel, new Insets(20,10,5,10));
      Scene scene = new Scene(borderPane, 800, 600, Color.AQUA);
      stage.setTitle("shape drawing");
      stage.setScene(scene);
      stage.show();
    }

    public static void main(String[] args) {
      launch(args);
    }

}
