package com.ecommerce.project.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "addresses")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotBlank
    @Size(min = 5, message = "Street name must be atleast 5 characters")
    private String street;

    @NotBlank
    @Size(min = 5, message = "Bulding name must be atleast 5 characters")
    private String buildingName;

    @NotBlank
    @Size(min = 5, message = "City name must be atleast 5 characters")
    private String cityName;

    @NotBlank
    @Size(min = 2, message = "City name must be atleast 2 characters")
    private String stateName;

    @NotBlank
    @Size(min = 2, message = "Country name must be atleast 2 characters")
    private String country;

    @NotBlank
    @Size(min = 6, message = "pincode must be atleast 6 characters")
    private String pincode;

    @ToString.Exclude
    @ManyToMany(mappedBy = "addresses")
    private List<User> users = new ArrayList<>();

    
    public Address(@NotBlank @Size(min = 5, message = "Street name must be atleast 5 characters") String street,
            @NotBlank @Size(min = 5, message = "Bulding name must be atleast 5 characters") String buildingName,
            @NotBlank @Size(min = 5, message = "City name must be atleast 5 characters") String cityName,
            @NotBlank @Size(min = 2, message = "City name must be atleast 2 characters") String stateName,
            @NotBlank @Size(min = 2, message = "Country name must be atleast 2 characters") String country,
            @NotBlank @Size(min = 6, message = "pincode must be atleast 6 characters") String pincode) {
        this.street = street;
        this.buildingName = buildingName;
        this.cityName = cityName;
        this.stateName = stateName;
        this.country = country;
        this.pincode = pincode;
    }

    
}
