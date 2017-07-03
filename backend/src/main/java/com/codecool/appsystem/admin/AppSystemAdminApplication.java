package com.codecool.appsystem.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AppSystemAdminApplication {


    @Bean(name = "authenticationRestTemplate")
    public RestTemplate authenticationRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add( new FormHttpMessageConverter());
        return restTemplate;
    }

	public static void main(String[] args) {
		SpringApplication.run(AppSystemAdminApplication.class, args);
	}

}
