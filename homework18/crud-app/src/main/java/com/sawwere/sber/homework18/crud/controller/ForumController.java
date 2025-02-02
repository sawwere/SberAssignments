package com.sawwere.sber.homework18.crud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ForumController {
    public static final String INDEX = "/";

    @GetMapping(INDEX)
    public ModelAndView index() {
        return new ModelAndView("index");
    }
}
