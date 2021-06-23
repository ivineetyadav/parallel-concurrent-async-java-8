package com.vineet.learn.domain.metaweather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MetaWeatherLocation {
	

	private String title;
	private String location_type;
	private int woeid;
	private String latt_long;

}
