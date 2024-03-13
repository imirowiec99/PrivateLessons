package pl.korpetycje.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.korpetycje.demo.model.Mail;

@Service
public class MailService {

    @Autowired
    JavaMailSender mailSender;


    @Value("$(spring.mail.username)")
    private String fromMail;


    public void sendMail(String email, Mail mail){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("przepawel1@gmail.com");
        simpleMailMessage.setSubject(mail.getSubject());
        simpleMailMessage.setText(mail.getMessage());
        simpleMailMessage.setTo(email);
        mailSender.send(simpleMailMessage);
    }
}
