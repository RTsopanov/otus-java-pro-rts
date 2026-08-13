package rts.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class EchoServlet extends BaseHtmlServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        renderParameters(request, response, "GET-параметры");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        renderParameters(request, response, "POST-форма");
    }

    private void renderParameters(HttpServletRequest request, HttpServletResponse response, String title) throws IOException {
        PrintWriter out = beginHtml(response, title);
        out.printf("<h1>%s</h1>%n", escape(title));
        out.println("<section class=\"card\"><table><tr><th>Параметр</th><th>Значение</th></tr>");
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            out.printf("<tr><td>%s</td><td>%s</td></tr>%n",
                    escape(entry.getKey()),
                    escape(String.join(", ", entry.getValue())));
        }
        if (request.getParameterMap().isEmpty()) {
            out.println("<tr><td colspan=\"2\">Параметров нет. Попробуйте /echo?name=Student</td></tr>");
        }
        out.println("</table></section>");
        out.println("<p><a href=\"/\">← На главную</a></p>");
        endHtml(out);
    }
}