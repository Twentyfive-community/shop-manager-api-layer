package org.twentyfive.shop_manager_api_layer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"org.twentyfive.shop_manager_api_layer", "com.twentyfive.authorizationflow"})
public class ShopManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopManagerApplication.class, args);
    }

}
