package rts.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import rts.filter.AuthFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class LoginServlet extends BaseHtmlServlet{

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean authenticated = request.getSession(false) != null
                && Boolean.TRUE.equals(request.getSession(false).getAttribute(AuthFilter.SESSION_ATTR));
        if (authenticated) {
            response.sendRedirect(request.getContextPath() + "/admin/clients");
            return;
        }

        boolean error = "1".equals(request.getParameter("error"));

        PrintWriter out = beginHtml(response, "Вход");
        out.println("<h1>Вход для администратора</h1>");
        if (error) {
            out.println("<section class=\"card\"><p style=\"color:red\">Неверный логин или пароль</p></section>");
        }
        out.println("""
                <section class="card">
                    <form method="post" action="/login">
                        <label>Логин: <input name="username" required></label><br><br>
                        <label>Пароль: <input type="password" name="password" required></label><br><br>
                        <button type="submit">Войти</button>
                    </form>
                </section>
                """);
        endHtml(out);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (adminUsername.equals(username) && adminPassword.equals(password)) {
            request.getSession(true).setAttribute(AuthFilter.SESSION_ATTR, Boolean.TRUE);
            response.sendRedirect(request.getContextPath() + "/admin/clients");
        } else {
            response.sendRedirect(request.getContextPath() + "/login?error=1");
        }
    }
}