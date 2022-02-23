package com.devbeekei.springbatch.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties
public class DataSourceProperties {

    @Primary
    @Bean(name = "ADataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.a-database")
    public DataSource ADataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "BDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.b-database")
    public DataSource BDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "CDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.c-database")
    public DataSource CDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

}
