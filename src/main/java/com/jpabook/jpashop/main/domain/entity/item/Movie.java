package com.jpabook.jpashop.main.domain.entity.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("C")
public class Movie extends Item {
    private String director;
    private String actor;

}
