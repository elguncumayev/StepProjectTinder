package controller;

import dbmigration.DbSetup;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.*;

public class TinderApp {
  public void start() throws Exception {
    DbSetup.prepare(
            System.getenv("dburi"),
            System.getenv("user"),
            System.getenv("password")
    );


    Server server = new Server(Integer.parseInt(System.getenv("8080")));
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
