package org.example.delivery_app.controller;

import org.example.delivery_app.entity.Order;
import org.example.delivery_app.repo.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public ResponseEntity<?> getOrder() {
        List<Order> all = orderRepository.findAll();
        return ResponseEntity.ok(all);
    }
}
