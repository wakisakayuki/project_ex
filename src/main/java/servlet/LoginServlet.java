package servlet;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.dao.UserDAO;
import model.entity.UserBean;
import util.WebSupport;

/**
 * ユーザーIDとパスワードを検証してログインを処理するサーブレット。
 */
@WebServlet(urlPatterns = {
    "/login", "/login-servlet"
}) public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override protected void doGet(HttpServletRequest q, HttpServletResponse p) throws ServletException, IOException {
        p.setCharacterEncoding("UTF-8");
        if (WebSupport.userId(q) != null) {
            p.sendRedirect(WebSupport.path(q, "/menu"));
            return;
        }
        WebSupport.forward(q, p, "login.jsp");
    }
    @Override protected void doPost(HttpServletRequest q, HttpServletResponse p) throws ServletException, IOException {
        q.setCharacterEncoding("UTF-8");
        String id = q.getParameter("userId"), pw = q.getParameter("password");
        if (!WebSupport.required(id) || !WebSupport.required(pw)) {
            WebSupport.error(q, "E001");
            q.setAttribute("userId", id);
            WebSupport.forward(q, p, "login.jsp");
            return;
        }
        if (id.length()>24 || pw.length()>32) {
            WebSupport.error(q, "E002");
            q.setAttribute("userId", id);
            WebSupport.forward(q, p, "login.jsp");
            return;
        }
        try {
            UserBean user = new UserDAO().findByIdAndPassword(id.trim(), pw);
            if (user == null) {
                WebSupport.error(q, "E005");
                q.setAttribute("userId", id.trim());
                WebSupport.forward(q, p, "login.jsp");
                return;
            }
            HttpSession s = q.getSession(true);
            if (q.getSession(false) != null) q.changeSessionId();
            s.setMaxInactiveInterval(1800);
            s.setAttribute("user_id", user.getUserId());
            s.setAttribute("user_name", user.getUserName());
            p.sendRedirect(WebSupport.path(q, "/menu"));
        }
        catch (SQLException e) {
            WebSupport.log(getServletContext(), e);
            WebSupport.error(q, "E015");
            q.setAttribute("userId", id.trim());
            WebSupport.forward(q, p, "login.jsp");
        }
    }
}
