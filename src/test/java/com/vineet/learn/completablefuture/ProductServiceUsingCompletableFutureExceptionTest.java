package com.vineet.learn.completablefuture;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vineet.learn.domain.Product;
import com.vineet.learn.service.InventoryService;
import com.vineet.learn.service.ProductInfoService;
import com.vineet.learn.service.ReviewService;

@ExtendWith(MockitoExtension.class)
public class ProductServiceUsingCompletableFutureExceptionTest {
	
	@Mock
	ProductInfoService productInfoService;
    @Mock
	ReviewService reviewService;
    @Mock
    InventoryService inventoryService;

    @InjectMocks
    ProductServiceUsingCompletableFuture productService;
    
    @Test
    void retrieveProductDetailsWithInventory_approach2_exception_in_review() {
    	when(productInfoService.retrieveProductInfo(any())).thenCallRealMethod();
    	when(reviewService.retrieveReviews(any())).thenThrow(new RuntimeException("exception occured in review service"));
    	when(inventoryService.retrieveInventory(any())).thenCallRealMethod();
    	
    	Product product = productService.retrieveProductDetailsWithInventory_approach2("ABCD123");
    	
    	assertNotNull(product.getProductInfo());
    	assertNotNull(product.getReview());
    	assertTrue(product.getProductInfo().getProductOptions().size()>0);
    	product.getProductInfo().getProductOptions().forEach(productOption -> {
    		assertTrue(productOption.getInventory().getCount() == 2);
    	});
    	assertEquals(0, product.getReview().getNoOfReviews());
    }
    
    
    @Test
    void retrieveProductDetailsWithInventory_approach2_exception_in_productInfo() {
    	when(productInfoService.retrieveProductInfo(any())).thenThrow(new RuntimeException("exception occured in product info service"));
    	when(reviewService.retrieveReviews(any())).thenCallRealMethod();
    	//when(inventoryService.retrieveInventory(any())).thenCallRealMethod();
    	
    	//Product product = productService.retrieveProductDetailsWithInventory_approach2("ABCD123");
    	
    	Assertions.assertThrows(RuntimeException.class, () -> productService.retrieveProductDetailsWithInventory_approach2("ABCD123"));
    }
    
    
    @Test
    void retrieveProductDetailsWithInventory_approach2_exception_in_inventory() {
    	when(productInfoService.retrieveProductInfo(any())).thenCallRealMethod();
    	when(reviewService.retrieveReviews(any())).thenCallRealMethod();
    	when(inventoryService.retrieveInventory(any())).thenThrow(new RuntimeException("exception occured in inventory service"));
    	
    	Product product = productService.retrieveProductDetailsWithInventory_approach2("ABCD123");
    	
    	assertNotNull(product.getProductInfo());
    	assertNotNull(product.getReview());
    	assertTrue(product.getProductInfo().getProductOptions().size()>0);
    	product.getProductInfo().getProductOptions().forEach(productOption -> {
    		assertTrue(productOption.getInventory().getCount() == 1);
    	});
    	
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
