package br.com.mercadoon.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Cofiguration {

    /* bean para reparar o migration do flyway.
    @Bean
    public FlywayMigrationStrategy cleanMigrationStrategy() {
        return flyway -> {
            flyway.repair();
            flyway.migrate();
        };
    }

     */
}
