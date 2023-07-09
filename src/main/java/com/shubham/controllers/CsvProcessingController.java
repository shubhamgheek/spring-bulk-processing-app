package com.shubham.controllers;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CsvProcessingController {

    private static final Logger logger = LoggerFactory.getLogger(CsvProcessingController.class);

    @Autowired(required = false)
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("readCsvFileToDbJob")
    private Job job;


    @PostMapping("/v1/invoke-batch-processing")
    public ResponseEntity<?> invokeBatchProcessor() throws Exception {
        logger.info("Invoking csv batch processing");

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(job, jobParameters);
        return ResponseEntity.ok().build();
    }
}
