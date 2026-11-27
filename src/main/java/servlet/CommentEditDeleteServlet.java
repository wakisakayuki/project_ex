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
 * コメントの編集・削除リクエストを処理するサーブレット。
 */
@WebServlet(urlPatterns = {
    "/comment-edit-delete-servlet", "/comment-edit-result-servlet", "/comment-delete-result-servlet"
}) public class CommentEditDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override protected void doGet(HttpServletRequest q, HttpServletResponse p) throws ServletException, IOException {
        p.sendRedirect(WebSupport.path(q, "/task-list"));
    }
    @Override protected void doPost(HttpServletRequest q, HttpServletResponse p) throws ServletException, IOException {
        q.setCharacterEncoding("UTF-8");
        String path = q.getServletPath();
        if (path.endsWith("comment-edit-delete-servlet")) {
            show(q, p);
            return;
        }
        int cid = WebSupport.positiveInt(q.getParameter("comment_id"));
        try {
            CommentDAO d = new CommentDAO();
            CommentBean b = d.findByIdAndUserId(cid, WebSupport.userId(q));
            if (b == null) {
                WebSupport.error(q, "E010");
                WebSupport.forward(q, p, "comment-edit-delete-failure.jsp");
                return;
            }
            if (path.endsWith("comment-edit-result-servlet")) {
                String body = q.getParameter("comment");
                if (!WebSupport.required(body) || body.length()>100) {
                    WebSupport.error(q, !WebSupport.required(body) ? "E001" : "E002");
                    q.setAttribute("comment", body);
                    q.setAttribute("comment_id", cid);
                    q.setAttribute("task_id", b.getTaskId());
                    WebSupport.forward(q, p, "comment-edit-delete-failure.jsp");
                    return;
                }
                if (d.updateByIdAndUserId(cid, WebSupport.userId(q), body.trim()) != 1) {
                    WebSupport.error(q, "E010");
                    WebSupport.forward(q, p, "comment-edit-delete-failure.jsp");
                    return;
                }
            }
            else if (d.deleteByIdAndUserId(cid, WebSupport.userId(q)) != 1) {
                WebSupport.error(q, "E011");
                WebSupport.forward(q, p, "comment-edit-delete-failure.jsp");
                return;
            }
            p.sendRedirect(WebSupport.path(q, "/task-detail-servlet?task_id="+b.getTaskId()));
        }
        catch (SQLException e) {
            WebSupport.log(getServletContext(), e);
            q.setAttribute("comment_id", cid);
            WebSupport.error(q, "E013");
            WebSupport.forward(q, p, "comment-edit-delete-failure.jsp");
        }
    }

    private void show(HttpServletRequest q, HttpServletResponse p) throws ServletException, IOException {
        int id = WebSupport.positiveInt(q.getParameter("comment_id"));
        try {
            CommentBean b = new CommentDAO().findByIdAndUserId(id, WebSupport.userId(q));
            if (b == null) {
                WebSupport.error(q, "E010");
                WebSupport.forward(q, p, "comment-edit-delete-failure.jsp");
                return;
            }
            q.setAttribute("comment", b);
            if (q.getParameter("comment") != null) q.setAttribute("commentValue", q.getParameter("comment"));
            WebSupport.forward(q, p, "comment-edit-delete.jsp");
        }
        catch (SQLException e) {
            WebSupport.log(getServletContext(), e);
            WebSupport.error(q, "E013");
            WebSupport.forward(q, p, "comment-edit-delete-failure.jsp");
        }
    }
}
