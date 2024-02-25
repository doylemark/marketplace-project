package com.markdoyle.marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.markdoyle.marketplace.entities.User;
import com.markdoyle.marketplace.repositories.UserRepository;

@Controller
public class UserController {
	@Autowired
	UserRepository userRepository;

	@GetMapping("/signup")
	public String auth(Model model) {
		model.addAttribute("user", new User());

		return "signup";
	}

	@PostMapping("/signup")
	public String signup(@ModelAttribute User user, Model model) {
		return "signup";
	}

}
