package com.example.demo.services;

import com.example.demo.models.Course;
import com.example.demo.models.ShoppingCart;
import com.example.demo.models.User;
import com.example.demo.repositories.ICourseRepository;
import com.example.demo.repositories.IShoppingCartRepository;
import com.example.demo.repositories.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShoppingCartServiceImplTest {

    @Mock
    private IShoppingCartRepository shoppingCartRepository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    private ShoppingCart cart;
    private User user;
    private Course course1;
    private Course course2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup initial test data
        user = new User();
        user.setUserId(1L);
        user.setUsername("john_doe");

        course1 = new Course();
        course1.setId(1L);
        course1.setTitle("Course 1");

        course2 = new Course();
        course2.setId(2L);
        course2.setTitle("Course 2");

        cart = new ShoppingCart();
        cart.setId(1L);
        cart.setUser(user);
        cart.setCourses(new ArrayList<>());
    }

    @Test
    void testCreateShoppingCart() {
        when(shoppingCartRepository.save(cart)).thenReturn(cart);

        ShoppingCart result = shoppingCartService.createShoppingCart(cart);

        assertEquals(cart.getId(), result.getId());
        assertEquals(cart.getUser(), result.getUser());
        verify(shoppingCartRepository, times(1)).save(cart);
    }

    @Test
    void testGetAllShoppingCarts() {
        ShoppingCart cart2 = new ShoppingCart();
        cart2.setId(2L);
        cart2.setUser(user);

        List<ShoppingCart> carts = Arrays.asList(cart, cart2);

        when(shoppingCartRepository.findAll()).thenReturn(carts);

        List<ShoppingCart> result = shoppingCartService.getAllShoppingCarts();

        assertEquals(2, result.size());
        verify(shoppingCartRepository, times(1)).findAll();
    }

    @Test
    void testGetShoppingCartById() {
        when(shoppingCartRepository.findById(1L)).thenReturn(Optional.of(cart));

        Optional<ShoppingCart> result = shoppingCartService.getShoppingCartById(1L);

        assertTrue(result.isPresent());
        assertEquals(cart.getId(), result.get().getId());
        verify(shoppingCartRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateShoppingCart() {
        ShoppingCart updatedCart = new ShoppingCart();
        updatedCart.setId(1L);
        updatedCart.setUser(user);
        updatedCart.setCourses(Arrays.asList(course1, course2));

        when(shoppingCartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(shoppingCartRepository.save(cart)).thenReturn(updatedCart);

        ShoppingCart result = shoppingCartService.updateShoppingCart(updatedCart);

        assertNotNull(result);
        assertEquals(2, result.getCourses().size());
        verify(shoppingCartRepository, times(1)).findById(1L);
        verify(shoppingCartRepository, times(1)).save(cart);
    }

    @Test
    void testDeleteShoppingCartById() {
        doNothing().when(shoppingCartRepository).deleteById(1L);

        shoppingCartService.deleteShoppingCartById(1L);

        verify(shoppingCartRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAllShoppingCarts() {
        doNothing().when(shoppingCartRepository).deleteAll();

        shoppingCartService.deleteAllShoppingCarts();

        verify(shoppingCartRepository, times(1)).deleteAll();
    }

    @Test
    void testAddCourseToCart() {
        when(shoppingCartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course1));

        ShoppingCart result = shoppingCartService.addCourseToCart(1L, 1L);

        assertNotNull(result);
        assertEquals(1, result.getCourses().size());
        assertEquals(course1, result.getCourses().get(0));

        verify(shoppingCartRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).findById(1L);
        verify(shoppingCartRepository, times(1)).save(cart);  // Ensure cart is saved
    }

    @Test
    void testRemoveCourseFromCart() {
        cart.getCourses().add(course1);

        when(shoppingCartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course1));

        ShoppingCart result = shoppingCartService.removeCourseFromCart(1L, 1L);

        assertNotNull(result);
        assertEquals(0, result.getCourses().size());

        verify(shoppingCartRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).findById(1L);
        verify(shoppingCartRepository, times(1)).save(cart);  // Ensure cart is saved
    }

}
