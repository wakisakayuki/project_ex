package servlet;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.dao.*;
import model.entity.*;
import util.WebSupport;

/**
 * タスクへのコメント投稿リクエストを処理するサーブレット。
 */
@WebServlet(urlPatterns = {
    "/comment-register-servlet", "/comment-register-result-servlet"
}) public class CommentRegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override protected void doGet(HttpServletRequest q, HttpServletResponse p) throws ServletException, IOException {
        p.sendRedirect(WebSupport.path(q, "/task-list"));
    }
    @Override protected void doPost(HttpServletRequest q, HttpServletResponse p) throws ServletException, IOException {
        q.setCharacterEncoding("UTF-8");
        if (q.getServletPath() .endsWith("comment-register-servlet")) {
            show(q, p);
            return;
        }
        register(q, p);
    }

    private void show(HttpServletRequest q, HttpServletResponse p) throws ServletException, IOException {
        int id = WebSupport.positiveInt(q.getParameter("task_id"));
        try {
            TaskBean t = new TaskDAO().findById(id);
            if (t == null) {
                WebSupport.error(q, "E012");
                WebSupport.forward(q, p, "comment-register.jsp");
                return;
            }
            q.setAttribute("task", t);
            if (q.getParameter("comment") != null) q.setAttribute("comment", q.getParameter("comment"));
            WebSupport.forward(q, p, "comment-register.jsp");
        }
        catch (SQLException e) {
            WebSupport.log(getServletContext(), e);
            WebSupport.error(q, "E013");
            WebSupport.forward(q, p, "comment-register.jsp");
        }
    }

    private void register(HttpServletRequest q, HttpServletResponse p) throws ServletException, IOException {
        int id = WebSupport.positiveInt(q.getParameter("task_id"));
        String body = q.getParameter("comment");
        if (id<1) {
            WebSupport.error(q, "E012");
            WebSupport.forward(q, p, "comment-register.jsp");
            return;
        }
        if (!WebSupport.required(body)) {
            WebSupport.error(q, "E001");
            q.setAttribute("comment", body);
            show(q, p);
            return;
        }
        if (body.length()>100) {
            WebSupport.error(q, "E002");
            q.setAttribute("comment", body);
            show(q, p);
            return;
        }
        try {
            if (!new TaskDAO().existsById(id)) {
                WebSupport.error(q, "E012");
                WebSupport.forward(q, p, "comment-register.jsp");
                return;
            }
            CommentBean b = new CommentBean();
            b.setTaskId(id);
            b.setUserId(WebSupport.userId(q));
            b.setComment(body.trim());
            if (new CommentDAO() .insert(b) != 1) {
                WebSupport.error(q, "E009");
                q.setAttribute("comment", body);
                WebSupport.forward(q, p, "comment-register-failure.jsp");
                return;
            }
            p.sendRedirect(WebSupport.path(q, "/task-detail-servlet?task_id="+id));
        }
        catch (SQLException e) {
            WebSupport.log(getServletContext(), e);
            WebSupport.error(q, "E009");
            q.setAttribute("task_id", id);
            q.setAttribute("comment", body);
            WebSupport.forward(q, p, "comment-register-failure.jsp");
        }
    }
}
