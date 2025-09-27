package com.dtzi.app;

import com.dtzi.app.classes.Member;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ListView;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.util.Callback;
import java.lang.IO;
import javafx.scene.control.ListCell;

public class FXMLController implements Initializable{

  ObservableList<Member> listOfMembers = FXCollections.observableArrayList(new Member("john", "johnson", "1", "0123456789", "john.johnson@mymail.xyz")); 

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

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    personList.setItems(listOfMembers);
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
    // textName.textProperty().addListener((observable, oldValue, newValue) -> {
    //   personList.getSelectionModel().getSelectedItem().firstNameProperty().set(newValue);
    // });
    // textSurname.textProperty().addListener((observable, oldValue, newValue) -> {
    //   personList.getSelectionModel().getSelectedItem().surnameProperty().set(newValue);
    // });
  }

  // public function
  // returns nothing
  public void handleChange() {
  }
}
