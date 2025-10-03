package com.ECom.ecommerce.dtos.order.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateOrderStatusRequest {

    private Long orderId;     
    private String orderStatus;
}
