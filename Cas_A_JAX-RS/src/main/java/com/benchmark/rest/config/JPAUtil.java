package com.benchmark.rest.config;


import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource; // pour DataSource
import java.util.HashMap;
import java.util.Map;

public class JPAUtil {

    private static EntityManagerFactory emf;

    public static EntityManagerFactory getEMF() {
        if (emf == null) {
            Map<String, Object> props = new HashMap<>();
            props.put("jakarta.persistence.nonJtaDataSource", dataSource());
            emf = Persistence.createEntityManagerFactory("benchmarkPU", props);
        }
        return emf;
    }

    private static DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/benchmark_rest");
        config.setUsername("postgres");
        config.setPassword("12345");

        config.setMinimumIdle(10);
        config.setMaximumPoolSize(20);
        config.setIdleTimeout(60000);
        config.setMaxLifetime(1800000);
        config.setConnectionTimeout(30000);

        return new HikariDataSource(config);
    }
}
