package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TimeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Отримуємо timezone
        String timezone = request.getParameter("timezone");
        if (timezone == null || timezone.isEmpty()) {
            timezone = "UTC";  // за замовчуванням, якщо параметр не переданий
        }

        // Виводимо час
        PrintWriter out = response.getWriter();
        try {
            // Формат часу
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
            TimeZone tz = TimeZone.getTimeZone(timezone);
            sdf.setTimeZone(tz);
            String currentTime = sdf.format(new Date());

            // Генерація HTML сторінки
            out.println("<html>");
            out.println("<head><title>Current Time</title></head>");
            out.println("<body>");
            out.println("<h1>Current Time in " + timezone + ": " + currentTime + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }
}