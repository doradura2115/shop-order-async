package com.example.shop_order_async;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ShopOrderAsyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopOrderAsyncApplication.class, args);
	}
}