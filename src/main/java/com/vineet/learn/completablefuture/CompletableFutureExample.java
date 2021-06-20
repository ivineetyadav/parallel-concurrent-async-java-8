package com.vineet.learn.completablefuture;

import java.util.concurrent.CompletableFuture;

import static com.vineet.learn.util.CommonUtil.*;
import static com.vineet.learn.util.LoggerUtil.log;

import com.vineet.learn.service.HelloWorldService;

public class CompletableFutureExample {
	
	private HelloWorldService hws;
	
		
	public CompletableFutureExample(HelloWorldService hws) {
		super();
		this.hws = hws;
	}

	public CompletableFuture<String> helloWorldUpperCaseAsync() {
		return CompletableFuture
						.supplyAsync(hws::helloWorld)
						.thenApply(String::toUpperCase);
						
	}
	
	public CompletableFuture<String> helloWorld_withSize() {
		return CompletableFuture
						.supplyAsync(hws::helloWorld)
						.thenApply(String::toUpperCase)
						.thenApply(s -> s.length() + "-" + s);				
	}
	
	public String helloworld_multiple_async_calls() {
		startTimer();
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(hws::hello);
		CompletableFuture<String> world = CompletableFuture.supplyAsync(hws::world);
		
		String result = hello.thenCombine(world, (h, w) -> h+w)
			.thenApply(String::toUpperCase)
			.join(); //join is necessary when combining results like this
		
		timeTaken();
		return result;
	}
	
	
	public String helloworld_3_async_calls() {
		startTimer();
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(hws::hello);
		CompletableFuture<String> world = CompletableFuture.supplyAsync(hws::world);
		CompletableFuture<String> hi = CompletableFuture.supplyAsync(() -> {
			delay(1000);
			return " Hi there!";
		});
		
		
		String result = hello.thenCombine(world, (h, w) -> h+w)
				.thenCombine(hi, (previous, current) -> previous + current)
				.thenApply(String::toUpperCase)
				.join(); //join is necessary when combining results like this
		
		timeTaken();
		return result;
	}
	
	
	
	public String helloworld_4_async_calls() {
		startTimer();
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(hws::hello);
		CompletableFuture<String> world = CompletableFuture.supplyAsync(hws::world);
	   CompletableFuture<String> hi = CompletableFuture.supplyAsync(() -> {
	        delay(1000);
	        return " Hi there!";
	    });
	   CompletableFuture<String> bye = CompletableFuture.supplyAsync(() -> {
	        delay(1000);
	        return " BYE!";
	    });
	
		
		String result = hello.thenCombine(world, (h, w) -> h+w)
				.thenCombine(hi, (previous, current) -> previous + current)
				.thenCombine(bye, (previous, current) -> previous + current)
				.thenApply(String::toUpperCase)
				.join(); //join is necessary when combining results like this
		
		timeTaken();
		return result;
	}
	
	
	public CompletableFuture<String> helloWorld_withCompose() {
		return CompletableFuture
						.supplyAsync(hws::hello)
						.thenCompose(previous -> hws.worldFuture(previous))
						.thenApply(String::toUpperCase);
						
	}
	
	
	
	public static void main(String[] args) {
		
		HelloWorldService hws = new HelloWorldService();
		CompletableFuture
						//.supplyAsync(() -> hws.helloWorld())
						//.thenApply(s -> s.toUpperCase())
						.supplyAsync(hws::helloWorld)
						.thenApply(String::toUpperCase)
						.thenAccept(result -> {
							log("Result is : " + result);
						});
		log("Done!");	
		delay(2000);
	}

}
