package com.ECom.ecommerce.dtos.order.response;

import java.util.List;

import com.ECom.ecommerce.dtos.address.response.AddressResponse;
import com.ECom.ecommerce.dtos.product.response.ProductResponse;
import com.ECom.ecommerce.dtos.user.response.UserResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderListResponse {
 
    private List<OrderResponse> orders;
    private List<AddressResponse> addresses;
    private List<UserResponse> users;
    private List<ProductResponse> products;

}
