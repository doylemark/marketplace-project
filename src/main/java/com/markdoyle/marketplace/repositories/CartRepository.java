package com.markdoyle.marketplace.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.markdoyle.marketplace.entities.CartItem;

@Repository
public interface CartRepository extends CrudRepository<CartItem, Long> {
}
