package com.devcambo.crudjpa;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@SpringBootTest
class CrudJpaApplicationTests {

  @Test
  void contextLoads() {
    /*
     * When user login build user roles to List<SimpleGrantedAuthority>
     * ex: [ROLE_USER, ROLE_ADMIN]
     * */
    List<String> userRoles = List.of("ROLE_USER", "ROLE_ADMIN");
    List<SimpleGrantedAuthority> authorities = userRoles
      .stream()
      .map(r -> new SimpleGrantedAuthority(r))
      .toList();
    System.out.println(authorities);
    System.out.println("---------------------");

    /*
     * When login success build user role as string
     * ex: ROLE_USER,ROLE_ADMIN
     * */
    String collect = authorities
      .stream()
      .map(r -> r.getAuthority())
      .collect(Collectors.joining(","));
    System.out.println(collect);
    System.out.println("--------------------");

    /*
     * After verify user token extract user roles from token
     * ex: [ROLE_USER, ROLE_ADMIN]
     * */
    List<GrantedAuthority> authorities1 = AuthorityUtils.commaSeparatedStringToAuthorityList(
      collect
    );
    System.out.println(authorities1);
  }
}
