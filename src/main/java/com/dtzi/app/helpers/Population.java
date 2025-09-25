package com.dtzi.app.helpers;

import com.dtzi.app.pgutils.PostgreSQL;
import com.dtzi.app.classes.Member.Email.EmailVerificationException;
import com.dtzi.app.classes.Member.Email;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public interface Population {
  List<String> popularNames = new ArrayList<String>(Arrays.asList("John", "Peter", "Arnold", "June", "July", "August"));
  List<String> popularSurnames = new ArrayList<String>(Arrays.asList("Johnson", "Peterson", "Arnoldson", "Junedaughter", "Julydaughter", "Augustdaughter"));

  private static int generateRandom(int upperBound) {
    Double intermediateRandom = Math.random()*upperBound;
    int output = intermediateRandom.intValue();
    return output;
  }

  public static void populateMembers(int number) throws SQLException {
    Connection connection = PostgreSQL.connect();
    for (int i = 0; i < 20; i++) {
      String randomName = popularNames.get(generateRandom(5));
      String randomSurname = popularSurnames.get(generateRandom(5));
      String ID = UUID.randomUUID().toString();
      StringBuilder sb = new StringBuilder();
      for (int n = 0; n < 10; n++) {
        sb.append(generateRandom(9));
      }
      String phoneNumber = sb.toString();
      String email;
      try {
        email = new Email(randomName + "." + randomSurname + "@mymail.xyz").of();
      } catch (EmailVerificationException e) {
        email = null;
      }
      System.out.format("%s %s \n %s \n %s \n %s \n --------------------- \n", randomName, randomSurname, phoneNumber, ID, email);
      String sql = "INSERT INTO members(name,surname,id,phone_no,email) VALUES (?,?,?,?,?)";
      PreparedStatement prep = connection.prepareStatement(sql); 
      prep.setString(1, randomName);
      prep.setString(2, randomSurname);
      prep.setString(3, ID);
      prep.setString(4, phoneNumber);
      prep.setString(5, email);
      prep.execute();
    }
  } 
}
