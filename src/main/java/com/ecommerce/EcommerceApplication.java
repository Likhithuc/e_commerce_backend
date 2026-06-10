package com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;

@SpringBootApplication
public class EcommerceApplication {

    public static void main(String[] args) {
        String databaseUrl = System.getenv("DATABASE_URL");
        if (databaseUrl != null && !databaseUrl.isEmpty()) {
            URI uri = URI.create(databaseUrl.replace("postgresql://", "https://"));
            String[] userInfo = uri.getUserInfo().split(":");
            System.setProperty("JDBC_DATABASE_URL",
                    "jdbc:postgresql://" + uri.getHost() + ":" + uri.getPort() + uri.getPath() + "?sslmode=require");
            System.setProperty("JDBC_DATABASE_USERNAME", userInfo[0]);
            System.setProperty("JDBC_DATABASE_PASSWORD", userInfo[1]);
        }
        SpringApplication.run(EcommerceApplication.class, args);
    }
}
