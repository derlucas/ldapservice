package de.ctdo.ldapservice.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String handleGet() {
        return "index";
    }

    @GetMapping("/update")
    public String handleTemporarilyUpdate() {
        return "update";
    }

    @GetMapping("/request")
    public String handleTemporarilyReqtestPassword() {
        return "update";
    }
}
