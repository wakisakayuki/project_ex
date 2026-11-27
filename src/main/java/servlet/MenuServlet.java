package servlet;
import java.io.IOException;import javax.servlet.*;import javax.servlet.annotation.WebServlet;import javax.servlet.http.*;import util.WebSupport;
@WebServlet("/menu") public class MenuServlet extends HttpServlet {private static final long serialVersionUID=1L;@Override protected void doGet(HttpServletRequest q,HttpServletResponse p)throws ServletException,IOException{q.setAttribute("user_name",q.getSession(false).getAttribute("user_name"));WebSupport.forward(q,p,"menu.jsp");}}
