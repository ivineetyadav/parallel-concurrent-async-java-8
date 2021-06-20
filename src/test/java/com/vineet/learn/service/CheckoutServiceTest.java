package com.vineet.learn.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ForkJoinPool;

import org.junit.jupiter.api.Test;

import com.vineet.learn.domain.checkout.Cart;
import com.vineet.learn.domain.checkout.CheckoutResponse;
import com.vineet.learn.domain.checkout.CheckoutStatus;
import com.vineet.learn.util.DataSet;

public class CheckoutServiceTest {
	
	PriceValidatorService priceValidationService = new PriceValidatorService();
	CheckoutService checkoutService = new CheckoutService(priceValidationService);
	
	@Test
	void checkout_with_6_items() {
		Cart cart = DataSet.createCart(6);
		
		CheckoutResponse checkoutResponse = checkoutService.checkout(cart);
		
		assertEquals(CheckoutStatus.SUCCESS, checkoutResponse.getCheckoutStatus());
	}

	
	
	@Test
	void checkout_with_8_items() {
		Cart cart = DataSet.createCart(8);
		
		CheckoutResponse checkoutResponse = checkoutService.checkout(cart);
		
		assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
	}
	

	@Test
	void modify_parallelism() {
		
		System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "100");
		Cart cart = DataSet.createCart(100);
		
		CheckoutResponse checkoutResponse = checkoutService.checkout(cart);
		
		assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
		System.out.print("parallelism : " + ForkJoinPool.getCommonPoolParallelism());
	}
	
	
	@Test
	void checkout_with_9_items() {
		Cart cart = DataSet.createCart(9);
		
		CheckoutResponse checkoutResponse = checkoutService.checkout(cart);
		
		assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
	}

}
