package com.devbeekei.springbatch;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;

public class CustomStepExecutionListener extends StepExecutionListenerSupport {

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        ExitStatus exitStatus = stepExecution.getExitStatus();
        if (!exitStatus.equals(ExitStatus.FAILED)) {
            return new ExitStatus("CUSTOM EXIT CODE");
        } else {
            return null;
        }
    }

}
