package com.sak.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.sak.entity.User;
import com.sak.service.ProductService;
import com.sak.service.UserService;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String home(Model model) {
        productService.getProductHome(model);
        return "home";
    }

    @GetMapping("/signup")
    public ModelAndView showsignup(Model model) {
        model.addAttribute("user", new User());
        return new ModelAndView("signup");
    }

    @PostMapping("/signup")
    public ModelAndView getsignup(@ModelAttribute User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/login")
    public String showlogin() {
        return "login";
    }

    @PostMapping("/login")
    public String getlogin(@RequestParam String userName,
                           @RequestParam String password,
                           Model model) {

        // Admin Login
        if ("admin".equals(userName) && "admin".equals(password)) {
            return "redirect:/admin/admin_dash";
        }

        // User Login
        User auth = userService.authenticate(userName, password);

        if (auth != null) {
            productService.getProductHome(model);
            return "home";
        }

        model.addAttribute("error", "Invalid Username or Password");
        return "login";
    }
}