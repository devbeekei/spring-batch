package com.devbeekei.springbatch.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "CEntityManagerFactory",
    transactionManagerRef = "CTransactionManager",
    basePackages = { "com.devbeekei.springbatch.c.repository" }
)
class CEntityManagerFactoryConfiguration {

    @Autowired
    @Qualifier("CDataSource")
    private DataSource CDataSource;

    @Bean(name = "CEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean CEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
            .dataSource(CDataSource)
            .packages("com.devbeekei.springbatch.c.entity")
            .persistenceUnit("C")
            .build();
    }

}
