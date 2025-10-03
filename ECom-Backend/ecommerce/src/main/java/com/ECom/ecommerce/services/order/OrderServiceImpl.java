package com.ECom.ecommerce.services.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ECom.ecommerce.dtos.address.response.AddressResponse;
import com.ECom.ecommerce.dtos.order.request.AddOrderItemRequest;
import com.ECom.ecommerce.dtos.order.request.CreateOrderRequest;
import com.ECom.ecommerce.dtos.order.request.UpdateOrderItemRequest;
import com.ECom.ecommerce.dtos.order.response.OrderItemResponse;
import com.ECom.ecommerce.dtos.order.response.OrderListResponse;
import com.ECom.ecommerce.dtos.order.response.OrderResponse;
import com.ECom.ecommerce.dtos.product.response.ProductResponse;
import com.ECom.ecommerce.dtos.user.response.UserResponse;
import com.ECom.ecommerce.entities.Order;
import com.ECom.ecommerce.entities.OrderItem;
import com.ECom.ecommerce.entities.OrderStatus;
import com.ECom.ecommerce.entities.Product;
import com.ECom.ecommerce.entities.Role;
import com.ECom.ecommerce.entities.User;
import com.ECom.ecommerce.entities.Address;
import com.ECom.ecommerce.repositories.OrderRepo;
import com.ECom.ecommerce.repositories.ProductRepo;
import com.ECom.ecommerce.repositories.UserRepo;
import com.ECom.ecommerce.repositories.AddressRepo;
import com.ECom.ecommerce.repositories.OrderItemRepo;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Autowired
    private ProductRepo productRepo;

    @Override
    public OrderResponse createOrder(CreateOrderRequest createOrderRequest) {
        // 
        User user = userRepo.findById(createOrderRequest.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 
        Address address = addressRepo.findById(createOrderRequest.getAddress().getId())
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));

        // 
        Order order = new Order();
        order.setId(createOrderRequest.getId());
        order.setUser(user);
        order.setAddress(address);
        order.setTotalAmount(createOrderRequest.getTotalAmount());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(createOrderRequest.getOrderDate());
        order.setDeliveryDate(createOrderRequest.getDeliveryDate());

        Order savedOrder = orderRepo.save(order);

        return mapOrderToResponse(savedOrder);
    }

    @Override
    public Optional<OrderResponse> findOrderById(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        return Optional.of(mapOrderToResponse(order));
    }

    @Override
    public void updateOrderStatus(Long orderId, String status, String ROLE) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));

        OrderStatus orderStatus = OrderStatus.valueOf(status);

        Role role = Role.valueOf(ROLE.toUpperCase());

        // Role-based restrictions
        if (role == Role.ROLE_ADMIN) {
            order.setOrderStatus(orderStatus);

        } else if (role == Role.DELIVERY) {
            if (orderStatus == OrderStatus.OUT_FOR_DELIVERY || orderStatus == OrderStatus.DELIVERED) {
                order.setOrderStatus(orderStatus);
            } else {
                throw new IllegalArgumentException("Delivery person not allowed to set status: " + orderStatus);
            }

        } else {
            throw new SecurityException("Unauthorized role: " + role);
        }

        orderRepo.save(order);
    }

    @Override
    public boolean deleteOrder(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));

        orderRepo.delete(order);

        return true;
    }

    @Override
    public List<OrderListResponse> findAll() {
        List<Order> orders = orderRepo.findAll();
        return orders.stream()
                .map(this::mapOrderListToResponseList)
                .toList();
    }

    @Override
    public List<OrderListResponse> findByUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<Order> orders = orderRepo.findByUser(user);
        return orders.stream()
                .map(this::mapOrderListToResponseList)
                .toList();
    }

    @Override
    public List<OrderListResponse> findByStatus(String status) {
        List<Order> orders = orderRepo.findByOrderStatus(OrderStatus.valueOf(status.toUpperCase()));
        return orders.stream()
                .map(this::mapOrderListToResponseList)
                .toList();
    }

    @Override
    public List<OrderListResponse> findByUserAndStatus(Long userId, String status) {
        List<Order> orders = orderRepo.findByUserIdAndOrderStatus(userId, OrderStatus.valueOf(status.toUpperCase()));
        return orders.stream()
                .map(this::mapOrderListToResponseList)
                .toList();
    }


    private OrderListResponse mapOrderListToResponseList(Order order) {
        OrderListResponse response = new OrderListResponse();
        response.setOrders(List.of(mapOrderToResponse(order)));
        response.setAddresses(List.of(mapAddressToResponse(order.getAddress())));
        response.setUsers(List.of(mapUserToResponse(order.getUser())));
        response.setProducts(order.getOrderItems().stream()
                .map(orderItem -> mapProductToResponse(orderItem.getProduct()))
                .toList());

        return response;
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        
        Order order = orderRepo.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));
    
        return mapOrderToResponse(order);
    }

    @Override
    public OrderResponse addOrderItem(Long orderId, AddOrderItemRequest addOrderItemRequest) {
       

        Order order = orderRepo.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));

        Product product = productRepo.findById(addOrderItemRequest.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + addOrderItemRequest.getProductId()));

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(addOrderItemRequest.getQuantity());
        orderItem.setPrice(addOrderItemRequest.getPrice());

        order.getOrderItems().add(orderItem); 
        
        order.setTotalAmount(order.getOrderItems().stream()
            .map(OrderItem::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add)); 

        orderRepo.save(order);
        return mapOrderToResponse(order);
    }

    @Override
    public OrderResponse updateOrderItem(Long orderId, UpdateOrderItemRequest updateOrderItemRequest) {
        
        Order order = orderRepo.findById(orderId)
        .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));

    // Find the order item by product or item ID
        OrderItem orderItem = order.getOrderItems().stream()
        .filter(item -> item.getProduct().getProductId().equals(updateOrderItemRequest.getProductId()))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Order item not found for productId: " 
                            + updateOrderItemRequest.getProductId()));

    // Update fields
    if (updateOrderItemRequest.getQuantity() != 0) {
        orderItem.setQuantity(updateOrderItemRequest.getQuantity());
    }
    if (updateOrderItemRequest.getPrice() != null) {
        orderItem.setPrice(updateOrderItemRequest.getPrice());
    }

    // Recalculate total
    order.setTotalAmount(order.getOrderItems().stream()
        .map(OrderItem::getTotalPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add));


        Order savedOrder = orderRepo.save(order);
        return mapOrderToResponse(savedOrder);
    }

        @Override
        public OrderResponse removeOrderItem(Long orderId, Long orderItemId) {
        
        Order order = orderRepo.findById(orderId)
        .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));

    
        OrderItem orderItem = order.getOrderItems().stream()
            .filter(item -> item.getOrderItemId().equals(orderItemId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Order item does not belong to the given order"));

        order.getOrderItems().remove(orderItem);
        
        Order savedOrder = orderRepo.save(order);

        return mapOrderToResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(Long userId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Order> orders = orderRepo.findByUser(user);

        return orders.stream()
            .map(this::mapOrderToResponse)
            .toList();
    }


    private OrderResponse mapOrderToResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setUserId(order.getUser().getId());
        response.setAddressId(order.getAddress().getId());
        response.setTotalAmount(order.getTotalAmount());
        response.setOrderStatus(order.getOrderStatus());
        response.setOrderDate(order.getOrderDate());
        response.setDeliveryDate(order.getDeliveryDate());

        response.setOrderItems(order.getOrderItems().stream().map(orderItem -> {

        OrderItemResponse itemResponse = new OrderItemResponse();

        itemResponse.setOrderId(orderItem.getOrderItemId());
        itemResponse.setProductId(orderItem.getProduct().getProductId());
        itemResponse.setQuantity(orderItem.getQuantity());
        itemResponse.setPrice(orderItem.getPrice());
        
        return itemResponse;

    }).toList());


        return response;
    }

    private AddressResponse mapAddressToResponse(Address address) {
        AddressResponse response = new AddressResponse();
        response.setId(address.getId());
        response.setStreet(address.getStreet());
        response.setUserId(address.getUser() != null ? address.getUser().getId() : null);
        response.setCity(address.getCity());
        response.setState(address.getState());
        response.setCountry(address.getCountry());
        response.setPincode(address.getPincode());
        response.setAddressType(address.getAddressType());

        return response;
    }
    private UserResponse mapUserToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setDateOfBirth(user.getDateOfBirth());
        response.setRole(user.getRole().toString());
        response.setActive(user.isActive());
        response.setEmailVerified(user.isEmailVerified());
        response.setPhoneVerified(user.isPhoneVerified());

        return response;
    }

    private ProductResponse mapProductToResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setProductId(product.getProductId());
        response.setName(product.getName());
        response.setBrandId(product.getBrand() != null ?product.getBrand().getBrandId() : null);
        response.setCategoryId(product.getCategory() != null ?product.getCategory().getCategoryId() : null);
        response.setItemType(product.getItemType() != null ?product.getItemType().getItemTypeId() : null);
        response.setSpecification(product.getSpecification());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());

        return response;
    }

    @Override
    public OrderResponse cancelOrder(Long orderId, Long userId) {
        Order order = orderRepo.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));

    // Check if the order belongs to the user (if non-admin)
    if (!order.getUser().getId().equals(userId)) {
        throw new SecurityException("You cannot cancel someone else's order");
    }

    // Check current status (e.g., cannot cancel shipped/delivered orders)
    if (order.getOrderStatus() == OrderStatus.SHIPPED || order.getOrderStatus() == OrderStatus.DELIVERED) {
        throw new IllegalStateException("Order cannot be canceled at this stage");
    }

    order.setOrderStatus(OrderStatus.CANCELLED);
    Order savedOrder = orderRepo.save(order);
    return mapOrderToResponse(savedOrder);
    }

    @Override
    public OrderResponse completeOrder(Long orderId, Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'completeOrder'");
    }

}
