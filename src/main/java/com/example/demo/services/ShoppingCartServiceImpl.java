
package com.example.demo.services;

import com.example.demo.models.Course;
import com.example.demo.models.ShoppingCart;
import com.example.demo.models.User;
import com.example.demo.repositories.ICourseRepository;
import com.example.demo.repositories.IShoppingCartRepository;
import com.example.demo.repositories.IUserRepository;
import com.example.demo.services.interfaces.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private IShoppingCartRepository shoppingCartRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ICourseRepository courseRepository;

    @Override
    public ShoppingCart createShoppingCart(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public List<ShoppingCart> getAllShoppingCarts() {
        try {
            return shoppingCartRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving all shopping carts", e);
        }
    }

    @Override
    public Optional<ShoppingCart> getShoppingCartById(Long id) {
        try {
            return shoppingCartRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving shopping cart by id", e);
        }
    }

    @Override
    public ShoppingCart updateShoppingCart(ShoppingCart updatedShoppingCart) {
        Optional<ShoppingCart> existingShoppingCart = shoppingCartRepository.findById(updatedShoppingCart.getId());
        if (existingShoppingCart.isPresent()) {
            ShoppingCart cart = existingShoppingCart.get();
            cart.setUser(updatedShoppingCart.getUser());
            return shoppingCartRepository.save(cart);
        }
        return null;
    }

    @Override
    public void deleteShoppingCartById(Long id) {
        shoppingCartRepository.deleteById(id);
    }

    @Override
    public void deleteAllShoppingCarts() {
        shoppingCartRepository.deleteAll();
    }

    @Override
    public double calculateTotalAmount(Long cartId) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("ShoppingCart not found"));

        List<Course> cartCourses = cart.getCourses();
        return cartCourses.stream()
                .mapToDouble(course -> course.getPrice() != null ? course.getPrice() : 0.0)
                .sum();
    }

    @Override
    public ShoppingCart addCourseToCart(Long cartId, Long userId) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("ShoppingCart not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Course> userCourses = user.getCourseList();
        cart.setCourses(new ArrayList<>(userCourses));

        return shoppingCartRepository.save(cart);
    }

}

