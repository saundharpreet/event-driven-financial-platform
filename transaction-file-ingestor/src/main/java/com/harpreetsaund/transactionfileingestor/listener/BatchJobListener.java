package com.harpreetsaund.transactionfileingestor.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.AfterChunkError;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.AfterProcess;
import org.springframework.batch.core.annotation.AfterRead;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.annotation.BeforeProcess;
import org.springframework.batch.core.annotation.BeforeRead;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.core.annotation.OnChunkError;
import org.springframework.batch.core.annotation.OnProcessError;
import org.springframework.batch.core.annotation.OnReadError;
import org.springframework.batch.core.annotation.OnSkipInProcess;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.core.annotation.OnSkipInWrite;
import org.springframework.batch.core.annotation.OnWriteError;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.stereotype.Component;

@Component
public class BatchJobListener {

    private static final Logger logger = LoggerFactory.getLogger(BatchJobListener.class);

    @BeforeJob
    public void beforeJob(JobExecution jobExecution) {
        logger.info("Before executing job: {}", jobExecution);
    }

    @AfterJob
    public void afterJob(JobExecution jobExecution) {
        logger.info("After executing job: {}", jobExecution);
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        logger.info("Before executing step");
    }

    @AfterStep
    public void afterStep(StepExecution stepExecution) {
        logger.info("After executing step: {}", stepExecution.getSummary());
    }

    @BeforeRead
    public void beforeRead() {
        logger.debug("Before reading item");
    }

    @AfterRead
    public void afterRead(Object item) {
        logger.debug("After reading item: {}", item);
    }

    @OnReadError
    public void onReadError(Exception exception) {
        logger.error("Read error: {}", exception.getMessage(), exception);
    }

    @OnSkipInRead
    public void onSkipInRead(Throwable throwable) {
        logger.warn("Skipped item during read: {}", throwable.getMessage());
    }

    @BeforeProcess
    public void beforeProcess(Object item) {
        logger.debug("Before processing item: {}", item);
    }

    @AfterProcess
    public void afterProcess(Object item, Object result) {
        logger.debug("After processing item: {} -> {}", item, result);
    }

    @OnProcessError
    public void onProcessError(Object item, Exception exception) {
        logger.error("Process error for item {}: {}", item, exception.getMessage(), exception);
    }

    @OnSkipInProcess
    public void onSkipInProcess(Object item, Throwable throwable) {
        logger.warn("Skipped item during process: {} - {}", item, throwable.getMessage());
    }

    @BeforeWrite
    public void beforeWrite(Chunk<?> items) {
        logger.debug("Before writing {} items", items.size());
    }

    @AfterWrite
    public void afterWrite(Chunk<?> items) {
        logger.debug("After writing {} items", items.size());
    }

    @OnWriteError
    public void onWriteError(Exception exception, Chunk<?> items) {
        logger.error("Write error for {} items: {}", items.size(), exception.getMessage(), exception);
    }

    @OnSkipInWrite
    public void onSkipInWrite(Object item, Throwable throwable) {
        logger.warn("Skipped item during write: {} - {}", item, throwable.getMessage());
    }

    @BeforeChunk
    public void beforeChunk(Chunk<?> chunk) {
        logger.debug("Before chunk processing");
    }

    @AfterChunk
    public void afterChunk(Chunk<?> chunk) {
        logger.debug("After chunk processing");
    }

    @OnChunkError
    public void onChunkError(ChunkContext context) {
        logger.error("Chunk error occurred");
    }

    @AfterChunkError
    public void afterChunkError(ChunkContext context) {
        logger.error("After chunk error");
    }
}
