package rts.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class LifecycleServlet extends BaseHtmlServlet {
    private final AtomicInteger requestCounter = new AtomicInteger();
    private LocalDateTime initTime;
    private String lessonName;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.initTime = LocalDateTime.now();
        this.lessonName = config.getInitParameter("lesson");
        System.out.println("[LifecycleServlet] init(): сервлет создан один раз при старте контекста");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int currentRequestNumber = requestCounter.incrementAndGet();
        System.out.println("[LifecycleServlet] doGet(): обработан запрос #" + currentRequestNumber);

        PrintWriter out = beginHtml(response, "Жизненный цикл сервлета");
        out.printf("""
                <h1>Жизненный цикл сервлета</h1>
                <section class="card">
                    <ol>
                        <li><b>init()</b> — вызывается один раз при создании сервлета.</li>
                        <li><b>service()</b> — вызывается на каждый HTTP-запрос и распределяет запрос в doGet(), doPost() и т.д.</li>
                        <li><b>destroy()</b> — вызывается при остановке сервера/контекста.</li>
                    </ol>
                </section>
                <section class="card">
                    <p><b>init-param lesson:</b> %s</p>
                    <p><b>Время init():</b> %s</p>
                    <p><b>Количество GET-запросов к этому сервлету:</b> %d</p>
                    <p>Обновите страницу несколько раз и посмотрите счетчик и консоль IntelliJ.</p>
                </section>
                <p><a href="/">← На главную</a></p>
                """, escape(lessonName), initTime, currentRequestNumber);
        endHtml(out);
    }

    @Override
    public void destroy() {
        System.out.println("[LifecycleServlet] destroy(): сервер останавливается, ресурсы можно освободить");
        super.destroy();
    }
}