package com.dtzi.app;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.dtzi.app.classes.Member.Email;
import com.dtzi.app.classes.Member.Email.EmailVerificationException;

import javafx.beans.property.SimpleStringProperty;

public class EmailTest {

  @Test
  public void testVerification() {

    assertThrowsExactly(EmailVerificationException.class, () -> {new Email("1@mymail.xyz");});
    assertDoesNotThrow(() -> {new Email("thisisabeauty1@mymail.xyz");});
    assertThrowsExactly(EmailVerificationException.class, () -> {new Email("thisisabeauty1@gmail.xyz");});
    assertThrowsExactly(EmailVerificationException.class, () -> {new Email("thisisabeauty1@mymail.lt");});
    assertThrowsExactly(EmailVerificationException.class, () -> {new Email("thisisabeauty1[at]mymail.xyz");});
  }

  @Test
  public void testGetReturnType() throws EmailVerificationException {
    assertTrue(new Email("thisisabeauty1@mymail.xyz").get() instanceof SimpleStringProperty);
  }
}
