package com.detyparfum.gestao.security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CsrfLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        if (csrfToken != null) {
            System.out.println("🔐 CSRF Token esperado pelo Spring:");
            System.out.println("  Header Name: " + csrfToken.getHeaderName());
            System.out.println("  Parameter Name: " + csrfToken.getParameterName());
            System.out.println("  Token Value: " + csrfToken.getToken());
        } else {
            System.out.println("⚠️ Nenhum CSRF token presente na requisição.");
        }

        filterChain.doFilter(request, response);
    }
}