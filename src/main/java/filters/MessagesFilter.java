package filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MessagesFilter implements Filter {

  private boolean isPathOk(HttpServletRequest request) {
    String[] split = request.getRequestURI().split("/");
    return split.length >= 3 && split[2].matches("[0-9]+");
  }

  private boolean isHttp(ServletRequest req) {
    return req instanceof HttpServletRequest;
  }

  @Override
  public void init(FilterConfig filterConfig) {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    if (isHttp(request) && isPathOk((HttpServletRequest) request)) {
      chain.doFilter(request, response);
    } else ((HttpServletResponse) response).sendRedirect("/liked");
  }

  @Override
  public void destroy() {

  }
}
