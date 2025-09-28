package com.dtzi.app;

import com.dtzi.app.classes.Member;
import com.dtzi.app.pgutils.PostgreSQL;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.control.ListView;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ListCell;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController implements Initializable{

  @FXML
  private ListView<Member> personList;

  @FXML
  private TextField textName;
  @FXML
  private TextField textSurname;
  @FXML
  private TextField textID;
  @FXML
  private TextField textPhoneNo;
  @FXML
  private TextField textEmail;

  @FXML
  private Button filterButton;

  private FilterScene filterScene = new FilterScene();

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    try (Connection conn = PostgreSQL.connect()) {
      ListPayload.loadAll(conn);
      personList.setItems(ListPayload.toTransfer);
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

      personList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (observable != null) {
          textName.setText(newValue.firstNameProperty().get());
          textSurname.setText(newValue.surnameProperty().get());
          textID.setText(newValue.IDProperty().get());
          textPhoneNo.setText(newValue.phoneNumberProperty().get());
          textEmail.setText(newValue.emailProperty().get());
        }
      });

      textName.textProperty().addListener((observable, oldValue, newValue) -> {
        personList.getSelectionModel().getSelectedItem().firstNameProperty().set(newValue);
      });
      textSurname.textProperty().addListener((observable, oldValue, newValue) -> {
        personList.getSelectionModel().getSelectedItem().surnameProperty().set(newValue);
      });
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  @FXML
  private void filter() throws Exception {
    Stage stage = new Stage();
    filterScene.start(stage);
  }

  public static class ListPayload {
    public static ObservableList<Member> toTransfer;

    public static void loadAll(Connection conn){
      ListPayload.toTransfer = FXCollections.observableArrayList(
          PostgreSQL.retrieveMembers("SELECT * FROM members", conn));
    }

    public static void set(ObservableList<Member> transferable) {
      ListPayload.toTransfer.clear();
      ListPayload.toTransfer.addAll(transferable);
    }
  }
}
