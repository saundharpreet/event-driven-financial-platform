package com.harpreetsaund.transactionfileingestor.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.launch.support.TaskExecutorJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.batch.integration.launch.JobLaunchingGateway;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
@EnableBatchIntegration
public class BatchIntegrationConfig implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(BatchIntegrationConfig.class);

    @Bean
    public DirectChannel jobLaunchOutputChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "fileToBatchJobChannel")
    public JobLaunchingGateway jobLaunchingGateway(JobRepository jobRepository) {
        TaskExecutorJobOperator jobOperator = new TaskExecutorJobOperator();
        jobOperator.setTaskExecutor(new SyncTaskExecutor());
        jobOperator.setJobRepository(jobRepository);

        JobLaunchingGateway jobLaunchingGateway = new JobLaunchingGateway(jobOperator);
        jobLaunchingGateway.setOutputChannelName("jobLaunchOutputChannel");

        return jobLaunchingGateway;
    }

    @Bean
    @ServiceActivator(inputChannel = "jobLaunchOutputChannel")
    public MessageHandler jobLaunchOutputHandler() {
        return message -> logger.debug("Received job launch response: {}", message);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("Batch Integration configuration enabled.");
    }
}
