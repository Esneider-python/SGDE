package com.mycompany.sgde.util;

import jakarta.mail.Session;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

public class CorreoUtil {

    private static final String REMITENTE = "esneiderortega1999@gmail.com"; // <-- cambia esto
    private static final String PASSWORD = "rxylhonapxrtyhsx";

    public static void enviarCodigo(String destinatario, String codigo) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");

        Session sesion = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(REMITENTE, PASSWORD);
            }
        });

        Message mensaje = new MimeMessage(sesion);
        mensaje.setFrom(new InternetAddress(REMITENTE));
        mensaje.setRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
        mensaje.setSubject("Código de recuperación de contraseña");
        mensaje.setText("Tu código de verificación es: " + codigo);

        Transport.send(mensaje);
    }
}
