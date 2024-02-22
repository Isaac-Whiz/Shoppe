package com.shoppeapp.shoppe.mail;


import jakarta.annotation.Nonnull;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailService {

    private final String userName;
    private final String password;
    private final String host;
    private final int port;
    private final String enableTls;
    private final String enableAuth;


    public MailService() {
        this.userName = "whizincorporation@gmail.com";
        this.password = "bqcgzvwkninnrjrf";
        this.host = "smtp.gmail.com";
        this.port = 587;
        this.enableTls = "true";
        this.enableAuth = "true";
    }


    public void sendMail(String to, String subject, String body) throws MessagingException {
        Properties properties = getProperties();

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(userName);
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setText(body);
        Transport.send(message);
    }

    @Nonnull
    private Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", enableAuth);
        properties.put("mail.smtp.starttls.enable", enableTls);
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        return properties;
    }
}
