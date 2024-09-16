package com.example.demo.repositories;

import com.example.demo.models.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    public ShoppingCart addCourseToCart(Long cartId, Long userId);
    public double calculateTotalAmount(Long cartId);
}
