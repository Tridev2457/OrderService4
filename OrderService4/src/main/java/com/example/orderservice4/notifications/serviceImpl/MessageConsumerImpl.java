package com.example.orderservice4.notifications.serviceImpl;

import com.example.orderservice4.model.NotificationResponse;
import com.example.orderservice4.service.IMessageSend;
import com.example.orderservice4.service.IProductCostCalculator;
import com.example.orderservice4.stockstate.ProductQuantityState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Component
public class MessageConsumerImpl implements IMessageSend {

    @Autowired
    IProductCostCalculator itemCostCalculator;

    NotificationResponse notificationResponse;

    @Autowired
    ProductQuantityState itemState;


    @JmsListener(destination = "simple-jms-queue")
    public void listener(String[] items) {

        boolean sufficientItems = isSufficientItems(items);

        if(sufficientItems){
            double totalCost = itemCostCalculator.calculateItemCost(items);

            notificationResponse = new NotificationResponse(totalCost, "Total Cost ");

            Date deliveryDate = new Date();
            deliveryDate = addDays(deliveryDate, 2);

            System.out.println("Message Received: " + notificationResponse + "Estimated Delivery is ::" + deliveryDate);
        }else{
            System.out.println("Not enough quantity");
        }



    }

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    public boolean isSufficientItems(String[] items) {
        int num_of_Apples = 0;
        int num_of_Oranges = 0;

        Map<String, Integer> stateMap = itemState.getItemQuantityMap();

        for (String item : items) {
            if (item.equalsIgnoreCase("APPLE")) {
                num_of_Apples++;
            } else if (item.equalsIgnoreCase("ORANGE")) {
                num_of_Oranges++;
            } else {
                System.out.println("We sell only oranges and apples");
                break;
            }
        }

        int curr_Apples = stateMap.get("Apple");
        int curr_Oranges = stateMap.get("Orange");

        if (curr_Apples < num_of_Apples || curr_Oranges < num_of_Oranges) {
            return false;
        }else{
            stateMap.put("Apple", curr_Apples - num_of_Apples);
            stateMap.put("Orange", curr_Oranges - num_of_Oranges);
        }
        return true;

    }
}
