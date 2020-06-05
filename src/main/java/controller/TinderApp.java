package controller;

import dbmigration.DbSetup;
import filters.CookieFilter;
import filters.MessagesFilter;
import filters.UsersFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.*;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class TinderApp {
  public void start() throws Exception {
    DbSetup.prepare(
            System.getenv("dburi"),
            System.getenv("user"),
            System.getenv("password")
    );


    Server server = new Server(Integer.parseInt(System.getenv("PORT")));
    ServletContextHandler handler = new ServletContextHandler();

    TemplateEngine engine = TemplateEngine.folder("content");

    handler.addServlet(new ServletHolder(new StaticServlet("js")), "/js/*");
    handler.addServlet(new ServletHolder(new StaticServlet("css")), "/css/*");
    handler.addServlet(new ServletHolder(new UsersServlet(engine)), "/users");
    handler.addServlet(new ServletHolder(new UsersServlet(engine)), "/users/*");
    handler.addServlet(new ServletHolder(new LikedServlet(engine)), "/liked");
    handler.addServlet(new ServletHolder(new MessagesServlet(engine)), "/messages/*");
    handler.addServlet(new ServletHolder(new LoginServlet(engine)), "/login");
    handler.addServlet(new ServletHolder(new LogoutServlet()), "/logout");
    handler.addServlet(new ServletHolder(new RedirectServlet("/login")), "/*");

    handler.addFilter(CookieFilter.class, "/messages", EnumSet.of(DispatcherType.REQUEST));
    handler.addFilter(CookieFilter.class, "/liked", EnumSet.of(DispatcherType.REQUEST));
    handler.addFilter(CookieFilter.class, "/users", EnumSet.of(DispatcherType.REQUEST));
    handler.addFilter(UsersFilter.class,"/users",EnumSet.of(DispatcherType.REQUEST));
    handler.addFilter(MessagesFilter.class,"/messages",EnumSet.of(DispatcherType.REQUEST));

    server.setHandler(handler);

    server.start();
    server.join();

  }
}
