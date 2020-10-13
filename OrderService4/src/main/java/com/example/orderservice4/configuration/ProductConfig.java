package com.example.orderservice4.configuration;

import com.example.orderservice4.model.AppleProduct;
import com.example.orderservice4.model.OrangeProduct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class ProductConfig {

    @Value("${offer.valid}")
    public boolean isOfferValid;

    @Bean
    public AppleProduct appleItem(){
        return new AppleProduct("Apple", 0.60);
    }

    @Bean
    public OrangeProduct orangeItem(){
        return new OrangeProduct("Orange", 0.25);
    }
}


