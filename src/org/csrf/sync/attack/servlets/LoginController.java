package org.csrf.sync.attack.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.csrf.sync.attack.identifiers.Lambdas;
import org.csrf.sync.attack.persist.Database;

/**
 * Based upon making a login request, this will create a session ID along with a
 * CSRF token for each individual client
 * 
 * @author JNiroshan
 *
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet
{

  public static Map<String, String> csrfTokenStore = new HashMap<String, String>();
  private static final long serialVersionUID = 1L;

  /**
   * HTTP GET request will land in this method
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    response.getWriter().append("Served at: ").append(request.getContextPath());
  }

  /**
   * HTTP POST request will land in this method
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {

    String username = request.getParameter("username");
    String password = request.getParameter("password");
    HttpSession session = request.getSession(true); // create a new session if not exists

    if (Database.isValidUser(username, password))
    {
      String csrfToken = generateCSRFToken(session.getId());
      csrfTokenStore.put(session.getId(), csrfToken); // adding to token store
      response.addCookie(Lambdas.COOKIE_WITH_SESSION_ID.apply(session));

      session.removeAttribute("invalidCredentials");
      response.sendRedirect("./Home.jsp");
    }
    else
    {
      session.setAttribute("invalidCredentials", "Not_ok");
      response.sendRedirect("./Login.jsp");
    }
  }

  private String generateCSRFToken(String strClearText)
  {
    return strClearText + "." + getRandomString();
  }

  private String getRandomString()
  {
    UUID randomUuid = UUID.randomUUID();
    return randomUuid.toString();
  }
}
