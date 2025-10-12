package com.dtzi.app.classes;

import java.lang.IO;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.dtzi.app.classes.Member.Email.EmailVerificationException;

import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;


public class MemberTest {

  final String memID = UUID.fromString("52778853-2a07-4e46-8c04-8007a97ce3aa").toString();

  @Test
  public void testInsertion() throws SQLException, EmailVerificationException {
    Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb");
    Statement statement = conn.createStatement();
    statement.execute("CREATE TABLE members(name varchar, surname varchar, id uuid, phone_no varchar, email varchar)");
    Member mem = new Member("1", "2", memID, "0123456789", "whatawonderfulworld@mymail.xyz");
    mem.createSQLRow(conn);

    ResultSet mems = statement.executeQuery("SELECT * FROM members");
    assertTrue(mems.next());
    assertEquals("1", mems.getString("name"));
    assertEquals("2", mems.getString("surname"));
    assertEquals("0123456789", mems.getString("phone_no"));
    assertEquals("whatawonderfulworld@mymail.xyz", mems.getString("email"));
  }

  @Test
  public void testUpdate() throws SQLException, EmailVerificationException {

    Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb");
    Statement statement = conn.createStatement();
    Member mem = new Member("1", "2", memID, "0123456789", "whatawonderfulworld@mymail.xyz");
    mem.update("2", "1", "9876543210","whatahorribleworld@mymail.xyz" , conn);

    ResultSet mems = statement.executeQuery("SELECT * FROM members");
    assertTrue(mems.next());
    assertEquals("2", mems.getString("name"));
    assertEquals("1", mems.getString("surname"));
    assertEquals("9876543210", mems.getString("phone_no"));
    assertEquals("whatahorribleworld@mymail.xyz", mems.getString("email"));
  }
}
