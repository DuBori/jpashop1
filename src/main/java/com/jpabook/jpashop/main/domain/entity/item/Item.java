package com.jpabook.jpashop.main.domain.entity.item;

import com.jpabook.jpashop.main.domain.entity.Category;
import com.jpabook.jpashop.main.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;
    @ManyToMany(mappedBy = "items")
    private List<Category> categoryList = new ArrayList<>();

    // 공통 비즈니스 로직


    public Item() {
    }

    public Item(Long id, String name, int price, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    /**
     *  재고 추가
     * */
    public void addStock(int quantity) {
        stockQuantity += quantity;
    }
    /**
     * 재고 감소
     * */
    public void removeStock(int quantity) {
        if (stockQuantity - quantity < 0) {
            throw new NotEnoughStockException("재고 보다 많은 수량의 취소가 발생");
        }
        stockQuantity -= quantity;
    }


    public void updateCommonValues(Book param) {
        this.price = param.getPrice();
        this.name = param.getName();
        this.stockQuantity = param.getStockQuantity();
    }

    @PreUpdate
    protected void onUpdate() {

    }
}
