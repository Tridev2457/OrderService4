package com.example.orderservice4.serviceImpl;

import com.example.orderservice4.configuration.ProductConfig;
import com.example.orderservice4.model.AppleProduct;
import com.example.orderservice4.model.OrangeProduct;
import com.example.orderservice4.service.IProductCostCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;


@Service
@ConditionalOnExpression("!'${offer.valid}'")
public class ProductCostCalculator implements IProductCostCalculator {
    AppleProduct appleProduct;
    OrangeProduct orangeProduct;

    @Autowired
    ProductConfig itemConfig;

    public ProductCostCalculator(ProductConfig itemConfig) {
        this.appleProduct = new AppleProduct("Apple", 0.60);;
        this.orangeProduct = new OrangeProduct("Orange", 0.25);
        this.itemConfig = itemConfig;
    }

    public Double calculateItemCost(String[] items) {

        Double totalCost = 0.0;
        for (String item : items){
            if(item.equalsIgnoreCase(appleProduct.getItemName())){
                totalCost+=appleProduct.getItemPrice();
            }else if(item.equalsIgnoreCase(orangeProduct.getItemName())){
                totalCost+=orangeProduct.getItemPrice();
            }else{
                System.out.println("We sell only oranges and apples");
            }
        }


        return totalCost;

    }

}
