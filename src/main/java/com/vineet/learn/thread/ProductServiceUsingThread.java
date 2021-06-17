package com.vineet.learn.thread;

import static com.vineet.learn.util.CommonUtil.stopWatch;
import static com.vineet.learn.util.LoggerUtil.log;

import com.vineet.learn.domain.Product;
import com.vineet.learn.domain.ProductInfo;
import com.vineet.learn.domain.Review;
import com.vineet.learn.service.ProductInfoService;
import com.vineet.learn.service.ReviewService;

public class ProductServiceUsingThread {
	
    private ProductInfoService productInfoService;
    private ReviewService reviewService;

    public ProductServiceUsingThread(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId) throws InterruptedException {
        stopWatch.start();
        
        ProductInfoRunnable productInfoRunnable = new ProductInfoRunnable(productId);
        ReviewRunnable reviewRunnable = new ReviewRunnable(productId);
        
        Thread productInfoThread = new Thread(productInfoRunnable);
        Thread reviewThread = new Thread(reviewRunnable);
        
        productInfoThread.start();
        reviewThread.start();
        
        productInfoThread.join();
        reviewThread.join();
        
        ProductInfo productInfo = productInfoRunnable.getProductInfo(); // async call
        Review review = reviewRunnable.getReview(); // async call

        stopWatch.stop();
        
        log("Total Time Taken : "+ stopWatch.getTime());
        //return new Product(productId, productInfo, review);
        return Product.builder()
        		.productId(productId)
        		.productInfo(productInfo)
        		.review(review)
        		.build();
    }

    public static void main(String[] args) throws InterruptedException {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingThread productService = new ProductServiceUsingThread(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);

    }
    
    
    private class ProductInfoRunnable implements Runnable{

    	private String productId;
    	private ProductInfo productInfo;
    	   	
		public ProductInfoRunnable(String productId) {
			super();
			this.productId = productId;
		}

		public ProductInfo getProductInfo() {
			return productInfo;
		}

		@Override
		public void run() {
			productInfo = productInfoService.retrieveProductInfo(productId); 
		}
    	
    }
    
    
    
    private class ReviewRunnable implements Runnable{

    	private String productId;
    	private Review review;
    	   	
		public ReviewRunnable(String productId) {
			super();
			this.productId = productId;
		}

		public Review getReview() {
			return review;
		}

		@Override
		public void run() {
			review = reviewService.retrieveReviews(productId); 
		}
    	
    }
    
    
    
    
    
}
