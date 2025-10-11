package com.dtzi.app.classes;

import java.sql.Connection;
import java.sql.SQLException;

public interface DBObject {
  public void createSQLRow(Connection conn) throws SQLException;
}
