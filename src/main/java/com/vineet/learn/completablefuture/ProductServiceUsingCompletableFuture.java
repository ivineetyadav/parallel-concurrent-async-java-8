package com.vineet.learn.completablefuture;

import static com.vineet.learn.util.CommonUtil.stopWatch;
import static com.vineet.learn.util.LoggerUtil.log;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.vineet.learn.domain.Inventory;
import com.vineet.learn.domain.Product;
import com.vineet.learn.domain.ProductInfo;
import com.vineet.learn.domain.ProductOption;
import com.vineet.learn.domain.Review;
import com.vineet.learn.service.InventoryService;
import com.vineet.learn.service.ProductInfoService;
import com.vineet.learn.service.ReviewService;

public class ProductServiceUsingCompletableFuture {
	
    private ProductInfoService productInfoService;
    private ReviewService reviewService;
    private InventoryService inventoryService;

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }
    
    

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService,
			InventoryService inventoryService) {
		super();
		this.productInfoService = productInfoService;
		this.reviewService = reviewService;
		this.inventoryService = inventoryService;
	}



	public Product retrieveProductDetails_approach1_for_client_based_app(String productId) {
        stopWatch.start();

       CompletableFuture<ProductInfo> productInfoFuture = CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
       CompletableFuture<Review> reviewFuture = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId)); 
 
       Product product = productInfoFuture.
       				thenCombine(reviewFuture, (productInfo, review) -> new Product(productId, productInfo, review))
       				.join(); //blocking the thread is necessary here because we are returning the combined result
       
       stopWatch.stop();
       log("Total Time Taken : "+ stopWatch.getTime());
       return product;
    }
    
    
    public CompletableFuture<Product> retrieveProductDetails_approach2_for_server_based_app(String productId) {

       CompletableFuture<ProductInfo> productInfoFuture = CompletableFuture
    		   .supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
       CompletableFuture<Review> reviewFuture = CompletableFuture
    		   .supplyAsync(() -> reviewService.retrieveReviews(productId)); 
 
       return productInfoFuture.
       				thenCombine(reviewFuture, (productInfo, review) -> new Product(productId, productInfo, review));
       
    }
    
    
    
    public Product retrieveProductDetailsWithInventory(String productId) {
        stopWatch.start();

       CompletableFuture<ProductInfo> productInfoFuture = CompletableFuture
    		   .supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
    		   .thenApply(productInfo -> {
    			   List<ProductOption> productOptions = updateInventory(productInfo);
    			   productInfo.setProductOptions(productOptions);
    			   return productInfo;
    		   });
       
       CompletableFuture<Review> reviewFuture = CompletableFuture
    		   .supplyAsync(() -> reviewService.retrieveReviews(productId)); 
 
       Product product = productInfoFuture.
       				thenCombine(reviewFuture, (productInfo, review) -> new Product(productId, productInfo, review))
       				.join(); //blocking the thread is necessary here because we are returning the combined result
       
       stopWatch.stop();
       log("Total Time Taken : "+ stopWatch.getTime());
       return product;
    }
    
    

    private List<ProductOption> updateInventory(ProductInfo productInfo) {
		return productInfo.getProductOptions()
				.stream()
				.map(productOption -> {
					Inventory inventory = inventoryService.retrieveInventory(productOption);
					productOption.setInventory(inventory);
					return productOption;
				})
				.collect(Collectors.toList());

	}
    
    
    public Product retrieveProductDetailsWithInventory_approach2(String productId) {
        stopWatch.start();

       CompletableFuture<ProductInfo> productInfoFuture = CompletableFuture
    		   .supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
    		   .thenApply(productInfo -> {
    			   List<ProductOption> productOptions = updateInventory_approach2(productInfo);
    			   productInfo.setProductOptions(productOptions);
    			   return productInfo;
    		   });
       
       CompletableFuture<Review> reviewFuture = CompletableFuture
    		   .supplyAsync(() -> reviewService.retrieveReviews(productId))
    		   .exceptionally(ex -> {
    			  log("Exception in review is : " + ex.getMessage()) ;
    			  return Review.builder()
    					  .noOfReviews(0)
    					  .overallRating(0.0)
    					  .build();
    		   }); 
 
       Product product = productInfoFuture.
       				thenCombine(reviewFuture, (productInfo, review) -> new Product(productId, productInfo, review))
       				.whenComplete((res, ex) -> {
       					if(ex != null) {
       						log("Response in whenComplete is : " + res);
       						log("inside  whenComplete : " + ex);
       					}
       				})
       				.join(); //blocking the thread is necessary here because we are returning the combined result
       
       stopWatch.stop();
       log("Total Time Taken : "+ stopWatch.getTime());
       return product;
    }
    
    
    private List<ProductOption> updateInventory_approach2(ProductInfo productInfo) {
		 List<CompletableFuture<ProductOption>> productOptionFuture = productInfo.getProductOptions()
				.stream()
				.map(productOption -> {
					return CompletableFuture.supplyAsync(() -> inventoryService.retrieveInventory(productOption))
							.exceptionally(ex -> {
								log("Exception in inventory service : " + ex);
								return Inventory.builder().count(1).build();
							})
							.thenApply(inventory -> {
								productOption.setInventory(inventory);
								return productOption;
							});
					
				})
				.collect(Collectors.toList());
		 
		 return productOptionFuture.stream().map(CompletableFuture::join).collect(Collectors.toList());

	}

    

	public static void main(String[] args) {
        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingCompletableFuture productService = new ProductServiceUsingCompletableFuture(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails_approach1_for_client_based_app(productId);
        log("Product is " + product);
    }
}
