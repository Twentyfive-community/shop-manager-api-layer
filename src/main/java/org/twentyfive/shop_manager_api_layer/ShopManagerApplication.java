package org.twentyfive.shop_manager_api_layer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableFeignClients
@EnableJpaAuditing
@EntityScan(basePackages = {"twentyfive.twentyfiveadapter.models.msUserBusinessModels",
        "org.twentyfive.shop_manager_api_layer.models"})
@ComponentScan(basePackages = {"org.twentyfive.shop_manager_api_layer",
        "com.twentyfive.authorizationflow"})
@EnableJpaRepositories(basePackages = "org.twentyfive.shop_manager_api_layer.repositories")
public class ShopManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopManagerApplication.class, args);
    }

}
