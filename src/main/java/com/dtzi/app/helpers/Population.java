package com.dtzi.app.helpers;

import com.dtzi.app.classes.Member.Email.EmailVerificationException;
import com.dtzi.app.classes.Member.Email;
import com.dtzi.app.classes.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

public interface Population {
  List<String> popularNames = new ArrayList<String>(Arrays.asList("John", "Peter", "Arnold", "June", "July", "August"));
  List<String> popularSurnames = new ArrayList<String>(Arrays.asList("Johnson", "Peterson", "Arnoldson", "Junedaughter", "Julydaughter", "Augustdaughter"));

  public static void populateMembers(int limit, Connection conn) throws SQLException {
    Random rnd = new Random();
    for (int i = 0; i < limit; i++) {
      String randomName = popularNames.get(rnd.nextInt(6));
      String randomSurname = popularSurnames.get(rnd.nextInt(6));
      StringBuilder sb = new StringBuilder();
      for (int n = 0; n < 10; n++) {
        sb.append(rnd.nextInt(10));
      }
      String phoneNumber = sb.toString();
      String email;
      try {
        email = new Email(randomName.toLowerCase() + "." + randomSurname.toLowerCase() + "@mymail.xyz").get().get();
      } catch (EmailVerificationException e) {
        email = null;
      }
      Member newMem = new Member(randomName, randomSurname, phoneNumber, email);
      String sql = "INSERT INTO members(name,surname,id,phone_no,email) VALUES (?,?,?::uuid,?,?)";
      PreparedStatement prep = conn.prepareStatement(sql); 
      prep.setString(1, randomName);
      prep.setString(2, randomSurname);
      prep.setString(3, newMem.IDProperty().get());
      prep.setString(4, phoneNumber);
      prep.setString(5, email);
      prep.execute();
    }
  } 
}
