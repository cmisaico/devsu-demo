package com.devsu.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class AppConfig {

    @Value("${msvc.devsu-cliente.url}")
    private String url;

    @Bean
//    public WebClient registrarWebClient() {
//        return WebClient.create("http://".concat(url).concat("/clientes"));
//    }
        public WebClient registrarWebClient() {
        return WebClient.create("http://localhost:8001/clientes");
    }

}
