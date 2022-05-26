package com.engineeringwithramaa.springbatchparallelsteps;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/parallel-steps/start")
public class ParallelStepsController {
    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    private Job job;

    @GetMapping
    public ResponseEntity<String> parallelSteps() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        Map<String, JobParameter> maps = new HashMap<>();
        maps.put("Time-stamp ", new JobParameter(System.currentTimeMillis()));

        JobParameters jobParameters = new JobParameters(maps);
        JobExecution execution = jobLauncher.run(job, jobParameters);


        return ResponseEntity.ok("finsihed");
    }
}
