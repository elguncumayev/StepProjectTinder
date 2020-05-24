package services;

import dao.DAO;
import dao.DAOMessagesSql;
import dao.DAOUsersSql;
import entity.Message;
import entity.User;

import java.time.LocalDateTime;
import java.util.*;

public class UserService {

  private final DAO<User> userDAO = new DAOUsersSql();
  private final DAO<Message> messageDAO = new DAOMessagesSql();

  public ArrayList<User> likedPeople(int userID) {
    throw new RuntimeException("Not implemented");
  }


  public User randomUser(int userID) {
    throw new RuntimeException("Not implemented");
  }

  public boolean containsMail(String email) {
    throw new RuntimeException("Not implemented");
  }

  public boolean checkPass(String email, String password) {
    throw new RuntimeException("Not implemented");
  }

  public int getUserID(String email) {
    throw new RuntimeException("Not implemented");
  }

  public boolean relationInteraction(int currUserId, int choosesUserId, boolean b) {
    throw new RuntimeException("Not implemented");
  }

  public boolean setLoginTime(int id) {
    throw new RuntimeException("Not implemented");
  }

  public User getUserName(String id) {
    throw new RuntimeException("Not implemented");
  }

  public ArrayList<Message> getMessages(int logUser, int partner) {
    throw new RuntimeException("Not implemented");
  }

  public boolean sendMessage(String logUser, String partner, String message) {
    throw new RuntimeException("Not implemented");
  }
}
