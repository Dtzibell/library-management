package com.dtzi.app;

import com.dtzi.app.classes.Member;
import com.dtzi.app.pgutils.PostgreSQL;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.List;
import java.util.Map;

import javafx.scene.control.ListView;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.scene.control.ListCell;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.stage.Stage;
import javafx.beans.InvalidationListener;
import javafx.scene.control.Control;

public class MainController implements Initializable{

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
  private HashMap<String, TextField> fields;
  @FXML
  private Button updateButton;

  @FXML
  private Button filterButton;

  public static ObservableList<Member> listOfMembers;
  public Connection conn;

  // needs to exist for filter scene initialization.
  private FilterScene filterScene = new FilterScene();
  private AddScene addScene = new AddScene();
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    try {
      final Connection conn = PostgreSQL.connect();
      listOfMembers = FXCollections.observableArrayList(PostgreSQL.retrieveMembers("SELECT * FROM members LIMIT 20", conn));
      personList.setItems(listOfMembers);
      fields = new HashMap<String, TextField>(Map.of(
        "name", textName, "surname", textSurname, "phoneNo", textPhoneNo, "email", textEmail));

      // this cell factory creates cells consisting of only first name and surname, meanwhile the objects persist within listview.
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

      // set the textfields to the selected item's properties
      personList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (observable != null) {
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
  private void filter() throws Exception {
    Stage stage = new Stage();
    filterScene.start(stage);
  }

  public void add() throws Exception {
    Stage stage = new Stage();
    addScene.start(stage);
  }

  public void update() throws Exception {
    Connection conn = PostgreSQL.connect();
    String newName = fields.get("name").getText();
    String newSurname = fields.get("surname").getText();
    String newPhoneNo = fields.get("phoneNo").getText();
    String newEmail = fields.get("email").getText();
    PreparedStatement prep =  conn.prepareStatement("UPDATE members SET " +
        "name = ?, surname = ?, phone_no = ?, email = ? WHERE id = ?::uuid");
    prep.setString(1, newName);
    prep.setString(2, newSurname);
    prep.setString(3, newPhoneNo);
    prep.setString(4, newEmail);
    prep.setString(5, textID.getText());
    prep.executeUpdate();
    personList.getSelectionModel().getSelectedItem().firstNameProperty().set(newName);
    personList.getSelectionModel().getSelectedItem().surnameProperty().set(newSurname);
    personList.refresh();
  }
}
