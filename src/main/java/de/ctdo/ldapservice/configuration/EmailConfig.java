package de.ctdo.ldapservice.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.email")
public class EmailConfig {

    private String host;
    private String from;
    private String subject;
    private String to;

}
