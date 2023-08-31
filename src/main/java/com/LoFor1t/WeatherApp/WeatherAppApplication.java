package com.LoFor1t.WeatherApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@SpringBootApplication
public class WeatherAppApplication {

	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		HiddenHttpMethodFilter filter = new HiddenHttpMethodFilter();
		return filter;
	}

	public static void main(String[] args) {
		SpringApplication.run(WeatherAppApplication.class, args);
	}

}
