package com.example.orderservice4.serviceImpl;

import com.example.orderservice4.configuration.ProductConfig;
import com.example.orderservice4.model.AppleProduct;
import com.example.orderservice4.model.OrangeProduct;
import com.example.orderservice4.model.Product;
import com.example.orderservice4.service.IProductCostCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@ConditionalOnExpression("${offer.valid}")
public class OfferProductCostCalculator implements IProductCostCalculator {

    List<Product> itemsList = new ArrayList<>();
    AppleProduct appleProduct;
    OrangeProduct orangeProduct;

    @Autowired
    ProductConfig itemConfig;

    public OfferProductCostCalculator(ProductConfig itemConfig) {
        this.appleProduct = new AppleProduct("Apple", 0.60);;
        this.orangeProduct = new OrangeProduct("Orange", 0.25);
        this.itemConfig = itemConfig;
    }
    @Override
    public Double calculateItemCost(String[] items) {

        int num_of_Apples = 0;
        int num_of_Oranges = 0;

        double appleCost = appleProduct.getItemPrice();
        double orangeCost = orangeProduct.getItemPrice();

        Double totalCost = 0.0;
        for (String item : items){
            if(item.equalsIgnoreCase(appleProduct.getItemName())){
               num_of_Apples++;
            }else if(item.equalsIgnoreCase(orangeProduct.getItemName())){
                num_of_Oranges++;
            }else{
                System.out.println("We sell only oranges and apples");
                break;
            }
        }

        if(num_of_Apples > 1 ){
            if(num_of_Apples%2 == 0){
                totalCost += num_of_Apples/2 * appleCost;
            }

            else{
                totalCost += (num_of_Apples/2 + 1) * appleCost;
            }
        }

        else if(num_of_Apples == 1){
            totalCost += num_of_Apples * appleCost;
        }

        if(num_of_Oranges > 2){

          totalCost +=  num_of_Oranges * ((double) 2/3 * orangeCost);

        }else{
            totalCost += num_of_Oranges * orangeCost;
        }

        return totalCost;
    }
}
