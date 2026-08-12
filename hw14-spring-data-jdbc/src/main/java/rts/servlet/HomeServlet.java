package rts.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class HomeServlet extends BaseHtmlServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = beginHtml(response, "Jetty Servlet API Demo");
        out.println("""
        <section class="card">
            <h2>Админка</h2>
            <p><a href="/login">Войти как администратор</a></p>
        </section>
        """);
        out.println("""
                <h1>Jetty + Servlet API</h1>
                <p>Мини-проект для ДЗ: встроенный Web-сервер, подключение сервлетов и жизненный цикл сервлета.</p>

                <section class="card">
                    <h2>Что посмотреть</h2>
                    <ul>
                        <li><a href="/lifecycle">/lifecycle</a> — init(), service()/doGet(), destroy(), init-param</li>
                        <li><a href="/echo?name=Student&course=Jetty">/echo?name=Student&amp;course=Jetty</a> — GET-параметры и POST-форма</li>
                        <li><a href="/request-info">/request-info</a> — данные HTTP-запроса</li>
                    </ul>
                </section>

                <section class="card">
                    <h2>POST пример</h2>
                    <form method="post" action="/echo">
                        <label>Ваше имя: <input name="name" value="Student"></label>
                        <label>Сообщение: <input name="message" value="Привет из формы"></label>
                        <button type="submit">Отправить POST</button>
                    </form>
                </section>
                """);
        endHtml(out);
    }
}