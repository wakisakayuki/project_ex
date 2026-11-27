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
 * 選択されたタスクの詳細とコメントを表示するサーブレット。
 */
@WebServlet("/task-detail-servlet") public class TaskDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override protected void doGet(HttpServletRequest q, HttpServletResponse p) throws ServletException, IOException {
        show(q, p);
    }
    @Override protected void doPost(HttpServletRequest q, HttpServletResponse p) throws ServletException, IOException {
        q.setCharacterEncoding("UTF-8");
        show(q, p);
    }

    private void show(HttpServletRequest q, HttpServletResponse p) throws ServletException, IOException {
        int id = WebSupport.positiveInt(q.getParameter("task_id"));
        if (id<1) {
            WebSupport.error(q, "E012");
            WebSupport.forward(q, p, "task-detail.jsp");
            return;
        }
        try {
            TaskDAO td = new TaskDAO();
            TaskBean t = td.findById(id);
            if (t == null) {
                WebSupport.error(q, "E012");
                WebSupport.forward(q, p, "task-detail.jsp");
                return;
            }
            java.util.List<CommentBean> cs = new CommentDAO().findByTaskIdOrderByUpdateDatetime(id);
            q.setAttribute("task", t);
            q.setAttribute("comments", cs);
            q.setAttribute("isTaskOwner", t.getUserId().equals(WebSupport.userId(q)));
            q.setAttribute("currentUserId", WebSupport.userId(q));
            WebSupport.forward(q, p, "task-detail.jsp");
        }
        catch (SQLException e) {
            WebSupport.log(getServletContext(), e);
            WebSupport.error(q, "E013");
            WebSupport.forward(q, p, "task-detail.jsp");
        }
    }
}
