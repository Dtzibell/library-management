package com.dtzi.app.classes;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class Member {

  public static class Email {

    private String email;

    public Email (String input){
      // TODO: add email validation;
      // 10+ symbols before @mymail.xyz;
      boolean result = Pattern.matches("[\\w\\.]{10,}@mymail.[a-z]{3}", input);
      System.out.println(result + input);
    }
  }

  int userID, userPhoneNumber;
  Email userEmail;
  String userName, userSurname;
}
