package com.dtzi.app.classes;

import com.dtzi.app.classes.Member.Email.EmailVerificationException;

import java.util.UUID;
import java.util.regex.Pattern;

import javafx.beans.property.SimpleStringProperty;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class Member implements DBObject {

  Email email;
  SimpleStringProperty name, surname, ID, phoneNo;

  // Dummy initializer for object dependent scenes
  public Member (){
  }

  // Initializer with instant injection into sql (creating members)
  public Member (String Name, String Surname, String ID,
      String phoneNo, String Email, Connection conn) throws EmailVerificationException, SQLException {
    this(Name, Surname, ID, phoneNo, Email);
    createSQLRow(conn); 
  }

  // Constructor for members received from SQL DB (receiving members)
  public Member (String Name, String Surname, 
      String ID, String phoneNo, String Email) throws EmailVerificationException {
    this.email = new Email(Email);
    this.name = new SimpleStringProperty(Name);
    this.surname = new SimpleStringProperty(Surname);
    this.ID = new SimpleStringProperty(ID);
    this.phoneNo = new SimpleStringProperty(phoneNo);

  }

  // DO NOT DELETE! used by Population.java
  public Member (String Name, String Surname,
      String phoneNo, String Email) throws EmailVerificationException {
    this(Name, Surname, UUID.randomUUID().toString(), phoneNo, Email);
  }

  //public function
  //returns String Name
  public SimpleStringProperty nameProperty () {
    return this.name; 
  }
  //public function
  //returns String Surname
  public SimpleStringProperty surnameProperty () {
    return this.surname; 
  }
  //public function
  //returns int ID
  public SimpleStringProperty IDProperty () {
    return this.ID; 
  }
  //public function
  //returns int phoneNo
  public SimpleStringProperty phoneNoProperty () {
    return this.phoneNo; 
  }
  //public function
  //returns String Email
  public SimpleStringProperty emailProperty () {
    return this.email.get(); 
  }
  public String getName() {
    return this.name.get();
  }
  public String getSurname() {
    return this.surname.get();
  }
  public String getPhoneNo() {
    return this.phoneNo.get();
  }
  public String getID() {
    return this.ID.get();
  }
  public String getEmail() {
    return this.email.get().get();
  }
  //public function
  //returns nothing
  public void setName (String newName) {
    this.name.set(newName); 
  }
  //public function
  //returns nothing
  public void setSurname (String newSurname) {
    this.surname.set(newSurname); 
  }
  //public function
  //returns nothing
  public void setID (String newID) {
    this.ID.set(newID); 
  }
  //public function
  //returns nothing
  public void setPhoneNo (String newphoneNo) {
    this.phoneNo.set(newphoneNo); 
  }
  //public function
  //returns nothing
  public void setEmail (String newEmail) throws EmailVerificationException {
    this.email.setEmail(newEmail);          
  }

  public void createSQLRow(Connection conn) throws SQLException {
    PreparedStatement prep = conn.prepareStatement("INSERT INTO members(name, surname, id, phone_no, email)" +
        " VALUES (?, ?, ?::uuid, ?, ?)");
    prep.setString(1, this.getName());
    prep.setString(2, this.getSurname());
    prep.setString(3, this.ID.get());
    prep.setString(4, this.getPhoneNo());
    prep.setString(5, this.getEmail());
    prep.executeUpdate();
    System.out.print(prep.toString());
  }

  public void update(String newName, String newSurname, String newPhoneNo, String newEmail, Connection conn) throws SQLException, EmailVerificationException {
      PreparedStatement prep = conn.prepareStatement("""
          UPDATE members SET name = ?, surname = ?, 
          phone_no = ?, email = ? WHERE id = ?::uuid""");
      prep.setString(1, newName);
      prep.setString(2, newSurname);
      prep.setString(3, newPhoneNo);
      prep.setString(4, newEmail);
      prep.setString(5, this.getID());
      prep.executeUpdate();
      this.setName(newName);
      this.setSurname(newSurname);
      this.setPhoneNo(newPhoneNo);
      this.setEmail(newEmail);
  }

  public static class Email {

    private SimpleStringProperty email;
    private static String verification = "[\\w\\.]{10,}@mymail.[a-z]{3}";

    public Email(String input) throws EmailVerificationException {
      // Email validation; 10+ symbols before @mymail.xyz;
      boolean result = Pattern.matches(verification, input);
      if (result == false)
        throw new EmailVerificationException("Email " + input + " could not be verified. Enter a valid email");
      else
        this.email = new SimpleStringProperty(input);
    }

    // public function
    // @param Email email
    // returns String email
    public SimpleStringProperty get() {
      SimpleStringProperty stringifiedEmail = this.email;
      return stringifiedEmail;
    }

    public void setEmail(String input) throws EmailVerificationException {
      boolean result = Pattern.matches(verification, input);
      if (!result)
        throw new EmailVerificationException("Email " + input + " could not be verified. Enter a valid email");
      else
        this.email = new SimpleStringProperty(input);
    }

    public class EmailVerificationException extends Exception {
      EmailVerificationException(String errorMessage) {
        super(errorMessage);
      }
    }
  }
}
