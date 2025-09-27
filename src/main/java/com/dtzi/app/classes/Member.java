package com.dtzi.app.classes;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

import com.dtzi.app.classes.Member.Email.EmailVerificationException;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Callback;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableStringValue;


public class Member {

  public static class Email {
    
    public class EmailVerificationException extends Exception {
      EmailVerificationException(String errorMessage) {
        super(errorMessage);
      }
    }

    private SimpleStringProperty email;
    private String verification = "[\\w\\.]{10,}@mymail.[a-z]{3}";

    public Email (String input) throws EmailVerificationException{
      // Email validation; 10+ symbols before @mymail.xyz;
      boolean result = Pattern.matches(verification, input);
      this.email = new SimpleStringProperty(input);
      if (result == false) {
        throw new EmailVerificationException("email could not be verified. Enter a valid email");
      } 
    }

    //public function
    //@param Email email
    //returns String email
    public SimpleStringProperty get() {
      SimpleStringProperty stringifiedEmail = this.email; 
      return stringifiedEmail; 
    }

    public void setEmail (String input) throws EmailVerificationException {
      boolean result = Pattern.matches(verification, input);
      if (!result)
        throw new EmailVerificationException("Email could not be verified. Enter a valid email");
      else
        this.email = new SimpleStringProperty(input);
    }
  }

  Email userEmail;
  SimpleStringProperty userName, userSurname, userID, userPhoneNumber;

  public Member (String userName, String userSurname, 
      String userID, String userPhoneNumber, String userEmail, Connection conn) {
    try {
      this.userEmail = new Email(userEmail);
      System.out.println(this.userEmail.email);
    } catch (EmailVerificationException e) {
      this.userEmail = null;
    }
    this.userName = new SimpleStringProperty(userName);
    this.userSurname = new SimpleStringProperty(userSurname);
    this.userID = new SimpleStringProperty(userID);
    this.userPhoneNumber = new SimpleStringProperty(userPhoneNumber );
    try {
      Statement statement = conn.createStatement();
    } catch (SQLException e) {
      System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
    }
    StringBuilder sb = new StringBuilder("INSERT INTO members (name, surname, id, phone_no, email) VALUES (");
    sb.append(this.userName.get() + ", ");
    sb.append(this.userSurname.get() + ", ");
    sb.append(this.userID.get() + ", ");
    sb.append(this.userPhoneNumber.get() + ", ");
    sb.append(this.userEmail.email.get() + ")");
    String sqlQuery = sb.toString();
    System.out.print(sb);

  }

  public Member (String userName, String userSurname, 
      String userID, String userPhoneNumber, String userEmail) {
    try {
      this.userEmail = new Email(userEmail);
      System.out.println(this.userEmail.email);
    } catch (EmailVerificationException e) {
      this.userEmail = null;
    }
    this.userName = new SimpleStringProperty(userName);
    this.userSurname = new SimpleStringProperty(userSurname);
    this.userID = new SimpleStringProperty(userID);
    this.userPhoneNumber = new SimpleStringProperty(userPhoneNumber );
  }

  public static Callback<Member, Observable[]> extractor = p -> new Observable[] {p.surnameProperty(), p.firstNameProperty()};
  //public function
  //returns int userID
  public SimpleStringProperty getUserID () {
    return userID; 
  }
  //public function
  //returns int userPhoneNumber
  public SimpleStringProperty getPhoneNumber () {
    return userPhoneNumber; 
  }
  //public function
  //returns String userEmail
  public SimpleStringProperty getEmail () {
    return userEmail.get(); 
  }
  //public function
  //returns String userName
  public SimpleStringProperty firstNameProperty () {
    return userName; 
  }
  //public function
  //returns String userSurname
  public SimpleStringProperty surnameProperty () {
    return userSurname; 
  }
  //public function
  //returns nothing
  public void setUserID (String newID) {
    this.userID = new SimpleStringProperty(newID); 
  }
  //public function
  //returns nothing
  public void setPhoneNumber (String newPhoneNumber) {
    this.userPhoneNumber = new SimpleStringProperty(newPhoneNumber); 
  }
  //public function
  //returns nothing
  public void setEmail (String newEmail) throws EmailVerificationException {
    this.userEmail.setEmail(newEmail);          
  }
  //public function
  //returns nothing
  public void setName (String newUserName) {
    this.userName = new SimpleStringProperty(newUserName); 
  }
  //public function
  //returns nothing
  public void setSurname (String newUserSurname) {
    this.userSurname = new SimpleStringProperty(newUserSurname); 
  }
}
