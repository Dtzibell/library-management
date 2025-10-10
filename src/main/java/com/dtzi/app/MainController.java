package com.dtzi.app;

import com.dtzi.app.classes.Book;
import com.dtzi.app.classes.Member;
import com.dtzi.app.controllers.ConfirmationController;
import com.dtzi.app.pgutils.PostgreSQL;
import com.dtzi.app.ui.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.control.ListView;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.collections.FXCollections;
import javafx.scene.control.ListCell;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainController implements Initializable{
  
  public Connection conn;

  // ---------------------
  // MEMBER SIDE
  // ---------------------
  @FXML
  private ListView<Member> personList;

  @FXML
  private TextField textName;
  @FXML
  private TextField textSurname;
  @FXML
  private Label textID;
  @FXML
  private TextField textPhoneNo;
  @FXML
  private TextField textEmail;
  @FXML
  public static ObservableList<Member> listOfMembers;
  public static int memberIndex; // used to delete items from list of members

  // ---------------------
  // BOOK SIDE
  // ---------------------
  // @FXML
  // private ListView<Book> bookList;
  @FXML
  private TextField textTitle;
  @FXML
  private TextField textAuthor;
  @FXML
  private TextField textPubYear;
  @FXML
  private TextField textISBN;
  @FXML
  private Button bookFilterButton;


  // static
  public static ObservableList<Book> listOfBooks;
  public static int bookIndex;
  
  // Windows init
  private FilterScene<Member> memberFilterScene = new FilterScene<Member>(new Member());
  private AddScene<Member> memberAddScene = new AddScene<Member>(new Member());
 //  private FilterScene<Book> bookFilterScene = new FilterScene<Book>();
 //  private AddScene<Book> bookAddScene = new AddScene<Book>();
  private ConfirmationScene confirmationScene = new ConfirmationScene();
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    try {
      Connection conn = PostgreSQL.connect();

      listOfMembers = FXCollections.observableArrayList(PostgreSQL.retrieveMembers("SELECT * FROM members LIMIT 20", conn));
      personList.setItems(listOfMembers);

      // listOfBooks = FXCollections.observableArrayList(PostgreSQL.retrieveBooks("SELECT * FROM books LIMIT 20", conn));
      // bookList.setItems(listOfBooks);

      personList.setCellFactory(p -> new ListCell<>() {
        @Override
        protected void updateItem(Member item, boolean empty) {
          super.updateItem(item, empty);

          if (item == null || empty) {
            setText(null);
          } else {
            setText(new String(item.firstNameProperty().get() + " " + item.surnameProperty().get()));
          }
        }
      });

      // bookList.setCellFactory(p -> new ListCell<Book>() {
      //   @Override
      //   protected void updateItem(Book item, boolean empty) {
      //     super.updateItem(item, empty);

      //     if (item == null || empty) {
      //       setText(null);
      //     } else {
      //       setText(new String(item.authorProperty().get() + " " + item.titleProperty().get().substring(0,20)));
      //     }
      //   }
      // });

      // set the textfields to the selected item's properties
      personList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
          textName.setText(newValue.firstNameProperty().get());
          textSurname.setText(newValue.surnameProperty().get());
          textID.setText(newValue.IDProperty().get());
          textPhoneNo.setText(newValue.phoneNumberProperty().get());
          textEmail.setText(newValue.emailProperty().get());
        }
      });
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

  }

  @FXML
  private void filterMembers() throws Exception {
    Stage stage = new Stage();
    memberFilterScene.start(stage);
  }

  @FXML
  private void addMember() throws Exception {
    Stage stage = new Stage();
    memberAddScene.start(stage);
  }

  @FXML
  private void removeMember() throws Exception {
    memberIndex = personList.getSelectionModel().getSelectedIndex();
    if (memberIndex > -1) {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/confirmationScreen.fxml"));
      Parent root = loader.load();
      ConfirmationController contr = loader.getController();
      contr.setRunnable(contr::removeMember);
      Scene scene = new Scene(root);
      Stage stage = new Stage();
      stage.setScene(scene);
      confirmationScene.start(stage);
    }
  }

  @FXML
  private void updateMember() throws Exception {
    String userID = textID.getText();
    if (userID != "") {
      Connection conn = PostgreSQL.connect();
      String newName = textName.getText();
      String newSurname = textSurname.getText();
      String newPhoneNo = textPhoneNo.getText();
      String newEmail = textEmail.getText();
      PreparedStatement prep =  conn.prepareStatement("UPDATE members SET " +
          "name = ?, surname = ?, phone_no = ?, email = ? WHERE id = ?::uuid");
      prep.setString(1, newName);
      prep.setString(2, newSurname);
      prep.setString(3, newPhoneNo);
      prep.setString(4, newEmail);
      prep.setString(5, userID);
      prep.executeUpdate();
      Member toChange = personList.getSelectionModel().getSelectedItem();
      toChange.setName(newName);
      toChange.setSurname(newSurname);
      toChange.setPhoneNumber(newPhoneNo);
      toChange.setEmail(newEmail);
      personList.refresh();
    }
  }

  // @FXML
  // private void loan() throws Exception {
  // }

  // @FXML
  // private void addBook() throws Exception {
  //   // Connection conn = PostgreSQL.connect();
  //   // PreparedStatement prep = conn.prepareStatement("INSERT INTO books(title, author, pub_date, ISBN) VALUES(?,?,?,?)");
  // }

  // @FXML
  // private void updateBook() throws Exception {
  // }

  // @FXML 
  // private void removeBook() throws Exception {
  //   bookIndex = bookList.getSelectionModel().getSelectedIndex();
  //   if (bookIndex > -1) {
  //     Stage stage = new Stage();
  //     confirmationScene.start(stage);
  //   }
  // }
}
