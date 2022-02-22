package com.devbeekei.springbatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DeciderJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job deciderJob() {
        return jobBuilderFactory.get("deciderJob")
            .start(startStep()) // startStep 실행

            .next(decider()) // Step 분기

            .from(decider()) // decider 결과가
                .on("StepA") // StepA 라면
                .to(stepA()) // StepA 실행

            .from(decider()) // decider 결과가
                .on("StepB") // StepB 라면
                .to(stepB()) // StepB 실행

            .end()
            .build();
    }

    @Bean
    public Step startStep() {
        return stepBuilderFactory.get("StartStep")
            .tasklet((contribution, chunkContext) -> {
                log.info(">>>>> Start!");
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step stepA() {
        return stepBuilderFactory.get("StepA")
            .tasklet((contribution, chunkContext) -> {
                log.info(">>>>> This is StepA");
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step stepB() {
        return stepBuilderFactory.get("StepB")
            .tasklet((contribution, chunkContext) -> {
                log.info(">>>>> This is StepB");
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public JobExecutionDecider decider() {
        return new StepDecider();
    }

    public static class StepDecider implements JobExecutionDecider {
        @Override
        public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
            String stepName = "StepA";
            return new FlowExecutionStatus(stepName);
        }
    }

}
