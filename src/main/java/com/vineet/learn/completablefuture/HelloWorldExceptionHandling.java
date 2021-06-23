package com.vineet.learn.completablefuture;

import static com.vineet.learn.util.CommonUtil.delay;
import static com.vineet.learn.util.CommonUtil.startTimer;
import static com.vineet.learn.util.CommonUtil.timeTaken;
import static com.vineet.learn.util.LoggerUtil.log;

import java.util.concurrent.CompletableFuture;

import com.vineet.learn.service.HelloWorldService;


public class HelloWorldExceptionHandling {

	
	private HelloWorldService hws;
	
	
	public HelloWorldExceptionHandling(HelloWorldService hws) {
		super();
		this.hws = hws;
	}
	
	public String helloworld_3_async_calls_handle() {
		startTimer();
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(hws::hello);
		CompletableFuture<String> world = CompletableFuture.supplyAsync(hws::world);
				/*.handle((res, ex) -> {
					log("Exception is : " + ex.getMessage());
					return " Vishav!";
					
				});*/
		CompletableFuture<String> hi = CompletableFuture.supplyAsync(() -> {
			delay(1000);
			return " Hi there!";
		});
		
		
		String result = hello
				.handle((res, ex) -> {
					log("response is : " + res);
					if(ex != null) {
						log("Exception after hello is : " + ex.getMessage());
						return "";
					}else
						return res;
				})
				.thenCombine(world, (h, w) -> h+w)
				.handle((res, ex) -> {
					log("response is : " + res);
					if(ex != null) {
						log("Exception after world is : " + ex.getMessage());
						return " Vishav!";
					}else
						return res;
					
				})
				.thenCombine(hi, (previous, current) -> previous + current)
				.thenApply(String::toUpperCase)
				.join(); //join is necessary when combining results like this
		
		timeTaken();
		return result;

	}
	
	
	
	public String helloworld_3_async_calls_exceptionally() {
		startTimer();
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(hws::hello);
		CompletableFuture<String> world = CompletableFuture.supplyAsync(hws::world);
		CompletableFuture<String> hi = CompletableFuture.supplyAsync(() -> {
			delay(1000);
			return " Hi there!";
		});
		
		
		String result = hello
				.exceptionally(ex -> {
					log("Exception after hello is : " + ex.getMessage());
					return "";
				})
				.thenCombine(world, (h, w) -> h+w)
				.exceptionally(ex -> {
					log("Exception after world is : " + ex.getMessage());
					return "";
				})
				.thenCombine(hi, (previous, current) -> previous + current)
				.thenApply(String::toUpperCase)
				.join(); //join is necessary when combining results like this
		
		timeTaken();
		return result;

	}
	
	
	public String helloworld_3_async_calls_whenComplete() {
		startTimer();
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(hws::hello);
		CompletableFuture<String> world = CompletableFuture.supplyAsync(hws::world);
		CompletableFuture<String> hi = CompletableFuture.supplyAsync(() -> {
			delay(1000);
			return " Hi there!";
		});
		
		
		String result = hello
				.whenComplete((res, ex) -> {
					log("response is : " + res);
					if(ex != null) {
						log("Exception after hello is : " + ex.getMessage());
					}
				})
				.thenCombine(world, (h, w) -> h+w)
				.whenComplete((res, ex) -> {
					log("response is : " + res);
					if(ex != null) {
						log("Exception after world is : " + ex.getMessage());
					}
				})
				.thenCombine(hi, (previous, current) -> previous + current)
				.thenApply(String::toUpperCase)
				.join(); //join is necessary when combining results like this
		
		timeTaken();
		return result;

	}
	
	
	
	public String helloworld_3_async_calls_whenComplete_exceptionally() {
		startTimer();
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(hws::hello);
		CompletableFuture<String> world = CompletableFuture.supplyAsync(hws::world);
		CompletableFuture<String> hi = CompletableFuture.supplyAsync(() -> {
			delay(1000);
			return " Hi there!";
		});
		
		
		String result = hello
				.whenComplete((res, ex) -> {
					log("response is : " + res);
					if(ex != null) {
						log("Exception after hello is : " + ex.getMessage());
					}
				})
				.thenCombine(world, (h, w) -> h+w)
				.whenComplete((res, ex) -> {
					log("response is : " + res);
					if(ex != null) {
						log("Exception after world is : " + ex.getMessage());
					}
				})
				.exceptionally(ex -> {
					log("Exception after 2nd whenComplete() is : " + ex.getMessage());
					return "";
				})
				.thenCombine(hi, (previous, current) -> previous + current)
				.thenApply(String::toUpperCase)
				.join(); //join is necessary when combining results like this
		
		timeTaken();
		return result;

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
