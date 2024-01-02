package com.jpabook.jpashop.api.controller;

import com.jpabook.jpashop.api.dto.response.*;
import com.jpabook.jpashop.main.domain.dto.request.OrderSearch;
import com.jpabook.jpashop.main.domain.entity.Order;
import com.jpabook.jpashop.main.domain.entity.OrderItem;
import com.jpabook.jpashop.main.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderService orderService;
    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> orders = orderService.findOrders(new OrderSearch());
        for (Order order : orders) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream()
                    .forEach(it -> it.getItem().getName());
        }
        return orders;
    }

    @GetMapping("/api/v2/orders")
    public ResponseEntity ordersV2() {
        List<Order> orders = orderService.findOrders(new OrderSearch());
        List<OrderDto> collect = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());
        return new ResponseEntity(collect);
    }

    @GetMapping("/api/v3/orders")
    public ResponseEntity ordersV3() {
        List<Order> orders = orderService.withItem();
        List<OrderDto> collect = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());
        return new ResponseEntity(collect);
    }

    @GetMapping("/api/v3.1/orders")
    public ResponseEntity ordersV3_1(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                     @RequestParam(value = "limit", defaultValue = "0") int limit) {
        List<Order> orders = orderService.findAllWithMemberDeliveryPaging(offset, limit);
        List<OrderDto> collect = orders.stream()
                .map(it -> new OrderDto(it))
                .collect(Collectors.toList());
        return new ResponseEntity(collect);
    }

    @GetMapping("/api/v4/orders")
    public ResponseEntity ordersV4() {
        return new ResponseEntity(orderService.findOrderQueryDtos());
    }

    @GetMapping("/api/v5/orders")
    public ResponseEntity ordersV5() {
        return new ResponseEntity(orderService.findOrderAllByDto_optimization());
    }

    @GetMapping("/api/v6/orders")
    public ResponseEntity ordersV6() {
        List<OrderFlatDto> orderAllByDtoFlat = orderService.findOrderAllByDto_flat();
        List<OrderQueryDto> collect = orderAllByDtoFlat.stream()
                .collect(Collectors.groupingBy(it -> new OrderQueryDto(it.getOrderId(), it.getName(), it.getOrderDate(), it.getOrderStatus(), it.getAddress()),
                        Collectors.mapping(o -> new OrderItemQueryDto(o.getOrderId(), o.getItemName(), o.getOrderPrice(), o.getCount()), Collectors.toList())
                )).entrySet().stream()
                .map(it -> new OrderQueryDto(it.getKey().getOrderId(),
                        it.getKey().getName(), it.getKey().getOrderDate(), it.getKey().getOrderStatus(),
                        it.getKey().getAddress()))
                .sorted(Comparator.comparing(OrderQueryDto::getOrderId))
                .collect(Collectors.toList());
        return new ResponseEntity(collect);
    }

}
