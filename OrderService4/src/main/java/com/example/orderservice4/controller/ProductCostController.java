package com.example.orderservice4.controller;

import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Queue;


@RestController
public class ProductCostController {

    @Autowired
    private Queue queue;

    @Autowired
    JmsTemplate jmsTemplate;

    @PostMapping("/v1/api/item/cost")
    public String calculateItemCost(@NotNull @RequestBody String[] itemsList) {
        jmsTemplate.convertAndSend(queue, itemsList);

        return "Order received: Total Cost would be sent";
    }

}
