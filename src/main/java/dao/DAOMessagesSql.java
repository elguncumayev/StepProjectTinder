package dao;

import entity.Message;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class DAOMessagesSql implements DAO<Message> {
  private final static String URL = "jdbc:postgresql://localhost:5432/postgres";
  private final static String UNAME = "postgres";
  private final static String PWD = "cumayev_99";

  @Override
  public Collection<Message> getBySQLQuery(String query) {
    throw new RuntimeException("Not implemented");
  }

  @Override
  public int getID(String query) {
    throw new RuntimeException("Not implemented");
  }

  @Override
  public boolean check(String query) {
    throw new RuntimeException("Not implemented");
  }

  @Override
  public Optional<Message> get(int id) {
    throw new RuntimeException("Not implemented");
  }

  @Override
  public boolean executeSQL(String sb) {
    throw new RuntimeException("Not implemented");
  }
}
