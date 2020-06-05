package servlets;

import entity.User;
import services.EncodeDecode;
import services.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LikedServlet extends HttpServlet {
  private final TemplateEngine engine;
  private final UserService userService = new UserService();
  private final EncodeDecode eD = new EncodeDecode();

  public LikedServlet(TemplateEngine engine) {
    this.engine = engine;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp){
    Cookie sign = Arrays.stream(req.getCookies())
            .filter(cookie -> cookie.getName().equals("sign"))
            .findFirst()
            .get();
    HashMap<String, Object> data = new HashMap<>();
    List<User> users = userService.likedPeople(Integer.parseInt(eD.decrypt(sign.getValue())));
    data.put("users",users);
    engine.render("people-list.ftl", data, resp);
  }
}