package com.jpabook.jpashop.api.controller;

import com.jpabook.jpashop.api.dto.response.ResponseEntity;
import com.jpabook.jpashop.api.dto.response.SimpleOrderDto;
import com.jpabook.jpashop.main.domain.dto.request.OrderSearch;
import com.jpabook.jpashop.main.domain.entity.Order;
import com.jpabook.jpashop.main.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderService orderService;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> orders = orderService.findOrders(new OrderSearch());
        for (Order order : orders) {
            order.getMember().getName();
            order.getDelivery().getAddress();

        }
        return orders;
    }

    @GetMapping("/api/v2/simple-orders")
    public ResponseEntity ordersV2() {
        List<Order> orders = orderService.findOrders(new OrderSearch());
        List<SimpleOrderDto> collect = orders.stream()
                .map(it -> new SimpleOrderDto(it))
                .collect(Collectors.toList());
        return new ResponseEntity(collect);
    }

    @GetMapping("/api/v3/simple-orders")
    public ResponseEntity ordersV3() {
        List<Order> orders = orderService.findAllWithMemberDelivery();
        List<SimpleOrderDto> collect = orders.stream()
                .map(it -> new SimpleOrderDto(it))
                .collect(Collectors.toList());
        return new ResponseEntity(collect);
    }
}
