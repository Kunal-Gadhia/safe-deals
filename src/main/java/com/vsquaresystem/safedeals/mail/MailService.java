/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author hp2
 */
@Service
@Transactional
public class MailService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void sendMail(String mailId) {
        // Recipient's email ID needs to be mentioned.
        String to = mailId;//change accordingly

        // Sender's email ID needs to be mentioned
        String from = "vsoftdevtest@gmail.com";//change accordingly
        final String username = "vsoftdevtest@gmail.com";//change accordingly
        final String password = "vsoft@admin1";//change accordingly

        // Assuming you are sending email through relay.jangosmtp.net
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("Testing Subject");

            // Now set the actual message
            message.setText("Hello, this is sample for to check send "
                    + "email using JavaMailAPI ");

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
//        System.err.println("Coming to the mail function :" + mailId);
//        String to = mailId;//change accordingly  
//
//        //Get the session object  
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.socketFactory.port", "465");
//        props.put("mail.smtp.socketFactory.class",
//                "javax.net.ssl.SSLSocketFactory");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.port", "465");
//
//        Session session = Session.getDefaultInstance(props,
//                new javax.mail.Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication("vsoftdevtest@gmail.com", "vsoft@admin1");//change accordingly  
//                    }                    
//                });
//
//        //compose message  
//        try {
//            System.err.println("In Try??");
//            MimeMessage message = new MimeMessage(session);
//            message.setFrom(new InternetAddress("vsoftdevtest@gmail.com"));//change accordingly  
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//            message.setSubject("Hello");
//            message.setText("Testing.......");
//
//            //send message  
//            Transport.send(message);
//
//            System.out.println("message sent successfully");
//
//        } catch (MessagingException e) {
//            System.err.println("In Catch??");
//            throw new RuntimeException(e);
//        }

    }
}