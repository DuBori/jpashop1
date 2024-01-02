package com.jpabook.jpashop.main.repostiroty.order.orderQuery;

import com.jpabook.jpashop.api.dto.response.OrderFlatDto;
import com.jpabook.jpashop.api.dto.response.OrderItemQueryDto;
import com.jpabook.jpashop.api.dto.response.OrderQueryDto;
import com.jpabook.jpashop.main.domain.entity.OrderItem;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderQueryRepository {
    private final EntityManager entityManager;

    public List<OrderQueryDto> findOrderQueryDtos() {
        List<OrderQueryDto> orders = findOrderQuery();
        orders.forEach(it ->it.setItems(findOrderItems(it.getOrderId())));
        return orders;
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return entityManager.createQuery(
                "select new com.jpabook.jpashop.api.dto.response.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) " +
                        "from OrderItem oi " +
                        "join oi.item i " +
                        "where oi.order.id = :orderId", OrderItemQueryDto.class
        ).setParameter("orderId", orderId)
        .getResultList();
    }

    private List<OrderQueryDto> findOrderQuery() {
        return entityManager.createQuery(
                "select new com.jpabook.jpashop.api.dto.response.OrderQueryDto(o.id, o.member.name, o.orderDate, o.status, o.delivery.address) from Order o " +
                        "join o.member m " +
                        "join o.delivery d ", OrderQueryDto.class
        ).getResultList();

    }

    public List<OrderQueryDto> findOrderAllByDto_optimization() {
        List<OrderQueryDto> result = findOrderQuery();

        List<Long> orderIds = getOrderIds(result);
        List<OrderItemQueryDto> orderItems = entityManager.createQuery(
                        "select new com.jpabook.jpashop.api.dto.response.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) " +
                                "from OrderItem oi " +
                                "join oi.item i " +
                                "where oi.order.id in :orderIds", OrderItemQueryDto.class
                ).setParameter("orderIds", orderIds)
                .getResultList();

        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(orderItemQueryDto -> orderItemQueryDto.getOrderId()));

        result.forEach(it -> it.setItems(orderItemMap.get(it.getOrderId())));

        return result;
    }

    private List<Long> getOrderIds(List<OrderQueryDto> result) {
        return result.stream()
                .map(it -> it.getOrderId())
                .collect(Collectors.toList());
    }

    public List<OrderFlatDto> findOrderAllByDto_flat() {
        return entityManager.createQuery(
                "select new com.jpabook.jpashop.api.dto.response.OrderFlatDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count) " +
                        "from Order o " +
                        "join o.member m " +
                        "join o.delivery d " +
                        "join o.orderItems oi " +
                        "join oi.item i ", OrderFlatDto.class).getResultList();

    }
}
