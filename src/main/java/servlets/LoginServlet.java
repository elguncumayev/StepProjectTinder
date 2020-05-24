package servlets;

import services.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class LoginServlet extends HttpServlet {
  private final HashMap<String, Object> data = new HashMap<>();
  private final UserService userService = new UserService();
  private final TemplateEngine engine;

  public LoginServlet(TemplateEngine engine) {
    this.engine = engine;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    throw new RuntimeException("Not implemented");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    throw new RuntimeException("Not implemented");
  }
}
