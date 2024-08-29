package br.com.goytaborgs.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    private String carregaTemplateEmail() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/template-email.html");
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }

    public void enviaEmail(String to, String resetLink, String nome) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("goytaborgs1@gmail.com");
            helper.setSubject("Recuperação de Senha");
            helper.setTo(to);

            String template = carregaTemplateEmail();
            template = template.replace("#{nome}", nome);
            template = template.replace("#{resetLink}", resetLink);

            helper.setText(template, true);

            // Adicionando o logo da empresa
            ClassPathResource resource = new ClassPathResource("static/logo.png");
            helper.addInline("logoImage", resource);

            javaMailSender.send(message);
        } catch (MessagingException | IOException exception) {
            System.out.println("Falha ao enviar o email");
            exception.printStackTrace();
        }
    }
}
