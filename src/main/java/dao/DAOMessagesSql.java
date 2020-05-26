package dao;

import entity.Message;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class DAOMessagesSql implements DAO<Message> {
  private final static String URL = "jdbc:postgresql://ec2-54-175-117-212.compute-1.amazonaws.com:5432/d79jffpio67dv9";
  private final static String UNAME = "ckvmkqdphiyopg";
  private final static String PWD = "6b84718fa2f3e1e6abc22554c3e8796d6d25c93584381a5e02b36bf79e9b42ee";

  @Override
  public Collection<Message> getBySQLQuery(String query) {

    try {
      Connection conn = DriverManager.getConnection(URL, UNAME, PWD);
      PreparedStatement stmt = conn.prepareStatement(query);
      ResultSet rSet = stmt.executeQuery();
      ArrayList<Message> messages = new ArrayList<>();
      while (rSet.next()) {
        int ID = rSet.getInt("id");
        String text = rSet.getString("text");
        int from = rSet.getInt("from");
        int to = rSet.getInt("to");
        String time = rSet.getString("time");
        messages.add(new Message(
                ID,
                from,
                to,
                text,
                LocalDateTime.parse(time)
        ));
      }
      conn.close();
      return messages;
    } catch (SQLException e) {
      return new ArrayList<>();
    }
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
