package com.todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
    return "redirect:/login"; // Redirige a la página de login al acceder a la raíz
    }
}
