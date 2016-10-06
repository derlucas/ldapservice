package de.ctdo.ldapservice.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.ldap")
public class LdapConfig {

    private String url;
    private String base;
    private String userDn;
    private String password;
}
