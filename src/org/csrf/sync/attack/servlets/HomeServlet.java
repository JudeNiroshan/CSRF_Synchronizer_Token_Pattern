package org.csrf.sync.attack.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Web servlet which authenticate for CSRF token before redirecting to the home
 * page
 * 
 * @author JNiroshan
 *
 */
@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet
{
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    response.getWriter().append("Served at: ").append(request.getContextPath());
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {

    HttpSession session = request.getSession(false);
    String hiddenToken = request.getParameter("myHiddenField");

    String csrfToken = LoginController.csrfTokenStore.get(session.getId());

    if (csrfToken.equals(hiddenToken))
    {
      response.getWriter().append("Success!");
    }
    else
    {
      response.getWriter().append("ERROR!");
    }
  }

}
