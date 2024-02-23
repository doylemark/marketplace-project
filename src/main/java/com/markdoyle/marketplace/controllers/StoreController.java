package com.markdoyle.marketplace.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StoreController {
	@GetMapping("/")
	public String getAll(Model model) {
		return "store";
	}
}
