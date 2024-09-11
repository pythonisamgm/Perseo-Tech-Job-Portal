package com.example.demo.repositories;

import com.example.demo.models.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}
