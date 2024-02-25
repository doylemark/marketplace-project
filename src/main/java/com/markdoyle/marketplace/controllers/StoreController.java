package com.markdoyle.marketplace.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.markdoyle.marketplace.entities.Car;
import com.markdoyle.marketplace.repositories.CarRepository;
import com.markdoyle.marketplace.repositories.CartRepository;
import com.markdoyle.marketplace.repositories.UserRepository;

@Controller
public class StoreController {
	@Autowired
	CarRepository carRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CartRepository cartRepository;

	@GetMapping("/")
	public String getAll(Model model) {
		List<Car> cars = new ArrayList<>();
		this.carRepository.findAll().forEach(cars::add);
		cars.removeIf((car) -> car.getIsDeleted());

		addAdmin(model);

		model.addAttribute("cars", cars);

		return "store";
	}

	@GetMapping("/cars/{name}")
	public String getCar(@PathVariable String name, Model model) {
		Car car = this.carRepository.findByName(name);

		if (car == null) {
			return "redirect:/store";
		}

		addAdmin(model);
		model.addAttribute("car", car);
		return "car";
	}

	@PostMapping("/admin/cars/create")
	public String createCar(@ModelAttribute Car car, Model model) {
		try {
			validateCar(car);

			if (car.img.length() == 0) {
				car.setImg(null);
			}

			carRepository.save(car);
		} catch (Exception e) {
			return "redirect:/store";
		}

		model.addAttribute("car", car);

		return "redirect:/cars/" + car.name;
	}

	@PostMapping("/admin/cars/patch")
	public String updateCar(@ModelAttribute Car carRef, Model model) {
		try {
			validateCar(carRef);

			Optional<Car> maybeCar = carRepository.findById(carRef.getId());

			if (maybeCar.isEmpty()) {
				throw new RuntimeException("Couldn't find car to update");
			}

			Car car = maybeCar.get();

			car.setName(carRef.getName());
			car.setPrice(carRef.getPrice());
			car.setDlc(carRef.getDlc());
			car.setImg(carRef.getImg());
			car.setCapacity(carRef.getCapacity());
			car.setDescription(carRef.getDescription());

			if (carRef.img.length() == 0) {
				carRef.setImg(null);
			}

			carRepository.save(car);

			addAdmin(model);
			model.addAttribute("car", car);
			return "redirect:/cars/" + carRef.name;
		} catch (Exception e) {
			System.out.println(e);
			return "redirect:/";
		}
	}

	@PostMapping("/admin/cars/delete")
	public String removeCar(@ModelAttribute Car carRef, Model model) {
		Optional<Car> maybeCar = carRepository.findById(carRef.getId());

		if (maybeCar.isPresent()) {
			Car car = maybeCar.get();
			car.setIsDeleted(true);
			carRepository.save(car);
		}

		this.getAll(model);

		return "redirect:/";
	}

	private void validateCar(Car car) throws Exception {
		if (car.getName() == null || car.getName().length() < 2) {
			throw new Exception("Car name must be at least 2 characters long");
		}
		if (car.getPrice() == null || car.getPrice() <= 0) {
			throw new Exception("Price must be a positive value");
		}
		if (car.getCapacity() == null || car.getCapacity() <= 0) {
			throw new Exception("Capacity must be a positive value");
		}
		if (car.getDescription() == null || car.getDescription().isEmpty()) {
			throw new Exception("Description must be provided");
		}
	}

	private void addAdmin(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			Boolean isAdmin = auth.getAuthorities().stream()
					.anyMatch((a) -> a.getAuthority().equals("admin"));

			model.addAttribute("isAdmin", isAdmin);
		}
	}
}
