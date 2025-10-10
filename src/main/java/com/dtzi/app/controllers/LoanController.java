// package com.dtzi.app.controllers;
// 
// import java.net.URL;
// import java.sql.Connection;
// import com.dtzi.app.pgutils.PostgreSQL;
// import com.dtzi.app.ui.FilterScene;
// import com.dtzi.app.MainController;
// import com.dtzi.app.classes.Book;
// import com.dtzi.app.classes.Member;
// import java.sql.PreparedStatement;
// import java.util.ResourceBundle;
// 
// import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;
// import javafx.fxml.FXML;
// import javafx.fxml.Initializable;
// import javafx.scene.control.Label;
// import javafx.scene.control.ListView;
// import javafx.scene.layout.Region;
// import javafx.stage.Stage;
// import javafx.scene.control.ListCell;
// 
// public class LoanController implements Initializable {
// 
//   @FXML
//   private ListView<Book> bookList;
// 
//   private FilterScene<Book> bookFilter;
// 
//   public void initialize (URL url, ResourceBundle rb) {
//     bookList.setCellFactory(p -> new ListCell<Book>() {
//       @Override
//       protected void updateItem(Book item, boolean empty) {
//         super.updateItem(item, empty);
// 
//         if (item == null || empty) {
//           setText(null);
//         } else {
//           setText(new String(item.authorProperty().get() + " " + item.titleProperty().get().substring(0,20)));
//         }
//       }
//     });
//     bookList.setItems(MainController.listOfBooks);
//   }
// 
//   public void filterBooks() throws Exception {
//     Stage stage = new Stage();
//     bookFilter.start(stage);
//   }
// }
