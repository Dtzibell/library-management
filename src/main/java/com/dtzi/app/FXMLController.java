package com.dtzi.app;

import com.dtzi.app.classes.Member;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import java.net.URL;
import java.nio.file.DirectoryStream.Filter;
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
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FXMLController implements Initializable{

  ObservableList<Member> listOfMembers = FXCollections.observableArrayList(new Member("john", "johnson", "1", "0123456789", "john.johnson@mymail.xyz"), new Member("joh", "johnson", "1", "0123456789", "john.johnson@mymail.xyz")); 

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

    personList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->  {
      if (observable != null){
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
  }

  @FXML
  private void filter () throws Exception {
    Stage stage = new Stage();
    filterScene.start(stage);
  }

}
