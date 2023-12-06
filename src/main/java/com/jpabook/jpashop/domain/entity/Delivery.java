package com.jpabook.jpashop.domain.entity;

import com.jpabook.jpashop.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name = "DELIVERY_ID")
    private Long id;
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;
    @Enumerated(value = EnumType.STRING)
    private DeliveryStatus status;

    public Delivery() {
    }

    public Delivery(Address address) {
        this.address = address;
    }

    public void settingOrder(Order order) {
        this.order = order;
    }
}
