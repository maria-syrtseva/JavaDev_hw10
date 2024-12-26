package org.example;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.TimeZone;

@WebFilter("/time")
public class TimezoneValidateFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    // Обробка запиту
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String timezone = httpRequest.getParameter("timezone");

        if (timezone != null && !isValidTimezone(timezone)) {

            // Якщо часова зона некоректна, видаємо помилку
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 400
            httpResponse.getWriter().write("Invalid timezone");
            return;
        }

        // Якщо все коректно, продовжуємо обробку
        chain.doFilter(request, response);
    }

    // Перевірка валідності часового поясу
    private boolean isValidTimezone(String timezone) {
        try {
            TimeZone.getTimeZone(timezone);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void destroy() {
    }
}