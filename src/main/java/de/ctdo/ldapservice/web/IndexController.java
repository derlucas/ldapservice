package de.ctdo.ldapservice.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
//@AllArgsConstructor(onConstructor = @__(@Autowired))
//@Slf4j
public class IndexController {


    @GetMapping
    public String handleGet() {

        return "index";
    }

}
