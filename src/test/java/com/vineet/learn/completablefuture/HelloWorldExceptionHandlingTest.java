package com.vineet.learn.completablefuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletionException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vineet.learn.service.HelloWorldService;

@ExtendWith(MockitoExtension.class)
public class HelloWorldExceptionHandlingTest {
	
	@Mock
	HelloWorldService helloWorldService; //= mock(HelloWorldService.class);
	
	@InjectMocks
	HelloWorldExceptionHandling helloWorldExceptionHandling;
	
	@Test
	void helloworld_3_async_calls() {
		when(helloWorldService.hello()).thenThrow(new RuntimeException("runtime exception in hello"));
		//when(helloWorldService.world()).thenCallRealMethod();
		when(helloWorldService.world()).thenThrow(new RuntimeException("runtime exception in world"));
		
		String result = helloWorldExceptionHandling.helloworld_3_async_calls_handle();
		
		//assertEquals(" WORLD! HI THERE!", result);
		assertEquals(" VISHAV! HI THERE!", result);
	}
	
	@Test
	void helloworld_3_async_calls_withoutExceptionMocking() {
		when(helloWorldService.hello()).thenCallRealMethod();
		when(helloWorldService.world()).thenCallRealMethod();
		
		String result = helloWorldExceptionHandling.helloworld_3_async_calls_handle();
		
		assertEquals("HELLO WORLD! HI THERE!", result);
		
	}
	
	
	@Test
	void helloworld_3_async_calls_exceptionally() {
		when(helloWorldService.hello()).thenCallRealMethod();
		when(helloWorldService.world()).thenCallRealMethod();
		
		String result = helloWorldExceptionHandling.helloworld_3_async_calls_exceptionally();
		
		assertEquals("HELLO WORLD! HI THERE!", result);
	}
	
	
	@Test
	void helloworld_3_async_calls_exceptionally_2() {
		when(helloWorldService.hello()).thenThrow(new RuntimeException("hello exception"));
		when(helloWorldService.world()).thenThrow(new RuntimeException("world exception"));
		
		String result = helloWorldExceptionHandling.helloworld_3_async_calls_exceptionally();
		
		assertEquals(" HI THERE!", result);
	}
	
	
	@Test
	void helloworld_3_async_calls_whenComplete() {
		when(helloWorldService.hello()).thenCallRealMethod();
		when(helloWorldService.world()).thenCallRealMethod();
		
		String result = helloWorldExceptionHandling.helloworld_3_async_calls_whenComplete();
		
		assertEquals("HELLO WORLD! HI THERE!", result);
	}
	
	
	@Test
	void helloworld_3_async_calls_whenComplete_withexception() {
		when(helloWorldService.hello()).thenThrow(new RuntimeException("hello exception"));
		when(helloWorldService.world()).thenThrow(new RuntimeException("world exception"));
		
		//String result = helloWorldExceptionHandling.helloworld_3_async_calls_whenComplete();
		
		//assertEquals("HELLO WORLD! HI THERE!", result);
		assertThrows(CompletionException.class, () -> helloWorldExceptionHandling.helloworld_3_async_calls_whenComplete());
	}
	
	
	@Test
	void helloworld_3_async_calls_whenComplete_exceptionally_withexception() {
		when(helloWorldService.hello()).thenThrow(new RuntimeException("hello exception"));
		when(helloWorldService.world()).thenThrow(new RuntimeException("world exception"));
		
		String result = helloWorldExceptionHandling.helloworld_3_async_calls_whenComplete_exceptionally();
		
		assertEquals(" HI THERE!", result);
	}
	
	
	
	
	
	
	
	
	
	
	

}
