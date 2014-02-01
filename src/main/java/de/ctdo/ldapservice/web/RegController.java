package de.ctdo.ldapservice.web;

import de.ctdo.ldapservice.business.PersonService;
import de.ctdo.ldapservice.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/register")
public class RegController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegController.class);

    @Autowired
    private PersonService personService;

    @RequestMapping(method = RequestMethod.GET)
    public String handleGet(Model model, @ModelAttribute Person person) {
        LOGGER.info("handleGet model = " + model);

        model.addAttribute("person", person);
        return "person";
    }


    @RequestMapping(method = RequestMethod.POST)
    public String handlePost(@Valid Person person, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        LOGGER.info("handlePost person = " + person + " bindingResult = " + bindingResult);

        if (bindingResult.hasErrors()) {
            LOGGER.info("has Errors");
            return "person";
        }

        Person p = personService.create(person);

        if (p == null) {
            LOGGER.error("could not save user");
            bindingResult.reject("status.user.notcreated");
            return "person";
        }
        redirectAttributes.addFlashAttribute("status", "status.user.created");

        return "redirect:register/created";

    }

    @RequestMapping(method = RequestMethod.GET, value = "/created")
    public String handleCreated() {

        return "created";
    }


}
