package com.markdoyle.marketplace.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.markdoyle.marketplace.entities.Car;
import com.markdoyle.marketplace.repositories.CarRepository;

@Controller
public class StoreController {
	@Autowired
	CarRepository carRepository;

	@GetMapping("/")
	public String getAll(Model model) {
		List<Car> cars = new ArrayList<>();
		this.carRepository.findAll().forEach(cars::add);

		model.addAttribute("cars", cars);

		return "store";
	}
}
