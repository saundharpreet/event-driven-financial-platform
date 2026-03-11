package com.harpreetsaund.transactionapiclient.dto;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

class TransactionResponseTest {

    @Test
    void testToString() {
        TransactionResponse.Version1 response = new TransactionResponse.Version1();
        response.setId(1L);
        response.setEventId("evt-12345");
        response.setTransactionId("txn-98765");
        response.setAccountNumber("ACC-001");
        response.setTransactionType("DEBIT");
        response.setAmount(BigDecimal.valueOf(100.50));
        response.setCurrency("USD");
        response.setMerchantName("Sample Merchant");
        response.setChannel("ATM");
        response.setTransactionTimestamp(Instant.parse("2026-03-10T10:30:00Z"));
        response.setSourceSystem("CORE");
        response.setTopicName("transaction-events");
        response.setCreatedAt(Instant.parse("2026-03-10T10:35:00Z"));

        String toString = response.toString();
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("eventId=evt-12345"));
        assertTrue(toString.contains("transactionId=txn-98765"));
        assertTrue(toString.contains("accountNumber=ACC-001"));
        assertTrue(toString.contains("transactionType=DEBIT"));
        assertTrue(toString.contains("amount=100.5"));
        assertTrue(toString.contains("currency=USD"));
        assertTrue(toString.contains("merchantName=Sample Merchant"));
        assertTrue(toString.contains("channel=ATM"));
        assertTrue(toString.contains("transactionTimestamp=2026-03-10T10:30:00Z"));
        assertTrue(toString.contains("sourceSystem=CORE"));
        assertTrue(toString.contains("topicName=transaction-events"));
        assertTrue(toString.contains("createdAt=2026-03-10T10:35:00Z"));
    }

    @Test
    void testGettersAndSetters() {
        TransactionResponse.Version1 response = new TransactionResponse.Version1();
        Instant txnTime = Instant.parse("2026-03-10T10:30:00Z");
        Instant created = Instant.parse("2026-03-10T10:35:00Z");
        BigDecimal amount = BigDecimal.valueOf(150.00);

        response.setId(2L);
        response.setEventId("evt-2");
        response.setTransactionId("txn-2");
        response.setAccountNumber("ACC-003");
        response.setTransactionType("TRANSFER");
        response.setAmount(amount);
        response.setCurrency("GBP");
        response.setMerchantName("Store");
        response.setChannel("MOBILE");
        response.setTransactionTimestamp(txnTime);
        response.setSourceSystem("APP");
        response.setTopicName("txn-events");
        response.setCreatedAt(created);

        assertEquals(2L, response.getId());
        assertEquals("evt-2", response.getEventId());
        assertEquals("txn-2", response.getTransactionId());
        assertEquals("ACC-003", response.getAccountNumber());
        assertEquals("TRANSFER", response.getTransactionType());
        assertEquals(amount, response.getAmount());
        assertEquals("GBP", response.getCurrency());
        assertEquals("Store", response.getMerchantName());
        assertEquals("MOBILE", response.getChannel());
        assertEquals(txnTime, response.getTransactionTimestamp());
        assertEquals("APP", response.getSourceSystem());
        assertEquals("txn-events", response.getTopicName());
        assertEquals(created, response.getCreatedAt());
    }
}
