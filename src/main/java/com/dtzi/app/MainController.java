package com.dtzi.app;

import com.dtzi.app.classes.Book;
import com.dtzi.app.classes.DBObject;
import com.dtzi.app.classes.Member;
import com.dtzi.app.controllers.ConfirmationController;
import com.dtzi.app.pgutils.PostgreSQL;
import com.dtzi.app.ui.*;
import com.sun.net.httpserver.Authenticator.Result;

import java.awt.Desktop.Action;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.collections.FXCollections;
import javafx.scene.control.ListCell;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainController implements Initializable {

  public Connection conn;

  // ---------------------
  // MEMBER SIDE
  // ---------------------
  @FXML
  private ListView<Member> personList;
  @FXML
  private VBox memberForm;

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
  private ErrorScene errorScene = new ErrorScene();

  private Clipboard clipboard = Clipboard.getSystemClipboard();
  private ClipboardContent content = new ClipboardContent();

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    try {
      Connection conn = PostgreSQL.connect();

      listOfMembers = FXCollections
          .observableArrayList(PostgreSQL.retrieveMembers("SELECT * FROM members LIMIT 20", conn));
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
            setText(new String(item.getName() + " " + item.getSurname()));
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
            int titleLength = item.getTitle().length();
            try {
              setText(new String(item.getAuthor() + ": " + item.getTitle().substring(0, 20)));
            } catch (IndexOutOfBoundsException e) {
              setText(new String(item.getAuthor() + ": " + item.getTitle().substring(0, titleLength)));
            }
          }
        }
      });

      // set the textfields to the selected item's properties
      personList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
          replaceFieldText(newValue);
          ObservableList<Node> nodeList = memberForm.getChildren();
          try {
            nodeList.remove(3, nodeList.size());
            ResultSet loans = getMembersLoans(textID.getText(), conn);
            while (loans.next()) {
              addLoanToMemberForm(loans);
            }
          } catch (Exception e) {
            System.out.println(e.getMessage());
          }
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
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    textPubYear.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.matches("[0-9]*"))
        bookUpdateButton.setDisable(false);
      else
        bookUpdateButton.setDisable(true);
    });

  }

  private void copyToClipboard(MouseEvent event) {
    Label label = (Label) event.getSource();
    content.putString(label.getText());
    clipboard.setContent(content);
  }

  private ResultSet getMembersLoans(String ID, Connection conn) throws SQLException {
    PreparedStatement prep = conn.prepareStatement("SELECT * FROM loans WHERE user_id = ?::uuid");
    prep.setString(1, ID);
    ResultSet loans = prep.executeQuery();
    return loans;
  }

  private void addLoanToMemberForm(ResultSet loans) throws SQLException {
    HBox LineBox = new HBox();
    Label bookLabel = new Label(loans.getString("isbn"));
    bookLabel.setOnMouseClicked(this::copyToClipboard);
    Button returnBook = new Button("Return");
    returnBook.setOnAction(this::returnBook);
    LineBox.getChildren().addAll(bookLabel, returnBook);
    memberForm.getChildren().add(LineBox);
  }

  private void replaceFieldText(DBObject selectedObj) {
    switch (selectedObj) {
      case Member currSelected -> { 
      textName.setText(currSelected.getName());
      textSurname.setText(currSelected.getSurname());
      textID.setText(currSelected.getID());
      textPhoneNo.setText(currSelected.getPhoneNo());
      textEmail.setText(currSelected.getEmail());
      }
      case Book currSelected -> {
      textAuthor.setText(currSelected.getAuthor());
      textTitle.setText(currSelected.getTitle());
      textISBN.setText(currSelected.getISBN());
      textPubYear.setText(Integer.toString(currSelected.getPubYear()));
      }
      default -> {
        throw new IllegalArgumentException("Unsupported object type: " + selectedObj.getClass().getName());
      }
    }
  }

  @FXML
  private void returnBook(ActionEvent event) {
    try {
      Button button = (Button) event.getSource();
      Connection conn = PostgreSQL.connect();
      HBox parent = (HBox) button.getParent();
      Label label = (Label) parent.getChildren().get(0);
      String ISBN = label.getText();
      PreparedStatement prep = conn.prepareStatement("""
          BEGIN;
          UPDATE books SET available = 'true' WHERE isbn = ?;
          DELETE FROM loans WHERE isbn = ?;
          COMMIT;
          """);
      prep.setString(1, ISBN);
      prep.setString(2, ISBN);
      prep.executeUpdate();
      memberForm.getChildren().remove(button.getParent());
    } catch (Exception e) {
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
      Member toChange = personList.getSelectionModel().getSelectedItem();
      Connection conn = PostgreSQL.connect();
      String newName = textName.getText();
      String newSurname = textSurname.getText();
      String newPhoneNo = textPhoneNo.getText();
      String newEmail = textEmail.getText();
      toChange.update(newName, newSurname, newPhoneNo, newEmail, conn);
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
        UPDATE books SET available = 'false' WHERE isbn = ?;
        COMMIT;""");
    prep.setString(1, mem.IDProperty().get());
    prep.setString(2, book.ISBNProperty().get());
    prep.setDate(3, Date.valueOf(LocalDate.now()));
    prep.setString(4, book.ISBNProperty().get());
    prep.executeUpdate();
    ResultSet loans = getMembersLoans(textID.getText(), conn);
    while (loans.next()) {
      addLoanToMemberForm(loans);
    }
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
      Book toChange = bookList.getSelectionModel().getSelectedItem();
      Connection conn = PostgreSQL.connect();
      String newTitle = textTitle.getText();
      String newAuthor = textAuthor.getText();
      int newPubYear = Integer.parseInt(textPubYear.getText());
      toChange.update(newTitle, newAuthor, newPubYear, conn);
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
