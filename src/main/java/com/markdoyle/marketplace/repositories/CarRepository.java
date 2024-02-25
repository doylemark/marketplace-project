package com.markdoyle.marketplace.repositories;

import com.markdoyle.marketplace.entities.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<Car, Integer> {
	Car findByName(String name);
}
