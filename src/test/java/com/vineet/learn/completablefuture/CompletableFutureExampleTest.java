package com.vineet.learn.completablefuture;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.vineet.learn.util.CommonUtil.*;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;

import com.vineet.learn.service.HelloWorldService;

public class CompletableFutureExampleTest {
	
	HelloWorldService hws = new HelloWorldService();
	CompletableFutureExample cf = new CompletableFutureExample(hws);
	
	@Test
	void helloWorldUpperCaseAsync() {
		CompletableFuture<String> result = cf.helloWorldUpperCaseAsync();
		result.thenAccept(s -> {
			assertEquals("HELLO WORLD", s);
		})
		.join();
	}

	
	@Test
	void helloWorld_withSize() {
		CompletableFuture<String> result = cf.helloWorld_withSize();
		result.thenAccept(s -> {
			assertTrue(s.endsWith("HELLO WORLD"));
			assertTrue(s.contains("-"));
		})
		.join();
	}
	
	@Test
	void helloworld_multiple_async_calls() {
		String result = cf.helloworld_multiple_async_calls();
		assertEquals("HELLO WORLD!", result);
		
	}
	
	
	
	@Test
	void helloworld_3_async_calls_with_customthreadpool() {
		String result = cf.helloworld_3_async_calls_with_customthreadpool();
		assertEquals("HELLO WORLD! HI THERE!", result);
		
	}
	
	@Test
	void helloworld_3_async_calls_with_completionStage_async() {
		String result = cf.helloworld_3_async_calls_with_completionStage_async();
		assertEquals("HELLO WORLD! HI THERE!", result);
		
	}
	
	@Test
	void helloworld_3_async_calls_with_customthreadpool_completionStage_async() {
		String result = cf.helloworld_3_async_calls_with_customthreadpool_completionStage_async();
		assertEquals("HELLO WORLD! HI THERE!", result);
		
	}
	
	@Test
	void helloworld_3_async_calls() {
		String result = cf.helloworld_3_async_calls();
		assertEquals("HELLO WORLD! HI THERE!", result);
		
	}
	
	
	@Test
	void helloworld_4_async_calls() {
		String result = cf.helloworld_4_async_calls();
		assertEquals("HELLO WORLD! HI THERE! BYE!", result);
		
	}
	
	
	@Test
	void helloworld_withCompose() {
		startTimer();
		CompletableFuture<String> result = cf.helloWorld_withCompose();
		result.thenAccept(s -> {
			assertEquals("HELLO WORLD!", s);
		})
		.join();
		timeTaken();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
