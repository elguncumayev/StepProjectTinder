package dao;

import entity.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class DAOUsersSql implements DAO<User> {
  private final String defaultPhoto = "https://www.logolynx.com/images/logolynx/s_cb/cbd29542455b9e0cc175289ff24cecaa.jpeg";
  private final static String URL = "jdbc:postgresql://localhost:5432/postgres";
  private final static String UNAME = "postgres";
  private final static String PWD = "cumayev_99";

//  @Override
//  public Collection<User> getAll() {
//    try {
//      Connection conn = DriverManager.getConnection(URL, UNAME, PWD);
//      String SQL = "select * from users;";
//      return getUsers(conn, SQL);
//    } catch (SQLException e) {
//      return new ArrayList<>();
//    }
//  }

  @Override
  public boolean check(String query) {
    throw new RuntimeException("Not implemented");
  }

  @Override
  public Collection<User> getBySQLQuery(String query) {
    throw new RuntimeException("Not implemented");
  }

  @Override
  public int getID(String query) {
    throw new RuntimeException("Not implemented");
  }

  @Override
  public Optional<User> get(int id) {
    throw new RuntimeException("Not implemented");
  }

  @Override
  public boolean executeSQL(String query) {
    throw new RuntimeException("Not implemented");
  }

  private Collection<User> getUsers(Connection conn, String SQL) throws SQLException {
    throw new RuntimeException("Not implemented");
}
