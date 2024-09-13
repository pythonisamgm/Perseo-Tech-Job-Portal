package com.example.demo.controllers;

import com.example.demo.dto.shoppingCart.ShoppingCartConverter;
import com.example.demo.dto.shoppingCart.ShoppingCartDTO;
import com.example.demo.models.ShoppingCart;
import com.example.demo.services.ShoppingCartServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/shoppingCart")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ShoppingCartController {

    @Autowired
    private final ShoppingCartServiceImpl shoppingCartService;
    @Autowired
    private final ShoppingCartConverter shoppingCartConverter;


    @PostMapping("/create")
    public ResponseEntity<ShoppingCartDTO> createShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = shoppingCartConverter.dtoToShoppingCart(shoppingCartDTO);
        ShoppingCart createdCart = shoppingCartService.createShoppingCart(shoppingCart);
        ShoppingCartDTO createdCartDTO = shoppingCartConverter.shoppingCartToDto(createdCart);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCartDTO);
    }


    @GetMapping("/all")
    public ResponseEntity<List<ShoppingCartDTO>> getAllShoppingCarts() {
        List<ShoppingCart> shoppingCarts = shoppingCartService.getAllShoppingCarts();
        List<ShoppingCartDTO> shoppingCartDTOs = shoppingCarts.stream()
                .map(shoppingCartConverter::shoppingCartToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(shoppingCartDTOs);
    }


    @GetMapping("/cart/{id}")
    public ResponseEntity<ShoppingCartDTO> getShoppingCartById(@PathVariable Long id) {
        Optional<ShoppingCart> shoppingCartOpt = shoppingCartService.getShoppingCartById(id);
        if (shoppingCartOpt.isPresent()) {
            ShoppingCartDTO shoppingCartDTO = shoppingCartConverter.shoppingCartToDto(shoppingCartOpt.get());
            return ResponseEntity.ok(shoppingCartDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ShoppingCartDTO> updateShoppingCart(@PathVariable Long id, @RequestBody ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = shoppingCartConverter.dtoToShoppingCart(shoppingCartDTO);
        shoppingCart.setId(id);
        ShoppingCart updatedShoppingCart = shoppingCartService.updateShoppingCart(shoppingCart);
        if (updatedShoppingCart != null) {
            ShoppingCartDTO updatedShoppingCartDTO = shoppingCartConverter.shoppingCartToDto(updatedShoppingCart);
            return ResponseEntity.ok(updatedShoppingCartDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteShoppingCartById(@PathVariable Long id) {
        shoppingCartService.deleteShoppingCartById(id);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAllShoppingCarts() {
        shoppingCartService.deleteAllShoppingCarts();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{cartId}/courses/{courseId}")
    public ResponseEntity<ShoppingCartDTO> addCourseToCart(@PathVariable Long cartId, @PathVariable Long courseId) {
        ShoppingCart updatedCart = shoppingCartService.addCourseToCart(cartId, courseId);
        ShoppingCartDTO updatedCartDTO = shoppingCartConverter.shoppingCartToDto(updatedCart);
        return ResponseEntity.ok(updatedCartDTO);
    }

    @DeleteMapping("/{cartId}/courses/{courseId}")
    public ResponseEntity<ShoppingCartDTO> removeCourseFromCart(@PathVariable Long cartId, @PathVariable Long courseId) {
        ShoppingCart updatedCart = shoppingCartService.removeCourseFromCart(cartId, courseId);
        ShoppingCartDTO updatedCartDTO = shoppingCartConverter.shoppingCartToDto(updatedCart);
        return ResponseEntity.ok(updatedCartDTO);
    }
}

