package com.perfectial.goal.pizzahub.model;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;

    @Override
    public String toString() {
        return "Product{"
                + "id=" + id
                + ", name=" + name
                + ", description=" + description
                + ", price=" + price
                + '}';
    }
}
