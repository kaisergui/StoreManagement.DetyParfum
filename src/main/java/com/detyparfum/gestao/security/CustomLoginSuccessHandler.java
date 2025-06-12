package com.detyparfum.gestao.security;

import java.io.IOException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JavaMailSender mailSender;

    public CustomLoginSuccessHandler(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String email = authentication.getName();
        response.sendRedirect("/index.html");
 //     sendLoginNotification(email); 
    }

    private void sendLoginNotification(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Login realizado com sucesso");
        message.setText("Olá, o login na aplicação Dety Parfum foi realizado com sucesso.");
        mailSender.send(message);
    }
}