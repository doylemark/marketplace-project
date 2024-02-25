package com.markdoyle.marketplace.controllers;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.markdoyle.marketplace.entities.Car;
import com.markdoyle.marketplace.entities.CartItem;
import com.markdoyle.marketplace.entities.User;
import com.markdoyle.marketplace.repositories.CarRepository;
import com.markdoyle.marketplace.repositories.CartRepository;
import com.markdoyle.marketplace.repositories.UserRepository;

@Controller()
public class CartController {
	@Autowired
	CarRepository carRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CartRepository cartRepository;

	@PostMapping("/cart/add")
	public String cartSubmit(@ModelAttribute Car carModel, Model model) {
		CartItem item = new CartItem();
		Car car = carRepository.findByName(carModel.name);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (!auth.isAuthenticated()) {
			return "redirect:/store";
		}

		item.setCar(car);
		User user = userRepository.findByUsername(auth.getName());

		item.setUser(user);
		item.setQuantity(1);
		this.cartRepository.save(item);

		List<CartItem> items = cartRepository.findAllByUserId(user.id);

		model.addAttribute("cart", items);

		return "redirect:/cart";
	}

	@GetMapping("/cart")
	public String cart(Model model) {
		Optional<List<CartItem>> items = getAllUserItems();

		if (items.isEmpty()) {
			return "redirect:/store";
		}

		model.addAttribute("cart", items.get());

		return "/cart";
	}

	@PostMapping("/cart/quantity")
	public String car(@ModelAttribute CartItem cartItem, Model model) {
		Optional<CartItem> optItem = cartRepository.findById(cartItem.id);

		if (optItem.isEmpty()) {
			return this.cart(model);
		}

		CartItem item = optItem.get();
		item.setQuantity(cartItem.quantity);

		if (item.getQuantity() == 0) {
			cartRepository.delete(item);
		} else {
			cartRepository.save(item);
		}

		Optional<List<CartItem>> items = getAllUserItems();

		if (items.isEmpty()) {
			return "redirect:/store";
		}

		Integer price = items.get().stream().mapToInt((i) -> i.car.price).sum();

		DecimalFormat df = new DecimalFormat("$#,###.##");
		model.addAttribute("price", df.format(price));
		model.addAttribute("cart", items.get());

		return "redirect:/cart";
	}

	private Optional<List<CartItem>> getAllUserItems() {
		User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		if (user == null) {
			return Optional.empty();
		}

		List<CartItem> items = cartRepository.findAllByUserId(user.id);

		return Optional.of(items);
	}
}
