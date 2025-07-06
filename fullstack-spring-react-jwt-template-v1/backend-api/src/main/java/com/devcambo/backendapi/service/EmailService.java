package com.devcambo.backendapi.service;

public interface EmailService {
  void send(String to, String subject, String body);
}
