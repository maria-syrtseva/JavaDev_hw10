package org.example;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.StringWriter;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    private TemplateEngine templateEngine;

    @Override
    public void init() {
        // Thymeleaf
        templateEngine = new TemplateEngine();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String timezone = request.getParameter("timezone");

        // Перевірка cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("lastTimezone".equals(cookie.getName())) {
                    timezone = cookie.getValue();
                    break;
                }
            }
        }

        if (timezone == null) {
            timezone = "UTC";
        }

        // Час у Cookie
        Cookie timezoneCookie = new Cookie("lastTimezone", timezone);
        timezoneCookie.setMaxAge(60 * 60 * 24);  // 24 години
        response.addCookie(timezoneCookie);

        // Відправлення відповіді
        ZoneId zoneId = ZoneId.of(timezone);
        LocalDateTime currentTime = LocalDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("currentTime", formattedTime);
        variables.put("timezone", timezone);

        StringWriter writer = new StringWriter();
        templateEngine.process("timeTemplate", new Context(Locale.ENGLISH, variables), writer);
        response.getWriter().write(writer.toString());
    }
}