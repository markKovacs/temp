package com.codecool.appsystem.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@EnableScheduling
@SpringBootApplication
public class AppSystemAdminApplication {


    @Value("${mailgun.adapter.api.key}")
    private String mailgunAdapterApiKey;

    @Bean(name = "emailSenderRestTemplate")
    public RestTemplate emailSenderRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors().add(
                (request, body, execution) -> {
                    request.getHeaders().add("Authorization", "Bearer " + mailgunAdapterApiKey);
                    return execution.execute(request, body);
                }
        );
        return restTemplate;
    }

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
