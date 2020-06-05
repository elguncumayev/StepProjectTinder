package servlets;

import entity.User;
import services.EncodeDecode;
import services.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

public class UsersServlet extends HttpServlet {

  private final TemplateEngine engine;
  private final UserService userService = new UserService();
  private final EncodeDecode eD = new EncodeDecode();

  public UsersServlet(TemplateEngine engine) {
    this.engine = engine;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Cookie sign = Arrays.stream(req.getCookies())
            .filter(cookie -> cookie.getName().equals("sign"))
            .findFirst().get();
    try {
      String[] split = req.getPathInfo().split("/");
      User user;
      if (split.length >= 2) {
        Optional<User> optionalUser = userService.getUser(Integer.parseInt(split[1]));
        if (!optionalUser.isPresent()) {
          resp.sendError(404);
          return;
        } else {
          user = optionalUser.get();
        }
      } else {
        Optional<User> randomUser = userService.randomUser(Integer.parseInt(eD.decrypt(sign.getValue())));
        if (!randomUser.isPresent()) {
          try (PrintWriter w = resp.getWriter()) {
            w.write("There is not any user in system.");
            return;
          }
        }
        user = randomUser.get();
        resp.sendRedirect(String.format("/users/%s",user.getId()));
        return;
      }
      HashMap<String, Object> data = new HashMap<>();
      String name = user.getFullName();
      String image = user.getPhoto();
      data.put("fullname", name);
      data.put("image", image);
      engine.render("like-page.ftl", data, resp);
    } catch (NumberFormatException ex) {
      resp.sendError(404);
    }
    catch (Exception e){
      resp.sendRedirect("/liked");
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Cookie sign = Arrays.stream(req.getCookies())
            .filter(cookie -> cookie.getName().equals("sign"))
            .findFirst()
            .get();
    String like = req.getParameter("like");
    String[] split = req.getPathInfo().split("/");
    if (!userService.relationInteraction(Integer.parseInt(eD.decrypt(sign.getValue())), Integer.parseInt(split[1]), like != null)) {
      resp.sendRedirect("/liked");
    } else resp.sendRedirect("/users");
  }
}