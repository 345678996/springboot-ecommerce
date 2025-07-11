package com.ecommerce.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    @Size(max = 20, message = "username can max be of 20 characters")
    private String userName;

    @NotBlank
    @Size(max = 50, message = "email can max be of 50 characters")
    @Email
    private String email;

    @NotBlank
    @Size(max = 120, message = "password can max be of 120 characters")
    private String password;

    public User(@NotBlank @Size(max = 20, message = "username can max be of 20 characters") String userName,
            @NotBlank @Size(max = 50, message = "email can max be of 50 characters") @Email String email,
            @NotBlank @Size(max = 120, message = "password can max be of 120 characters") String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    
}


