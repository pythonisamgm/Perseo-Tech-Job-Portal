package com.example.demo.dto.shoppingCart;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ShoppingCartDTO {
    private Long id;
    private Long userId;
    private List<Long> courseIds;
    private double totalAmount;

}
