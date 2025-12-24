package com.example.room_rent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RoomRentApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomRentApplication.class, args);
	}

}
