package rts.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class RequestInfoServlet extends BaseHtmlServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = beginHtml(response, "Информация о запросе");
        out.println("<h1>Информация о HTTP-запросе</h1>");
        out.println("<section class=\"card\"><table>");
        row(out, "HTTP method", request.getMethod());
        row(out, "Request URI", request.getRequestURI());
        row(out, "Query string", request.getQueryString());
        row(out, "Protocol", request.getProtocol());
        row(out, "Remote address", request.getRemoteAddr());
        row(out, "User-Agent", request.getHeader("User-Agent"));
        row(out, "Session id", request.getSession(true).getId());
        out.println("</table></section>");
        out.println("<p><a href=\"/\">← На главную</a></p>");
        endHtml(out);
    }

    private void row(PrintWriter out, String key, String value) {
        out.printf("<tr><th>%s</th><td>%s</td></tr>%n", escape(key), escape(value));
    }
}