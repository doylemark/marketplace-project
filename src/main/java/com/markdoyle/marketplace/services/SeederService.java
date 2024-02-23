package com.markdoyle.marketplace.services;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.markdoyle.marketplace.entities.Car;
import com.markdoyle.marketplace.repositories.CarRepository;

@Service
public class SeederService implements CommandLineRunner {
    @Autowired
    CarRepository carRepository;

    public void run(String... args) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<Car> cars = mapper.readValue(new File("./data/seed.json"), new TypeReference<List<Car>>() {
            });
            carRepository.saveAll(cars);
        } catch (Exception err) {
            throw new RuntimeException("Seeding failed", err);
        }
    }
}
