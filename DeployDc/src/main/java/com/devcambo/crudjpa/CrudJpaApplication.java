package com.devcambo.crudjpa;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class CrudJpaApplication {

  public static void main(String[] args) {
    SpringApplication.run(CrudJpaApplication.class, args);
  }
}
