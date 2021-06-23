package com.vineet.learn.apiclient;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.vineet.learn.domain.metaweather.MetaWeatherLocation;

public class MetaWeatherLocationClientTest {

	//MetaWeatherLocationClient metaWeatherLocationClient = new MetaWeatherLocationClient("https://api.ipify.org");
	MetaWeatherLocationClient metaWeatherLocationClient = new MetaWeatherLocationClient("https://www.metaweather.com");
	
	@Test
	void invokeMetaWeatherLocationApi() {
		List<MetaWeatherLocation> result = metaWeatherLocationClient.invokeMetaWeatherLocationApi("us");
		assertTrue(result.size()>0);
		
		result.forEach(metaWeatherLocation -> { 
			System.out.println(metaWeatherLocation);
			assertNotNull(metaWeatherLocation);
		});
		 
	}
	
	
	@Test
	void invokeMetaWeatherLocationApiWithMultipleLocation() {
		List<String> locations = List.of("us", "uk","fr",  "in", "pr");
		List<MetaWeatherLocation> result = metaWeatherLocationClient.invokeMetaWeatherLocationApiWithMultipleLocation(locations);
		assertTrue(result.size()>0);
		
		result.forEach(metaWeatherLocation -> { 
			System.out.println(metaWeatherLocation);
			assertNotNull(metaWeatherLocation);
		});
		 
	}
	
	
	@Test
	void invokeMetaWeatherLocationApiWithMultipleLocationAsync() {
		List<String> locations = List.of("us", "uk","fr", "in", "pr");
		List<MetaWeatherLocation> result = metaWeatherLocationClient.invokeMetaWeatherLocationApiWithMultipleLocationAsync(locations);
		assertTrue(result.size()>0);
		
		result.forEach(metaWeatherLocation -> { 
			System.out.println(metaWeatherLocation);
			assertNotNull(metaWeatherLocation);
		});
		 
	}
	
	
	
	
	
	
	
}
