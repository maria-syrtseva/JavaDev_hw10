package org.example;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.TimeZone;

@WebFilter("/time")
public class TimezoneValidateFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String timezone = ((HttpServletRequest) request).getParameter("timezone");

        if (timezone != null && !timezone.isEmpty()) {
            // Перевірка на коректність timezone
            TimeZone tz = TimeZone.getTimeZone(timezone);
            if (tz.getID().equals("GMT")) {
                // Якщо є помилка, тоді 400
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid timezone");
                return;
            }
        }

        // Продовження обробки
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}