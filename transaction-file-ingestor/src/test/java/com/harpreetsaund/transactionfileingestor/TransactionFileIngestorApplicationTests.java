package com.harpreetsaund.transactionfileingestor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TransactionFileIngestorApplicationTests {

    @Test
    void contextLoads() {
        String contextLoadsMessage = "Application context loaded successfully.";
        assertNotNull(contextLoadsMessage);
    }
}
