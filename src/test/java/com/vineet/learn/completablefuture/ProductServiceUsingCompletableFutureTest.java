package com.vineet.learn.completablefuture;

import static com.vineet.learn.util.CommonUtil.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;

import com.vineet.learn.domain.Product;
import com.vineet.learn.service.InventoryService;
import com.vineet.learn.service.ProductInfoService;
import com.vineet.learn.service.ReviewService;

public class ProductServiceUsingCompletableFutureTest {
	
	ProductInfoService productInfoService = new ProductInfoService();
    ReviewService reviewService = new ReviewService();
    InventoryService inventoryService = new InventoryService();
    
    ProductServiceUsingCompletableFuture productService = 
    		new ProductServiceUsingCompletableFuture(productInfoService, reviewService, inventoryService);
    
    @Test
    void retrieveProductDetails_approach1_for_client_based_app() {
    	Product product = productService.retrieveProductDetails_approach1_for_client_based_app("ABCD123");
    	
    	assertNotNull(product.getProductInfo());
    	assertNotNull(product.getReview());
    	assertTrue(product.getProductInfo().getProductOptions().size()>0);
    }
    
    
    @Test
    void retrieveProductDetails_approach1_for_server_based_app() {
    	startTimer();
    	CompletableFuture<Product> productFuture = productService.retrieveProductDetails_approach2_for_server_based_app("ABCD123");
    	
    	productFuture.thenAccept(product -> {
    		assertNotNull(product.getProductInfo());
        	assertNotNull(product.getReview());
        	assertTrue(product.getProductInfo().getProductOptions().size()>0);
    	})
    	.join();
    	timeTaken();
    }
    
    
    @Test
    void retrieveProductDetailsWithInventory() {
    	Product product = productService.retrieveProductDetailsWithInventory("ABCD123");
    	
    	assertNotNull(product.getProductInfo());
    	assertNotNull(product.getReview());
    	assertTrue(product.getProductInfo().getProductOptions().size()>0);
    	product.getProductInfo().getProductOptions().forEach(productOption -> {
    		assertTrue(productOption.getInventory().getCount() == 2);
    	});
    }
    
    
    @Test
    void retrieveProductDetailsWithInventory_approach2() {
    	Product product = productService.retrieveProductDetailsWithInventory_approach2("ABCD123");
    	
    	assertNotNull(product.getProductInfo());
    	assertNotNull(product.getReview());
    	assertTrue(product.getProductInfo().getProductOptions().size()>0);
    	product.getProductInfo().getProductOptions().forEach(productOption -> {
    		assertTrue(productOption.getInventory().getCount() == 2);
    	});
    }

}
