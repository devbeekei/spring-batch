package com.devbeekei.springbatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StepNextConditionalJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

//    @Bean
//    public Job stepNextConditionalJob() {
//        return jobBuilderFactory.get("stepNextConditionalJob")
//            .start(stepNextConditionalStepA()) // StepA 실행
//                .on("FAILED") // 실행 결과가 FAILED 일 경우
//                .to(stepNextConditionalStepC()) // StepC 실행
//                .on("*") // 결과 관계 없이
//                .end() // Flow 종료
//            .from(stepNextConditionalStepA()) // StepA로 부터
//                .on("CUSTOM EXIT CODE") // 실행 결과가 CUSTOM EXIT CODE 일 경우
//                .to(stepNextConditionalStepD()) // SteD 실행
//                .on("*") // 결과 관계 없이
//                .end() // Flow 종료
//            .from(stepNextConditionalStepA()) // StepA로 부터
//                .on("*") // 실행 결과가 FAILED, CUSTOM EXIT CODE 외에 모든 경우
//                .to(stepNextConditionalStepB()) // StepB 실행
//                .next(stepNextConditionalStepC()) // StepB가 정상 종료되면 StepC 실행
//                .on("*") // 결과 관계 없이
//                .end() // Flow 종료
//            .end() // Job 종료
//            .build();
//    }

    @Bean
    public Step stepNextConditionalStepA() {
        return stepBuilderFactory.get("stepNextConditionalStepA")
            .tasklet((contribution, chunkContext) -> {
                log.info(">>>>> This is stepNextConditionalStepA");
                // ExitStatus에 따라 Flow가 진행된다.
//                contribution.setExitStatus(ExitStatus.FAILED);
                return RepeatStatus.FINISHED;
            })
            .listener(new CustomStepExecutionListener())
            .build();
    }

    @Bean
    public Step stepNextConditionalStepB() {
        return stepBuilderFactory.get("stepNextConditionalStepB")
            .tasklet((contribution, chunkContext) -> {
                log.info(">>>>> This is stepNextConditionalStepB");
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step stepNextConditionalStepC() {
        return stepBuilderFactory.get("stepNextConditionalStepC")
            .tasklet((contribution, chunkContext) -> {
                log.info(">>>>> This is stepNextConditionalStepC");
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step stepNextConditionalStepD() {
        return stepBuilderFactory.get("stepNextConditionalStepD")
            .tasklet((contribution, chunkContext) -> {
                log.info(">>>>> This is stepNextConditionalStepD");
                return RepeatStatus.FINISHED;
            })
            .build();
    }

}
