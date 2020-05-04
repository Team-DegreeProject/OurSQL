package com.ucd.oursql.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {
    @RequestMapping("/")
//    @RequestMapping("/test")
    public String sqlUI(){
        return "index";
//        ModelAndView a = new ModelAndView("index");
//        \return "redirect:Home.html";
//        return "redirect:Home.html";
        //return a;
    }

    @RequestMapping(value="/home")
    public String sqlWindow(){
        return "Home";
    }


}
