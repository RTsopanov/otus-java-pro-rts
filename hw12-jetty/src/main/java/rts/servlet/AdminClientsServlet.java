package rts.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import rts.dto.ClientDto;
import rts.model.Address;
import rts.model.Client;
import rts.model.Phone;
import rts.service.ClientService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Component
public class AdminClientsServlet extends BaseHtmlServlet {
    private final ClientService clientService;

    public AdminClientsServlet(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        renderPage(response, null);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String street = request.getParameter("street");
        String phoneNumber = request.getParameter("phone");

        if (name == null || name.isBlank()) {
            renderPage(response, "Имя клиента обязательно");
            return;
        }

        Client client = new Client(name);
        if (street != null && !street.isBlank()) {
            client.setAddress(new Address(null, street));
        }
        if (phoneNumber != null && !phoneNumber.isBlank()) {
            client.addPhone(new Phone(null, phoneNumber));
        }

        clientService.save(client);

        response.sendRedirect(request.getContextPath() + "/admin/clients");
    }

    private void renderPage(HttpServletResponse response, String error) throws IOException {
        List<ClientDto> clients = clientService.findAll();

        PrintWriter out = beginHtml(response, "Клиенты");
        out.println("<h1>Админ-панель: клиенты</h1>");
        out.println("<p><a href=\"/logout\">Выйти</a></p>");

        if (error != null) {
            out.printf("<section class=\"card\"><p style=\"color:red\">%s</p></section>%n", escape(error));
        }

        out.println("""
                <section class="card">
                    <h2>Добавить клиента</h2>
                    <form method="post" action="/admin/clients">
                        <label>Имя: <input name="name" required></label><br><br>
                        <label>Адрес (улица): <input name="street"></label><br><br>
                        <label>Телефон: <input name="phone"></label><br><br>
                        <button type="submit">Создать</button>
                    </form>
                </section>
                """);

        out.println("<section class=\"card\"><h2>Список клиентов</h2><table>");
        out.println("<tr><th>ID</th><th>Имя</th><th>Адрес</th><th>Телефоны</th></tr>");
        for (ClientDto c : clients) {
            out.printf("<tr><td>%d</td><td>%s</td><td>%s</td><td>%s</td></tr>%n",
                    c.getId(),
                    escape(c.getName()),
                    escape(c.getAddressStreet()),
                    escape(String.join(", ", c.getPhoneNumbers())));
        }
        if (clients.isEmpty()) {
            out.println("<tr><td colspan=\"4\">Клиентов пока нет</td></tr>");
        }
        out.println("</table></section>");

        endHtml(out);
    }
}