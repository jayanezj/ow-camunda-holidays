package net.openwebinars.camunda.config;

import io.digitalstate.camunda.authentication.jwt.ProcessEngineAuthenticationFilterJwt;
import javax.servlet.Filter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamundaSecurityFilter {

  @Value("${camunda.rest-api.jwt.secret-path}")
  private String jwtSecretPath;
  @Value("${camunda.rest-api.jwt.validator-class}")
  private String jwtValidatorClass;

  @Bean
  public FilterRegistrationBean processEngineAuthenticationFilter() {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setName("camunda-jwt-auth");
    registration.addInitParameter("authentication-provider",
        "io.digitalstate.camunda.authentication.jwt.AuthenticationFilterJwt");
    registration.addInitParameter("jwt-secret-path", jwtSecretPath);
    registration.addInitParameter("jwt-validator", jwtValidatorClass);
    registration.addUrlPatterns("/engine-rest/*");
    registration.setFilter(getProcessEngineAuthenticationFilter());
    return registration;
  }

  @Bean
  public Filter getProcessEngineAuthenticationFilter() {
    return new ProcessEngineAuthenticationFilterJwt();
  }

  public String getJwtSecretPath() {
    return jwtSecretPath;
  }

  public void setJwtSecretPath(String jwtSecretPath) {
    this.jwtSecretPath = jwtSecretPath;
  }

  public String getJwtValidatorClass() {
    return jwtValidatorClass;
  }

  public void setJwtValidatorClass(String jwtValidatorClass) {
    this.jwtValidatorClass = jwtValidatorClass;
  }
}
