package com.devcambo.backendapi.service.impl;

import com.devcambo.backendapi.exception.CustomMailException;
import com.devcambo.backendapi.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender sender;

  @Override
  public void send(String to, String subject, String body) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom("noreply@devcambo.com"); // Optional when using gmail
      message.setTo(to);
      message.setSubject(subject);
      message.setText(body);
      sender.send(message);
    } catch (MailException exception) {
      throw new CustomMailException(exception.getMessage());
    }
  }
}
