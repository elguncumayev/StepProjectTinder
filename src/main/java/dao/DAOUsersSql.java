package dao;

import entity.User;
import lombok.SneakyThrows;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class DAOUsersSql implements DAO<User> {
  private final String defaultPhoto = "https://www.logolynx.com/images/logolynx/s_cb/cbd29542455b9e0cc175289ff24cecaa.jpeg";
  private final static String URL = System.getenv("dburi");
  private final static String UNAME = System.getenv("user");
  private final static String PWD = System.getenv("password");
  private final String SELECTALL = "select * from users u";

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

  @SneakyThrows
  @Override
  public int getID(String email) {
    Connection conn = DriverManager.getConnection(URL, UNAME, PWD);
    PreparedStatement stmt =
            conn.prepareStatement(String.format("select u.id from users u where u.\"e-mail\" = '%s';", email));
    ResultSet rSet = stmt.executeQuery();
    rSet.next();
    conn.close();
    return rSet.getInt("id");
  }

  @Override
  public List<User> getList(String choice) {
    try {
      Connection conn = DriverManager.getConnection(URL, UNAME, PWD);
      return getUsers(conn, String.format("%s %s;",SELECTALL,choice));
    } catch (SQLException e) {
      return new ArrayList<>();
    }
  }

  @Override
  public Optional<User> get(int id) {
    List<User> list = getList(String.format("where u.id = %s;", id));
    if(list.isEmpty()) return Optional.empty();
    return Optional.ofNullable(list.get(0));
  }

  @Override
  public boolean check(String condition) {
    try {
      Connection conn = DriverManager.getConnection(URL, UNAME, PWD);
      PreparedStatement stmt = conn.prepareStatement(String.format("%s %s;",SELECTALL,condition));
      ResultSet rSet = stmt.executeQuery();
      conn.close();
      return rSet.next();
    } catch (SQLException e) {
      return false;
    }
  }


  @Override
  public boolean executeSQL(String config) {
    try {
      Connection conn = DriverManager.getConnection(URL, UNAME, PWD);
      PreparedStatement stmt = conn.prepareStatement(config);
      stmt.execute();
      conn.close();
      return true;
    } catch (SQLException e) {
      return false;
    }
  }

  private List<User> getUsers(Connection conn, String SQL) throws SQLException {
    PreparedStatement stmt = conn.prepareStatement(SQL);
    ResultSet rSet = stmt.executeQuery();
    ArrayList<User> users = new ArrayList<>();
    while (rSet.next()) {
      int ID = rSet.getInt("id");
      String email = rSet.getString("e-mail");
      String username = rSet.getString("username");
      String fullName = rSet.getString("fullName");
      String lastLogin = rSet.getString("lastLogin");
      Optional<String> workInfo = Optional.ofNullable(rSet.getString("workInfo"));
      Optional<String> prof_photo = Optional.ofNullable(rSet.getString("prof_photo"));
      users.add(new User(
              ID,
              username,
              email,
              fullName,
              LocalDateTime.parse(lastLogin),
              workInfo.orElse(" "),
              prof_photo.orElse(defaultPhoto)
      ));
    }
    conn.close();
    return users;
  }
}
