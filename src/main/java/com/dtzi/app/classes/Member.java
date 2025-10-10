package com.dtzi.app.classes;

import com.dtzi.app.classes.Member.Email.EmailVerificationException;

import java.util.UUID;
import java.util.regex.Pattern;

import javafx.beans.property.SimpleStringProperty;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class Member {

  public static class Email {
    
    public class EmailVerificationException extends Exception {
      EmailVerificationException(String errorMessage) {
        super(errorMessage);
      }
    }

    private SimpleStringProperty email;
    private static String verification = "[\\w\\.]{10,}@mymail.[a-z]{3}";

    public Email (String input) throws EmailVerificationException{
      // Email validation; 10+ symbols before @mymail.xyz;
      boolean result = Pattern.matches(verification, input);
      this.email = new SimpleStringProperty(input);
      if (result == false) {
        throw new EmailVerificationException("email " + input + " could not be verified. Enter a valid email");
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

  // Dummy initializer for object dependent scenes
  public Member (){
  }

  // Initializer with instant injection into sql
  public Member (String userName, String userSurname, String userID,
      String userPhoneNumber, String userEmail, Connection conn) {
    try {
      this.userEmail = new Email(userEmail);
    } catch (EmailVerificationException e) {
      System.out.println(e.getMessage());
      this.userEmail = null;
    }
    this.userName = new SimpleStringProperty(userName);
    this.userSurname = new SimpleStringProperty(userSurname);
    this.userID = new SimpleStringProperty(userID);
    this.userPhoneNumber = new SimpleStringProperty(userPhoneNumber );
    try {
      PreparedStatement prep = conn.prepareStatement("INSERT INTO members(name, surname, id, phone_no, email)"+
          " VALUES (?, ?, ?::uuid, ?, ?)");
      prep.setString(1,userName);
      prep.setString(2,userSurname);
      prep.setString(3,this.userID.get());
      prep.setString(4,userPhoneNumber);
      prep.setString(5,userEmail);
      System.out.print(prep.toString());
    } catch (SQLException e) {
      System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
    }
  }

  // Initializer without sql injection
  public Member (String userName, String userSurname, 
      String userID, String userPhoneNumber, String userEmail) {
    try {
      this.userEmail = new Email(userEmail);
    } catch (EmailVerificationException e) {
      System.out.println(e.getMessage());
      this.userEmail = null;
    }
    this.userName = new SimpleStringProperty(userName);
    this.userSurname = new SimpleStringProperty(userSurname);
    this.userID = new SimpleStringProperty(userID);
    this.userPhoneNumber = new SimpleStringProperty(userPhoneNumber);
  }
  public Member (String userName, String userSurname,
      String userPhoneNumber, String userEmail) {
    try {
      this.userEmail = new Email(userEmail);
    } catch (EmailVerificationException e) {
      System.out.println(e.getMessage());
      this.userEmail = null;
    }
    this.userName = new SimpleStringProperty(userName);
    this.userSurname = new SimpleStringProperty(userSurname);
    this.userID = new SimpleStringProperty(UUID.randomUUID().toString());
    this.userPhoneNumber = new SimpleStringProperty(userPhoneNumber);
  }

  // public static Callback<Member, Observable[]> extractor = p -> new Observable[] {p.surnameProperty(), p.firstNameProperty()};
  //public function
  //returns int userID
  public SimpleStringProperty IDProperty () {
    return userID; 
  }
  //public function
  //returns int userPhoneNumber
  public SimpleStringProperty phoneNumberProperty () {
    return userPhoneNumber; 
  }
  //public function
  //returns String userEmail
  public SimpleStringProperty emailProperty () {
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
    this.userID.set(newID); 
  }
  //public function
  //returns nothing
  public void setPhoneNumber (String newPhoneNumber) {
    this.userPhoneNumber.set(newPhoneNumber); 
  }
  //public function
  //returns nothing
  public void setEmail (String newEmail) throws EmailVerificationException {
    this.userEmail.setEmail(newEmail);          
  }
  //public function
  //returns nothing
  public void setName (String newUserName) {
    this.userName.set(newUserName); 
  }
  //public function
  //returns nothing
  public void setSurname (String newUserSurname) {
    this.userSurname.set(newUserSurname); 
  }
}
