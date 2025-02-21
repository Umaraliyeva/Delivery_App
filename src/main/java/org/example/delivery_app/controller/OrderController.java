package org.example.delivery_app.controller;

import org.example.delivery_app.entity.Order;
import org.example.delivery_app.enums.Status;
import org.example.delivery_app.repo.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrder() {
        List<Order> all = orderRepository.findAll();
        return ResponseEntity.ok(all);
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<?> updateStatus(@RequestParam Integer id) {
        Order order = orderRepository.findById(id).orElse(null);

        if (order == null) {
            return ResponseEntity.badRequest().body("Order topilmadi");
        }

        // Statusni o'zgartirish
        Status status = order.getStatus();
        if (status == Status.NEW) {
            order.setStatus(Status.IN_PROGRESS);
        } else if (status == Status.IN_PROGRESS) {
            order.setStatus(Status.DELIVERING);
        } else if (status == Status.DELIVERING) {
            order.setStatus(Status.COMPLETED);
        } else {
            return ResponseEntity.badRequest().body("Order statusini o'zgartirib bo‘lmaydi");
        }

        // Yangilangan ma'lumotni saqlash
        orderRepository.save(order);

        return ResponseEntity.ok(order);
    }
}
