package com.dtzi.app;

import com.dtzi.app.classes.Book;
import com.dtzi.app.classes.Member;
import com.dtzi.app.controllers.ConfirmationController;
import com.dtzi.app.pgutils.PostgreSQL;
import com.dtzi.app.ui.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
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
  @FXML
  private ListView<Book> bookList;
  @FXML
  private TextArea textTitle;
  @FXML
  private TextField textAuthor;
  @FXML
  private TextField textPubYear;
  @FXML
  private Label textISBN;
  @FXML
  private Button bookFilterButton;
  @FXML
  private Button bookUpdateButton;


  // static
  public static ObservableList<Book> listOfBooks;
  public static int bookIndex;
  
  // Windows init
  // TODO: passing objects into inits is ultra messy. Other solutions?
  private FilterScene<Member> memberFilterScene = new FilterScene<Member>(new Member());
  private AddScene<Member> memberAddScene = new AddScene<Member>(new Member());
  private FilterScene<Book> bookFilterScene = new FilterScene<Book>(new Book());
  private AddScene<Book> bookAddScene = new AddScene<Book>(new Book());
  private ConfirmationScene confirmationScene = new ConfirmationScene();

  public void start(Stage stage) throws Exception{
    Parent root = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    try {
      Connection conn = PostgreSQL.connect();

      listOfMembers = FXCollections.observableArrayList(PostgreSQL.retrieveMembers("SELECT * FROM members LIMIT 20", conn));
      personList.setItems(listOfMembers);

      listOfBooks = FXCollections.observableArrayList(PostgreSQL.retrieveBooks("SELECT * FROM books LIMIT 20", conn));
      bookList.setItems(listOfBooks);

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

      bookList.setCellFactory(p -> new ListCell<Book>() {
        @Override
        protected void updateItem(Book item, boolean empty) {
          super.updateItem(item, empty);

          if (item == null || empty) {
            setText(null);
          } else {
            int titleLength = item.titleProperty().get().length();
            try {
              setText(new String(item.authorProperty().get() + ": " + item.titleProperty().get().substring(0, 20)));
            } catch (IndexOutOfBoundsException e) {
              setText(new String(item.authorProperty().get() + ": " + item.titleProperty().get().substring(0, titleLength)));
            }
          }
        }
      });

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

      bookList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
          textAuthor.setText(newValue.authorProperty().get());
          textTitle.setText(newValue.titleProperty().get());
          textISBN.setText(newValue.ISBNProperty().get());
          textPubYear.setText(Integer.toString(newValue.pubYearProperty().get()));
        }
      });
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    textPubYear.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.matches("[0-9]*"))
        bookUpdateButton.setDisable(false);
      else
        bookUpdateButton.setDisable(true);
    });

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
      Stage stage = new Stage();
      ConfirmationController contr = confirmationScene.start(stage);
      contr.setRunnable(contr::removeMember);
    }
  }

  @FXML
  private void updateMember() throws Exception {
    String userID = textID.getText();
    System.out.println(personList.getSelectionModel().getSelectedItem());
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

  @FXML
  private void loan() throws Exception {
    Member mem = personList.getSelectionModel().getSelectedItem();
    Book book = bookList.getSelectionModel().getSelectedItem();
    Connection conn = PostgreSQL.connect();
    PreparedStatement prep = conn.prepareStatement("""
        BEGIN;
        INSERT INTO loans(user_id, isbn, date) VALUES(?::uuid,?,?);
        UPDATE books SET available = falls WHERE isbn = ?;
        COMMIT;""");
    prep.setString(1, mem.IDProperty().get());
    prep.setString(2, book.ISBNProperty().get());
    prep.setDate(3, Date.valueOf(LocalDate.now()));
    prep.setString(4, book.ISBNProperty().get());
    prep.executeUpdate();
  }

  @FXML 
  private void returnBook() throws Exception {
    // a
  }

  @FXML
  private void addBook() throws Exception {
    Stage stage = new Stage();
    bookAddScene.start(stage);
  }

  @FXML
  private void updateBook() throws Exception {
    String userID = textTitle.getText();
    System.out.println(bookList.getSelectionModel().getSelectedItem());
    if (userID != "") {
      Connection conn = PostgreSQL.connect();
      String newTitle = textTitle.getText();
      String newAuthor = textAuthor.getText();
      int newPubYear = Integer.parseInt(textPubYear.getText());
      PreparedStatement prep =  conn.prepareStatement("UPDATE books SET " +
          "title = ?, author = ?, pub_date = ? WHERE isbn = ?");
      prep.setString(1, newTitle);
      prep.setString(2, newAuthor);
      prep.setInt(3, newPubYear);
      prep.setString(4, textISBN.textProperty().get());
      prep.executeUpdate();
      Book toChange = bookList.getSelectionModel().getSelectedItem();
      toChange.setTitle(newTitle);
      toChange.setAuthor(newAuthor);
      toChange.setPubYear(newPubYear);
      personList.refresh();
    }
  }

  @FXML
  private void removeBook() throws Exception {
    bookIndex = bookList.getSelectionModel().getSelectedIndex();
    if (bookIndex > -1) {
      Stage stage = new Stage();
      ConfirmationController contr = confirmationScene.start(stage);
      contr.setRunnable(contr::removeBook);
    }
  }

  @FXML
  private void filterBooks() throws Exception {
    Stage stage = new Stage();
    bookFilterScene.start(stage);
  }
}
