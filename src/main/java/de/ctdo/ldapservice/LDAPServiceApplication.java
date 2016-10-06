package de.ctdo.ldapservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
@SpringBootApplication
public class LDAPServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LDAPServiceApplication.class, args);
    }


    // configuring spring LDAP manually, because there is no spring-boot auto configuration support yet
    @Bean
    @ConfigurationProperties(prefix="app.ldap")
    public LdapContextSource contextSource() {
        return new LdapContextSource();
    }

    @Bean
    public LdapTemplate ldapTemplate(ContextSource contextSource) {
        return new LdapTemplate(contextSource);
    }



}
