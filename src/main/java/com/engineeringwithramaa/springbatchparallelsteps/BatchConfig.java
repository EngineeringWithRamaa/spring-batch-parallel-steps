package com.engineeringwithramaa.springbatchparallelsteps;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import javax.xml.ws.Action;

@Configuration
public class BatchConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Tasklet tasklet() {
        return new CountingTasklet();
    }

    @Bean
    public Flow flow1() {
        return new FlowBuilder<Flow>("flow 1")
                    .start(stepBuilderFactory.get("step 1 ")
                            .tasklet(tasklet()).build())
                    .build();
    }

    @Bean
    public Flow flow2() {
        return new FlowBuilder<Flow>("flow 2")
                .start(stepBuilderFactory.get("step 2")
                        .tasklet(tasklet()).build())
                .next(stepBuilderFactory.get("step 3 ")
                        .tasklet(tasklet()).build())
                .build();
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("Job ")
                .start(flow1())
                .split(new SimpleAsyncTaskExecutor("Parallel Steps - Simple Async Task Executor"))
                .add(flow2())
                .end()
                .build();
    }
}
