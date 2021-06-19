package com.vineet.learn.service;

import static com.vineet.learn.util.CommonUtil.*;

import java.util.List;
import java.util.stream.Collectors;

import com.vineet.learn.domain.checkout.Cart;
import com.vineet.learn.domain.checkout.CartItem;
import com.vineet.learn.domain.checkout.CheckoutResponse;
import com.vineet.learn.domain.checkout.CheckoutStatus;

public class CheckoutService {
	
	private  PriceValidatorService priceValidatorService;
	
	

	public CheckoutService(PriceValidatorService priceValidatorService) {
		super();
		this.priceValidatorService = priceValidatorService;
	}



	public CheckoutResponse checkout(Cart cart) {
		startTimer();
		List<CartItem> errorItemList = cart.getCartItemList()
			.parallelStream()
			.map(cartItem -> {
				boolean status = priceValidatorService.isCartItemInvalid(cartItem);
				cartItem.setExpired(status);
				return cartItem;
			})
			.filter(CartItem::isExpired)
			.collect(Collectors.toList());
		timeTaken();
		
		if(errorItemList.isEmpty()) {
			return new CheckoutResponse(CheckoutStatus.SUCCESS);
		}
		
		return new CheckoutResponse(CheckoutStatus.FAILURE, errorItemList);
	}

}
