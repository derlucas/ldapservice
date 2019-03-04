LDAP Selfservice
=============

This Software Project is for managing User-Accounts in a LDAP directory. It is based on Spring-Boot
with Thymeleaf as templating Framework and Spring-Boot-Data-LDAP as simple Data Backend to the LDAP
 Server
 

Notes for use in development Environment
------------

To be able to connect to your LDAP Server, you need to set a username/password/baseDN and host. All
parameters except the password option are already in the application.yaml file.

To set the password, you can add this to the yaml file, or better set this via an environment variable.
The name of the variable is "spring.ldap.password".
Please this this for more information:  https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html

Or use a command line like: "-Dspring.ldap.password=XXXYZ"
