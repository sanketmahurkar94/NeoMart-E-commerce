package com.piyush.Flicksy.service;

import com.piyush.Flicksy.dto.OrderDTO;
import com.piyush.Flicksy.dto.OrderItemDTO;
import com.piyush.Flicksy.model.OrderItem;
import com.piyush.Flicksy.model.Orders;
import com.piyush.Flicksy.model.Product;
import com.piyush.Flicksy.model.User;
import com.piyush.Flicksy.repository.OrderRepository;
import com.piyush.Flicksy.repository.ProductRepository;
import com.piyush.Flicksy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    public OrderDTO placeOrder(Long userId, Map<Long, Integer> productQuantities, double totalAmount) {

        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User Not Found"));

        Orders orders = new Orders();
        orders.setUser(user);
        orders.setOrderDate(new Date());
        orders.setStatus("Pending");
        orders.setTotalAmount(totalAmount);


        List<OrderItem> orderItems = new ArrayList<>();
        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();

        for(Map.Entry<Long, Integer> entry:productQuantities.entrySet())
        {
            Product product= productRepository.findById(entry.getKey())
                    .orElseThrow(()->new RuntimeException("Product Not found"));

            OrderItem orderItem=new OrderItem();
            orderItem.setOrders(orders);
            orderItem.setProduct(product);
            orderItem.setQuantity(entry.getValue());
            orderItems.add(orderItem);

            orderItemDTOS.add(new OrderItemDTO(product.getName(),product.getPrice(),entry.getValue()));
        }

        orders.setOrderItems(orderItems);
        Orders saveOrder = orderRepository.save(orders);
        return new OrderDTO(saveOrder.getId(), saveOrder.getTotalAmount()
                ,saveOrder.getStatus(),saveOrder.getOrderDate(),orderItemDTOS);
    }


    public List<OrderDTO> getAllOrders() {

        List<Orders> orders = orderRepository.findAllOrdersWithUsers();
        return orders.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private OrderDTO convertToDTO(Orders orders) {
        List<OrderItemDTO> OrderItems = orders.getOrderItems().stream()
                .map(item -> new OrderItemDTO(
                        item.getProduct().getName(),
                        item.getProduct().getPrice(),
                        item.getQuantity())).collect(Collectors.toList());
        return new OrderDTO(
                orders.getId(),
                orders.getTotalAmount(),
                orders.getStatus(),
                orders.getOrderDate(),
                orders.getUser()!=null ? orders.getUser().getName() : "Unknown",
                orders.getUser()!=null ? orders.getUser().getEmail() : "Unknown",
                OrderItems
        );
    }


    public List<OrderDTO> getOrderByUser(Long userId) {
        Optional<User> userOp = userRepository.findById(userId);
        if(userOp.isEmpty())
        {
            throw  new RuntimeException("user not found");
        }
        User user= userOp.get();
        List<Orders> ordersList = orderRepository.findByUser(user);
        return ordersList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
