package com.example.orderservice4.stockstate;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Getter
@Setter
public class ProductQuantityState {

    @Value("#{${simple.map}}")
    HashMap<String, Integer> itemQuantityMap = new HashMap<>();



}
