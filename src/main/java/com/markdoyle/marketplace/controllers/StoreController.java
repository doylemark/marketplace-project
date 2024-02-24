package com.markdoyle.marketplace.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.markdoyle.marketplace.entities.Car;
import com.markdoyle.marketplace.repositories.CarRepository;
import com.markdoyle.marketplace.repositories.CartRepository;

@Controller
public class StoreController {
	@Autowired
	CarRepository carRepository;

	@Autowired
	CartRepository cartRepository;

	@GetMapping("/")
	public String getAll(Model model) {
		List<Car> cars = new ArrayList<>();
		this.carRepository.findAll().forEach(cars::add);

		model.addAttribute("cars", cars);

		return "store";
	}

	@GetMapping("/cars/{name}")
	public String getCar(@PathVariable String name, Model model) {
		Car car = this.carRepository.findByName(name);
		model.addAttribute("car", car);

		return "car";
	}

	@PostMapping("/car")
	public String cartSubmit(@ModelAttribute Car car, Model model) {
		return "cart";
	}
}
