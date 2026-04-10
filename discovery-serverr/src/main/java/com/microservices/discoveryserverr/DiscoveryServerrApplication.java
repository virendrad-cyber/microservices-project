package com.microservices.discoveryserverr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
@EnableDiscoveryClient
public class DiscoveryServerrApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerrApplication.class, args);
    }

}
