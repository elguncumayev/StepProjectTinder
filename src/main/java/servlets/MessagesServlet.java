package servlets;

import entity.Message;
import entity.User;
import services.EncodeDecode;
import services.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class MessagesServlet extends HttpServlet {
  private final UserService userService = new UserService();
  private final TemplateEngine engine;
  private final EncodeDecode eD = new EncodeDecode();

  public MessagesServlet(TemplateEngine engine) {
    this.engine = engine;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Cookie sign = Arrays.stream(req.getCookies())
            .filter(cookie -> cookie.getName().equals("sign"))
            .findFirst()
            .get();
    String[] split = req.getRequestURI().split("/");
    String idS = split[1];
    if (idS == null ) {
      resp.sendRedirect("/liked");
      return;
    }
    if(!userService.containsRel(Integer.parseInt(eD.decrypt(sign.getValue())),Integer.parseInt(split[2]))){
      try (PrintWriter w = resp.getWriter()) {
        w.write("You can send message when both of you like each other!");
        return;
      }
    }
    int id = Integer.parseInt(idS);
    HashMap<String, Object> data = new HashMap<>();
    Optional<User> optionalUser = userService.getUser(id);
    if (!optionalUser.isPresent()) {
      resp.sendError(404);
      return;
    }
    User partner = optionalUser.get();
    List<Message> messages = userService.getMessages(Integer.parseInt(sign.getValue()), id);
    StringBuilder sb = new StringBuilder();
    for (Message m : messages) {
      if(m.getFrom() == id){
        sb.append("<li class=\"receive-msg float-left mb-2\">")
                .append(String.format("<div class=\"sender-img\"><img src=\"%s\" class=\"float-left\"></div>",
                        partner.getPhoto()))
                .append("<div class=\"receive-msg-desc float-left ml-2\">")
                .append(String.format("<p class=\"bg-white m-0 pt-1 pb-1 pl-2 pr-2 rounded\">%s<br></p>",
                        m.getText()))
                .append(String.format("<span class=\"receive-msg-time\">%s %s, %s</span>",
                        m.getSentTime().getMonth(),
                        m.getSentTime().getDayOfMonth(),
                        m.getSentTime().toLocalTime()))
                .append("</div> </li>");
      }
      else {
        sb.append("<li class=\"send-msg float-right mb-2\">")
                .append("<p class=\"pt-1 pb-1 pl-2 pr-2 m-0 rounded\">")
                .append(String.format("%s</p>",m.getText()))
                .append(String.format("<span class=\"send-msg-time\">%s %s %s</span></li>",
                        m.getSentTime().getMonth(),
                        m.getSentTime().getDayOfMonth(),
                        m.getSentTime().toLocalTime()));
      }
    }

    data.put("partnerName", partner.getFullName());
    data.put("partnerPhoto", partner.getPhoto());
    data.put("messages", sb.toString());
    engine.render("chat.ftl", data, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Cookie sign = Arrays.stream(req.getCookies())
            .filter(cookie -> cookie.getName().equals("sign"))
            .findFirst()
            .get();
    String[] split = req.getRequestURI().split("/");
    int id = Integer.parseInt(split[2]);
    String text = req.getParameter("message");
    if(text == null || text.matches("[\\s]") || text.isEmpty()) {
      resp.sendRedirect(String.format("/messages/%s",id));
      return;
    }
    userService.sendMessage(Integer.parseInt(eD.decrypt(sign.getValue())),id,text);
    resp.sendRedirect(String.format("/messages/%s",id));
  }
}