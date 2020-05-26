package controller;

import dbmigration.DbSetup;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.*;

public class TinderApp {
  public void start() throws Exception {
    DbSetup.prepare(
            "jdbc:postgresql://ec2-54-175-117-212.compute-1.amazonaws.com:5432/d79jffpio67dv9",
            "ckvmkqdphiyopg",
            "6b84718fa2f3e1e6abc22554c3e8796d6d25c93584381a5e02b36bf79e9b42ee"
    );


    Server server = new Server(Integer.parseInt(System.getenv("PORT")));
    ServletContextHandler handler = new ServletContextHandler();

    TemplateEngine engine = TemplateEngine.folder("content");

    handler.addServlet(new ServletHolder(new StaticServlet("js")), "/js/*");
    handler.addServlet(new ServletHolder(new StaticServlet("css")), "/css/*");
    handler.addServlet(new ServletHolder(new UsersServlet(engine)), "/users");
    handler.addServlet(new ServletHolder(new LikedServlet(engine)), "/liked");
    handler.addServlet(new ServletHolder(new MessagesServlet(engine)), "/messages/*");
    handler.addServlet(new ServletHolder(new LoginServlet(engine)), "/login");
    handler.addServlet(new ServletHolder(new RedirectServlet("/login")), "/*");
    server.setHandler(handler);

    server.start();
    server.join();

  }
}
