package com.jpabook.jpashop.main.repostiroty.order.orderQuery;

import com.jpabook.jpashop.api.dto.response.OrderItemQueryDto;
import com.jpabook.jpashop.api.dto.response.OrderQueryDto;
import com.jpabook.jpashop.main.domain.entity.OrderItem;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
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
}
