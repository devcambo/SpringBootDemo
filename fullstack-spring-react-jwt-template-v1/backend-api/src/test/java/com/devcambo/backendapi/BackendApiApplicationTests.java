package com.devcambo.backendapi;

import com.devcambo.backendapi.dto.auth.ForgotPwdDto;
import com.devcambo.backendapi.service.AuthService;
import com.devcambo.backendapi.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApiApplicationTests {

  @Autowired
  private EmailService emailService;

  @Autowired
  private AuthService authService;

  @Test
  void contextLoads() {
    String to = "pisethsek9899@gmail.com";
    String subject = "Test Send Mail";
    String body = "lorem ipsum dolor sit amet";
    emailService.send(to, subject, body);
  }

  @Test
  void testForgotPwd() {
    authService.forgotPassword(new ForgotPwdDto("Ceasar_Koelpin14@hotmail.com"));
  }
}
