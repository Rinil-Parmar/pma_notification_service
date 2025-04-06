package com.example.service;

import com.example.dto.NotificationRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendParcelStatusEmail(NotificationRequest request) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(request.getUserEmail());
        helper.setSubject("Parcel Status Update - " + request.getProjectStatus());
        helper.setText(buildEmailBody(request), true); // true = HTML content
        mailSender.send(message);
    }

    private String buildEmailBody(NotificationRequest request) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; color: #333; }" +
                ".container { max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #e0e0e0; }" +
                ".header { background-color: #f5f5f5; padding: 10px; text-align: center; }" +
                ".status { color: #0066cc; font-weight: bold; }" +
                ".footer { font-size: 12px; color: #777; text-align: center; margin-top: 20px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<div class='header'>" +
                "<h2>Parcel FLow</h2>" +
                "</div>" +
                "<p>Dear " + request.getUsername() + ",</p>" +
                "<p>We’re pleased to inform you that your parcel status has been updated.</p>" +
                "<p><strong>Current Status:</strong> <span class='status'>" + request.getProjectStatus() + "</span></p>" +
                "<p><strong>Details:</strong></p>" +
                "<ul>" +
                "<li><strong>Recipient:</strong> " + request.getUsername() + "</li>" +
                "<li><strong>Email:</strong> " + request.getUserEmail() + "</li>" +
                "<li><strong>Updated On:</strong> " + java.time.LocalDateTime.now().toString() + "</li>" +
                "</ul>" +
                "<p>Thank you for choosing our service. If you have any questions, feel free to contact our support team at <a href='mailto:support@parcelmanagement.com'>support@parcelmanagement.com</a>.</p>" +
                "<div class='footer'>" +
                "Parcel Flow | 123 Delivery Lane, Shipping City | <a href='http://www.parcelmanagement.com'>www.parcelmanagement.com</a><br>" +
                "This is an automated message, please do not reply directly to this email." +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }
}