package com.vineet.learn.service;

import com.vineet.learn.domain.Review;

import static com.vineet.learn.util.CommonUtil.delay;

public class ReviewService {

    public Review retrieveReviews(String productId) {
        delay(1000);
        //return new Review(200, 4.5);
        return Review.builder()
        		.noOfReviews(200)
        		.overallRating(4.5)
        		.build();
    }
}
