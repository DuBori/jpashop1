package com.jpabook.jpashop.main.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jpabook.jpashop.main.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name = "DELIVERY_ID")
    private Long id;
    /*@JsonIgnore*/
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
