package com.bluex.manifiesto;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ManifiestoApplication {

	public static void main(String[] args) {
		// SpringApplication.run(ManifiestoApplication.class, args);
		SpringApplication app = new SpringApplication(ManifiestoApplication.class);
        app.setDefaultProperties(Collections
          .singletonMap("server.port", "3000"));
        app.run(args);
	}

}
