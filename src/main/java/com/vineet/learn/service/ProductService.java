package com.vineet.learn.service;

import static com.vineet.learn.util.CommonUtil.stopWatch;
import static com.vineet.learn.util.LoggerUtil.log;

import com.vineet.learn.domain.Product;
import com.vineet.learn.domain.ProductInfo;
import com.vineet.learn.domain.Review;

public class ProductService {
	
    private ProductInfoService productInfoService;
    private ReviewService reviewService;

    public ProductService(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId) {
        stopWatch.start();

        ProductInfo productInfo = productInfoService.retrieveProductInfo(productId); // blocking call
        Review review = reviewService.retrieveReviews(productId); // blocking call

        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        //return new Product(productId, productInfo, review);
        return Product.builder()
        		.productId(productId)
        		.productInfo(productInfo)
        		.review(review)
        		.build();
    }

    public static void main(String[] args) {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductService productService = new ProductService(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);

    }
}
