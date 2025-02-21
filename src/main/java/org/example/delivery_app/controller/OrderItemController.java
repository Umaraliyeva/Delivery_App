package org.example.delivery_app.controller;

import org.example.delivery_app.entity.OrderItem;
import org.example.delivery_app.repo.OrderItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/orderItem")
public class OrderItemController {

    private final OrderItemRepository orderItemRepository;

    public OrderItemController(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @PostMapping("/{selectedOrderId}")
    public List<OrderItem> getOrderItem(@PathVariable Integer selectedOrderId) {
        return orderItemRepository.findByOrder_Id((selectedOrderId));
    }
}
