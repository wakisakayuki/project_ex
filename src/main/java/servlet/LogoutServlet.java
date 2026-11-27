package servlet;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import util.WebSupport;

/**
 * ログインセッションを終了するサーブレット。
 */
@WebServlet(urlPatterns = {
    "/logout", "/logout-servlet"
}) public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override protected void doGet(HttpServletRequest q, HttpServletResponse p) throws ServletException, IOException {
        q.setAttribute("message", WebSupport.message("C002"));
        WebSupport.forward(q, p, "logout.jsp");
    }
    @Override protected void doPost(HttpServletRequest q, HttpServletResponse p) throws ServletException, IOException {
        HttpSession s = q.getSession(false);
        if (s != null) s.invalidate();
        p.sendRedirect(WebSupport.path(q, "/login"));
    }
}
