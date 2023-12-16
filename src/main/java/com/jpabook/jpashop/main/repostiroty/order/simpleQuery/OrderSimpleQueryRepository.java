package com.jpabook.jpashop.main.repostiroty.order.simpleQuery;

import com.jpabook.jpashop.api.dto.response.SimpleOrderQueryDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {
    private final EntityManager em;
    public List<SimpleOrderQueryDto> findAllOrderDto() {
        return em.createQuery(
                "select new com.jpabook.jpashop.main.repostiroty.order.simpleQuery.OrderSimpleQueryRepository(o.id, m.name, o.orderDate, o.status, d.address) from Order o " +
                        "join o.member m " +
                        "join o.delivery d", SimpleOrderQueryDto.class
        ).getResultList();
    }
}
