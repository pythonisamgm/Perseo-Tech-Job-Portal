package com.example.demo.dto.shoppingCart;

import lombok.*;

import java.util.List;

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

}
