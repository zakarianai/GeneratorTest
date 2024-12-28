package org.javascanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class JavaScannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaScannerApplication.class, args);
    }

}
