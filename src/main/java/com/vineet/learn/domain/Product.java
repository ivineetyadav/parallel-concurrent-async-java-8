package com.vineet.learn.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @NonNull
    private String productId;
    @NonNull
    private ProductInfo productInfo;
    @NonNull
    private Review review;
}
