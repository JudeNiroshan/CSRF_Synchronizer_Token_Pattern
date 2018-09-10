package org.csrf.sync.attack.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Web service entry point to generate a CSRF token. It will be based on the
 * current session ID
 * 
 * @author JNiroshan
 *
 */
@WebServlet("/CSRFProvider")
public class CSRFProvider extends HttpServlet
{
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    HttpSession session = request.getSession(false);

    if (session != null)
    {
      response.setStatus(200);

      Map<String, String> returnMap = new HashMap<String, String>();
      returnMap.put("CSRF_key", LoginController.csrfTokenStore.get(session.getId()));
      String json = new Gson().toJson(returnMap);
      response.setContentType("application/json");
      response.getWriter().write(json);
    }
  }

}
