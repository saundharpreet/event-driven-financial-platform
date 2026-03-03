package com.harpreetsaund.transactionfileingestor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class MessageTransformService {

    private static final Logger logger = LoggerFactory.getLogger(MessageTransformService.class);

    @Transformer(inputChannel = "fileToBatchJobChannel")
    public Message<JobLaunchRequest> transformFileToBatchJobRequest(Message<File> inboundMessage) {
        File inboundFile = inboundMessage.getPayload();

        logger.debug("Transforming inbound file {} to batch job launch request", inboundFile.getAbsolutePath());

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("input.filepath", inboundFile.getAbsolutePath())
                .toJobParameters();

        JobLaunchRequest jobLaunchRequest = new JobLaunchRequest(null, jobParameters);

        return MessageBuilder.withPayload(jobLaunchRequest).copyHeaders(inboundMessage.getHeaders()).build();
    }
}
