package com.jpabook.jpashop.main.controller;

import com.jpabook.jpashop.main.domain.dto.request.OrderForm;
import com.jpabook.jpashop.main.domain.dto.request.OrderSearch;
import com.jpabook.jpashop.main.domain.entity.Member;
import com.jpabook.jpashop.main.domain.entity.item.Item;
import com.jpabook.jpashop.main.service.ItemService;
import com.jpabook.jpashop.main.service.MemberService;
import com.jpabook.jpashop.main.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();
        model.addAttribute("members", members);
        model.addAttribute("items", items);
        return "order/orderForm";
    }

    @PostMapping("/order")
    public String createForm2(OrderForm orderForm) {
        orderService.orderStart(orderForm.getMemberId(), orderForm.getItemId(), orderForm.getCount());
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(OrderSearch orderSearch, Model model) {
        log.info("{}", orderSearch);
        model.addAttribute("orderSearch", orderSearch);
        model.addAttribute("orders", orderService.findOrders(orderSearch));
        return "order/orderList";
    }
    @PostMapping("/orders/{orderId}/cancel")
    public String cancel(@PathVariable("orderId") Long id) {
        orderService.cancelOrder(id);
        return "redirect:/orders";
    }
}
