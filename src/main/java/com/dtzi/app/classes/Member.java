package com.dtzi.app.classes;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

import com.dtzi.app.classes.Member.Email.EmailVerificationException;

public class Member {

  public static class Email {
    
    public class EmailVerificationException extends Exception {
      EmailVerificationException(String errorMessage) {
        super(errorMessage);
      }
    }

    private String email;
    private String verification = "[\\w\\.]{10,}@mymail.[a-z]{3}";

    public Email (String input) throws EmailVerificationException{
      // Email validation; 10+ symbols before @mymail.xyz;
      boolean result = Pattern.matches(verification, input);
      this.email = input;
      if (result == false) {
        throw new EmailVerificationException("email could not be verified. Enter a valid email");
      } 
    }

    //public function
    //@param Email email
    //returns String email
    public String of() {
      String stringifiedEmail = this.email; 
      return stringifiedEmail; 
    }

    public void setEmail (String input) throws EmailVerificationException {
      boolean result = Pattern.matches(verification, input);
      if (!result)
        throw new EmailVerificationException("Email could not be verified. Enter a valid email");
      else
        this.email = input;
    }
  }

  Email userEmail;
  String userName, userSurname, userID, userPhoneNumber;

  public Member (String userName, String userSurname, 
      String userID, String userPhoneNumber, String userEmail) {
    try {
      this.userEmail = new Email(userEmail);
      System.out.println(this.userEmail.email);
    } catch (EmailVerificationException e) {
      this.userEmail = null;
    }
    this.userName = userName;
    this.userSurname = userSurname;
    this.userID = userID;
    this.userPhoneNumber = userPhoneNumber;
    try {
      Statement statement = conn.createStatement();
    } catch (SQLException e) {
      System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
    }
    StringBuilder sb = new StringBuilder("INSERT INTO members (name, surname, id, phone_no, email) VALUES (");
    sb.append(this.userName + ", ");
    sb.append(this.userSurname + ", ");
    sb.append(this.userID + ", ");
    sb.append(this.userPhoneNumber + ", ");
    sb.append(this.userEmail.email + ")");
    String sqlQuery = sb.toString();
    System.out.print(sb);

  }

  //public function
  //returns int userID
  public String getUserID () {
    return userID; 
  }
  //public function
  //returns int userPhoneNumber
  public String getPhoneNumber () {
    return userPhoneNumber; 
  }
  //public function
  //returns String userEmail
  public String getEmail () {
    return userEmail.of(); 
  }
  //public function
  //returns String userName
  public String getName () {
    return userName; 
  }
  //public function
  //returns String userSurname
  public String getSurname () {
    return userSurname; 
  }
  //public function
  //returns nothing
  public void setUserID (String newID) {
    this.userID =  newID; 
  }
  //public function
  //returns nothing
  public void setPhoneNumber (String newPhoneNumber) {
    this.userPhoneNumber = newPhoneNumber; 
  }
  //public function
  //returns nothing
  public void setEmail (String newEmail) throws EmailVerificationException {
    this.userEmail.setEmail(newEmail);          
  }
  //public function
  //returns nothing
  public void setName (String newUserName) {
    this.userName = newUserName; 
  }
  //public function
  //returns nothing
  public void setSurname (String newUserSurname) {
    this.userSurname = newUserSurname; 
  }
}
