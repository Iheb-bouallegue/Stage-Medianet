package com.example.medianet.stagemedianet.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendRegistrationEmail(String to, String username, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Confirmation d'inscription");
        message.setText("Bonjour " + username + ",\n\nVotre compte a été créé avec succès.\n\n"
                + "Nom d'utilisateur : " + username + "\n"
                + "Mot de passe : " + password + "\n\n"
                + "Merci de vous connecter à la plateforme.");
        mailSender.send(message);
    }
}
