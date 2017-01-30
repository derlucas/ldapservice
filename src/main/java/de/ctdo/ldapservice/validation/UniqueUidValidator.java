package de.ctdo.ldapservice.validation;

import de.ctdo.ldapservice.business.PersonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class UniqueUidValidator implements ConstraintValidator<UniqueUid, String> {

    // some usernames taken from different linux systems
    private final Collection<String> words = new ArrayList<>(Arrays.asList(new String[]{
        "root", "admin", "ldap", "system", "test", "hallo", "bin", "deamon", "mail", "ftp", "http", "dbus", "uidd", "nobody",
        "systemd", "systemd-journal-gateway", "systemd-timesync", "systemd-network", "systemd-bus-proxy", "systemd-resolve",
        "systemd-journal-remote", "systemd-journal-upload", "rpc", "dnsmasq", "nbd", "avahi", "polkitd", "colord", "rtkit",
        "usbmux", "ntp", "systemd-coredump", "git", "mysql", "gdm", "games", "sync", "sys", "news", "uucp", "bin", "man",
        "lp", "proxy", "www-data", "backup", "list", "irc", "gnats", "syslog", "_apt", "lxd", "dnsmasq", "messagebus",
        "sshd", "openldap", "bind"
    }));

    @Autowired
    private PersonService dao;

    @Override
    public void initialize(final UniqueUid constraintAnnotation) {

    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        // we do not validate if the value is blank. user has to use @NotEmpty for checking that constraint
        if (StringUtils.isBlank(value)) {
            return true;
        }
        return !words.contains(value.toLowerCase()) && !dao.isUidTaken(String.valueOf(value));
    }
}