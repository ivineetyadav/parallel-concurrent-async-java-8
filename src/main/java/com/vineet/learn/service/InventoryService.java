package com.vineet.learn.service;
import static com.vineet.learn.util.CommonUtil.delay;

import com.vineet.learn.domain.Inventory;
import com.vineet.learn.domain.ProductOption;

public class InventoryService {
    public Inventory retrieveInventory(ProductOption productOption) {
        delay(500);
        return Inventory.builder()
                .count(2).build();

    }
}