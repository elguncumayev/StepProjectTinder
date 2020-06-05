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

  public List<User> likedPeople(int ID) {
    String choice =
            String.format("inner join relations r on r.\"to\" = u.id where r.\"from\"= %s AND r.rel = true;", ID);
    return userDAO.getList(choice);
  }

  public Optional<User> getUser(int ID) {
    return userDAO.get(ID);
  }

  public int getUserID(String email) {
    return userDAO.getID(email);
  }

  public Optional<User> randomUser(int id) {
    StringBuilder sb = new StringBuilder();
    sb.append("left join relations r on u.id = r.\"from\" ")
            .append(String.format("where u.id != %s AND ", id))
            .append("u.id NOT IN ")
            .append(String.format("(select r.\"to\" from relations r where r.\"from\"= %s);", id));
    Random random = new Random();
    List<User> noActionUsers = userDAO.getList(sb.toString());
    return Optional.ofNullable(noActionUsers.get(random.nextInt(noActionUsers.size())));
  }

  public boolean containsMail(String email) {
    String condition = String.format("where u.\"e-mail\" = '%s';", email);
    return userDAO.check(condition);
  }

  public boolean checkPass(String email, String password) {
    String condition = String.format("where \"e-mail\" = '%s' AND pass = '%s';", email, password);
    return userDAO.check(condition);
  }

  public boolean containsRel(int from, int to) {
    String condition = String.format("select * from relations where \"from\" = %s AND \"to\" =%s;",to,from);
    return userDAO.executeSQL(condition);
  }

  public void setLoginTime(int id) {
    LocalDateTime now = LocalDateTime.now();
    String config = String.format("UPDATE public.users SET \"lastLogin\" = '%s' WHERE id = %s", now, id);
    userDAO.executeSQL(config);
  }

  public boolean relationInteraction(int currUserId, int choosesUserId, boolean b) {
    String config = String.format("INSERT INTO public.relations (\"from\", \"to\", rel) VALUES (%s, %s, %s);",
            currUserId,
            choosesUserId,
            b ? "true" : "false");
    return userDAO.executeSQL(config);
  }

  public void sendMessage(int from, int to, String text) {
    StringBuilder sb =new StringBuilder();
    sb.append("INSERT INTO public.messages (text, \"from\", \"to\", time)")
            .append(String.format("VALUES ('%s', %s, %s, '%s')",text,from,to,LocalDateTime.now()));
    userDAO.executeSQL(sb.toString());
  }

  public List<Message> getMessages(int from, int to) {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("where (m.\"from\" = %s and m.\"to\" = %s) or (m.\"from\" = %s and m.\"to\" = %s)",
                    from,
                    to,
                    to,
                    from))
            .append("order by time DESC limit 10;");
    List<Message> bySQLQuery = messageDAO.getList(sb.toString());
    bySQLQuery.sort(Comparator.comparing(Message::getSentTime));
    return bySQLQuery;
  }
}
