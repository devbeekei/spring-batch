package com.devbeekei.springbatch;

import com.devbeekei.springbatch.a.entity.AUser;
import com.devbeekei.springbatch.b.entity.BUser;
import com.devbeekei.springbatch.c.entity.CUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class MigrationJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("AEntityManagerFactory")
    private EntityManagerFactory AEntityManagerFactory;

    @Autowired
    @Qualifier("BEntityManagerFactory")
    private EntityManagerFactory BEntityManagerFactory;

    @Autowired
    @Qualifier("CEntityManagerFactory")
    private EntityManagerFactory CEntityManagerFactory;

    private int chunkSize;
    @Value("${chunkSize:1000}")
    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    @Bean
    public Job migrationJob() {
        return jobBuilderFactory.get("migrationJob")
            .start(migrationBStep())
            .next(migrationCStep())
            .build();
    }

    @Bean
    public Step migrationBStep() {
        return stepBuilderFactory.get("migrationBStep")
            .transactionManager(new JpaTransactionManager(BEntityManagerFactory))
            .<AUser, BUser>chunk(chunkSize)
            .reader(reader())
            .processor(bProcessor())
            .writer(bWriter())
            .build();
    }

    @Bean
    public Step migrationCStep() {
        return stepBuilderFactory.get("migrationCStep")
            .transactionManager(new JpaTransactionManager(CEntityManagerFactory))
            .<AUser, CUser>chunk(chunkSize)
            .reader(reader())
            .processor(cProcessor())
            .writer(cWriter())
            .build();
    }

    @Bean
    public ItemReader<AUser> reader() {
        return new JpaPagingItemReaderBuilder<AUser>()
            .name("reader")
            .entityManagerFactory(AEntityManagerFactory)
            .pageSize(chunkSize)
            .queryString("SELECT u FROM AUser u")
            .build();
    }

    @Bean
    public ItemProcessor<AUser, BUser> bProcessor() {
        return aUser -> BUser.builder()
            .id(aUser.getId())
            .email(aUser.getEmail())
            .name(aUser.getName())
            .build();
    }

    @Bean
    public JpaItemWriter<BUser> bWriter() {
        JpaItemWriter<BUser> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(BEntityManagerFactory);
        writer.setUsePersist(true);
        return writer;
    }

    @Bean
    public ItemProcessor<AUser, CUser> cProcessor() {
        return aUser -> CUser.builder()
            .id(aUser.getId())
            .email(aUser.getEmail())
            .name(aUser.getName())
            .build();
    }

    @Bean
    public JpaItemWriter<CUser> cWriter() {
        JpaItemWriter<CUser> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(CEntityManagerFactory);
        writer.setUsePersist(true);
        return writer;
    }

}
