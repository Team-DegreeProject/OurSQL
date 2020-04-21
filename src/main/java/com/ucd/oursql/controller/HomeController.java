package com.ucd.oursql.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @RequestMapping("/home")
    public String sqlUI(){
        //return "Home";
        ModelAndView a = new ModelAndView("Home");
        return "redirect:Home.html";
        //return a;
    }
}
