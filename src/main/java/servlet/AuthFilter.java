package servlet;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import util.WebSupport;

/**
 * ログイン状態を確認し、未認証ユーザーの画面アクセスを制御するフィルター。
 */
@WebFilter("/*") public class AuthFilter implements Filter {
    @Override public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest q = (HttpServletRequest) request;
        HttpServletResponse p = (HttpServletResponse) response;
        q.setCharacterEncoding("UTF-8");
        p.setCharacterEncoding("UTF-8");
        String uri = q.getRequestURI().substring(q.getContextPath().length());
        if (uri.equals("/login") || uri.equals("/login-servlet") || uri.startsWith("/assets/") || uri.startsWith("/WEB-INF/")) {
            chain.doFilter(request, response);
            return;
        }
        HttpSession s = q.getSession(false);
        if (s == null || s.getAttribute("user_id") == null) {
            if (s != null) s.invalidate();
            WebSupport.error(q, "E014");
            WebSupport.forward(q, p, "login.jsp");
            return;
        }
        chain.doFilter(request, response);
    }
}
