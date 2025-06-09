package com.devcambo.cloudopenfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CloudOpenFeignApplication {

  public static void main(String[] args) {
    SpringApplication.run(CloudOpenFeignApplication.class, args);
  }
}
