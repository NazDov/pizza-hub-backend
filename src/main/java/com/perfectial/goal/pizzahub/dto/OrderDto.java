package com.perfectial.goal.pizzahub.dto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDto {
    @NotNull
    @NotBlank
    private String firstName;
    @NotNull
    @NotBlank
    private String lastName;
    private String email;
    @NotNull
    @NotBlank
    private String phone;
    @NotNull
    @NotBlank
    private String address;
    @NotNull
    @NotBlank
    private String city;
    private String zip;
    @NotEmpty
    private List<OrderItemDto> orderItems;
}
