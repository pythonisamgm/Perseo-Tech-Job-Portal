package com.example.demo.dto.user;

import com.example.demo.models.ERole;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDTO {

    private Long userId;
    private String username;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private ERole role;
    private String oauthProvider;
    private String oauthToken;
    private List<Long> experienceIds;
    private List<Long> courseIds;
    private Long shoppingCartId;
}
