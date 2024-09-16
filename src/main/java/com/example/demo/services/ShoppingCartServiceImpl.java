
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

    public ShoppingCart createShoppingCart(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    public List<ShoppingCart> getAllShoppingCarts() {
        try {
            return shoppingCartRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving all shopping carts", e);
        }
    }

    public Optional<ShoppingCart> getShoppingCartById(Long id) {
        try {
            return shoppingCartRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving shopping cart by id", e);
        }
    }

    public ShoppingCart updateShoppingCart(ShoppingCart updatedShoppingCart) {
        Optional<ShoppingCart> existingShoppingCart = shoppingCartRepository.findById(updatedShoppingCart.getId());
        if (existingShoppingCart.isPresent()) {
            ShoppingCart cart = existingShoppingCart.get();
            cart.setUser(updatedShoppingCart.getUser());
            return shoppingCartRepository.save(cart);
        }
        return null;
    }

    public void deleteShoppingCartById(Long id) {
        shoppingCartRepository.deleteById(id);
    }

    public void deleteAllShoppingCarts() {
        shoppingCartRepository.deleteAll();
    }
    public double calculateTotalAmount(List<Course> courses) {
        return courses.stream()
                .mapToDouble(Course::getPrice)
                .sum();
    }

    public ShoppingCart addCourseToCart(Long cartId, Long userId) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("ShoppingCart not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Course> userCourses = user.getCourseList();
        List<Course> cartCourses = new ArrayList<>(userCourses);
        cart.setCourses(cartCourses);

        double totalAmount = calculateTotalAmount(cartCourses);
        cart.setTotalAmount(totalAmount);

        ShoppingCart updatedCart = shoppingCartRepository.save(cart);

        return updatedCart;
    }

}

