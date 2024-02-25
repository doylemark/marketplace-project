package com.markdoyle.marketplace.services;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.markdoyle.marketplace.entities.Car;
import com.markdoyle.marketplace.entities.CartItem;
import com.markdoyle.marketplace.entities.User;
import com.markdoyle.marketplace.repositories.CarRepository;
import com.markdoyle.marketplace.repositories.CartRepository;
import com.markdoyle.marketplace.repositories.UserRepository;

@Service
public class SeederService implements CommandLineRunner {
    @Autowired
    CarRepository carRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void run(String... args) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<Car> cars = mapper.readValue(new File("./data/seed.json"), new TypeReference<List<Car>>() {
            });
            carRepository.saveAll(cars);

            User user = new User();
            user.setUsername("john");
            user.setPassword(passwordEncoder.encode("password"));
            user.setIsAdmin(false);
            userRepository.save(user);

            User user2 = new User();
            user2.setUsername("jack");
            user2.setPassword(passwordEncoder.encode("password"));
            user2.setIsAdmin(false);
            userRepository.save(user2);

            User user3 = new User();
            user3.setUsername("mark");
            user3.setPassword(passwordEncoder.encode("password"));
            user3.setIsAdmin(true);
            userRepository.save(user3);

            CartItem item = new CartItem();
            item.setUser(user);
            item.setQuantity(1);
            item.setCar(cars.get(0));
            this.cartRepository.save(item);
        } catch (Exception err) {
            throw new RuntimeException("Seeding failed", err);
        }
    }
}
