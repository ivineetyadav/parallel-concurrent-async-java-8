package com.vineet.learn.apiclient;

import static com.vineet.learn.util.CommonUtil.startTimer;
import static com.vineet.learn.util.CommonUtil.timeTaken;
import static com.vineet.learn.util.LoggerUtil.log;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.vineet.learn.domain.metaweather.MetaWeatherLocation;

public class MetaWeatherLocationClient {
	
	private WebClient webClient;

	public MetaWeatherLocationClient(String baseUrl) {
		super();
		this.webClient = WebClient.create(baseUrl);
	}
	
	
	public List<MetaWeatherLocation> invokeMetaWeatherLocationApi(String query) {
		
		String uriString = UriComponentsBuilder.fromUriString("/api/location/search/")//.fromUriString("/")
						//.queryParam("format", query)
				.queryParam("query", query)		
				.buildAndExpand()
						.toUriString();
		
		log("URI : " + uriString);
		
		List<MetaWeatherLocation> list = webClient.get().uri(uriString)
			.retrieve()
			.bodyToFlux(MetaWeatherLocation.class)
			.collectList()
			.block();
		
		return list;
		
		
			
	}
	
	
	public List<MetaWeatherLocation> invokeMetaWeatherLocationApiWithMultipleLocation(List<String> query) {
		startTimer();
		
		List<MetaWeatherLocation> list = query.stream()
			.map(loc -> invokeMetaWeatherLocationApi(loc))
			.flatMap(Collection::stream)
			.collect(Collectors.toList());
		
		timeTaken();
		return list;
	}
	
	
	public List<MetaWeatherLocation> invokeMetaWeatherLocationApiWithMultipleLocationAsync(List<String> query) {
		startTimer();
		
		List<CompletableFuture<List<MetaWeatherLocation>>> list = query.stream()
			.map(loc -> CompletableFuture.supplyAsync(() -> invokeMetaWeatherLocationApi(loc)))
			.collect(Collectors.toList());
		
		List<MetaWeatherLocation> result = list.stream()
			.map(CompletableFuture::join)
			.flatMap(Collection::stream)
			.collect(Collectors.toList());
		
		timeTaken();
		return result;
	}
	
	
	
	
	
	public static void main(String[] args) {
		//new MetaWeatherLocationClient("https://api.ipify.org").invokeMetaWeatherLocationApi("json");
		new MetaWeatherLocationClient("https://www.metaweather.com").invokeMetaWeatherLocationApi("us");
	}
}
