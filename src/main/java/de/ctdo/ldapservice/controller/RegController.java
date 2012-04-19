package de.ctdo.ldapservice.controller;


import de.ctdo.ldapservice.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;


@Controller
@RequestMapping("/person")
public class RegController  {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegController.class);

//    @Autowired
//    private Validator validator;

    @RequestMapping(method = RequestMethod.GET)
    public String handleGet(ModelMap model) {
        LOGGER.info("handleGet model = " + model);

        Person p = new Person();
        p.setFirstName("LUcas");
        model.addAttribute("person", p);
        return "person";
    }


    @RequestMapping(method = RequestMethod.POST)
    public String handlePost(@ModelAttribute(value = "person") @Valid Person person, BindingResult bindingResult) {
        LOGGER.info("handlePost person = " + person + " bindingResult = " + bindingResult);

        if(bindingResult.hasErrors()) {
            LOGGER.info("has Errors");
            return "person";
        }
        
        return "personCreated";
    }
    
    

}
