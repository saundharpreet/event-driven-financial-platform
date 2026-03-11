package com.harpreetsaund.transactionapiclient.dto;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

class TransactionRequestTest {

    @Test
    void testToString() {
        TransactionRequest.Version1 request = new TransactionRequest.Version1();
        request.setEventId("evt-12345");
        request.setTransactionId("txn-98765");
        request.setAccountNumber("ACC-001");
        request.setTransactionType("DEBIT");
        request.setAmount(BigDecimal.valueOf(100.50));
        request.setCurrency("USD");
        request.setMerchantName("Sample Merchant");
        request.setChannel("ATM");
        request.setTransactionTimestamp(Instant.parse("2026-03-10T10:30:00Z"));
        request.setSourceSystem("CORE");
        request.setTopicName("transaction-events");

        String toString = request.toString();
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
    }

    @Test
    void testGettersAndSetters() {
        TransactionRequest.Version1 request = new TransactionRequest.Version1();
        Instant timestamp = Instant.parse("2026-03-10T10:30:00Z");
        BigDecimal amount = BigDecimal.valueOf(200.00);

        request.setEventId("evt-1");
        request.setTransactionId("txn-1");
        request.setAccountNumber("ACC-002");
        request.setTransactionType("CREDIT");
        request.setAmount(amount);
        request.setCurrency("EUR");
        request.setMerchantName("Shop");
        request.setChannel("ONLINE");
        request.setTransactionTimestamp(timestamp);
        request.setSourceSystem("BANK");
        request.setTopicName("events");

        assertEquals("evt-1", request.getEventId());
        assertEquals("txn-1", request.getTransactionId());
        assertEquals("ACC-002", request.getAccountNumber());
        assertEquals("CREDIT", request.getTransactionType());
        assertEquals(amount, request.getAmount());
        assertEquals("EUR", request.getCurrency());
        assertEquals("Shop", request.getMerchantName());
        assertEquals("ONLINE", request.getChannel());
        assertEquals(timestamp, request.getTransactionTimestamp());
        assertEquals("BANK", request.getSourceSystem());
        assertEquals("events", request.getTopicName());
    }
}
