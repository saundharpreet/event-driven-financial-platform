package com.harpreetsaund.transactioneventbridge.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MqTransactionEvent Model Tests")
class MqTransactionEventTest {

    private MqTransactionEvent mqTransactionEvent;

    @BeforeEach
    void setUp() {
        mqTransactionEvent = new MqTransactionEvent();
    }

    @Test
    @DisplayName("Should create MqTransactionEvent with default values")
    void testCreateMqTransactionEvent() {
        // Assert
        assertNotNull(mqTransactionEvent);
        assertNull(mqTransactionEvent.getRecordType());
        assertNull(mqTransactionEvent.getTransactionId());
        assertNull(mqTransactionEvent.getAccountNumber());
        assertNull(mqTransactionEvent.getTransactionType());
        assertNull(mqTransactionEvent.getAmount());
        assertNull(mqTransactionEvent.getCurrency());
        assertNull(mqTransactionEvent.getTimestamp());
        assertNull(mqTransactionEvent.getMerchantName());
        assertNull(mqTransactionEvent.getChannel());
    }

    @Test
    @DisplayName("Should set and get record type")
    void testSetAndGetRecordType() {
        // Act
        mqTransactionEvent.setRecordType("TXN");

        // Assert
        assertEquals("TXN", mqTransactionEvent.getRecordType());
    }

    @Test
    @DisplayName("Should set and get transaction ID")
    void testSetAndGetTransactionId() {
        // Act
        mqTransactionEvent.setTransactionId("1234567890");

        // Assert
        assertEquals("1234567890", mqTransactionEvent.getTransactionId());
    }

    @Test
    @DisplayName("Should set and get account number")
    void testSetAndGetAccountNumber() {
        // Act
        mqTransactionEvent.setAccountNumber("0987654321");

        // Assert
        assertEquals("0987654321", mqTransactionEvent.getAccountNumber());
    }

    @Test
    @DisplayName("Should set and get transaction type")
    void testSetAndGetTransactionType() {
        // Act
        mqTransactionEvent.setTransactionType("D");

        // Assert
        assertEquals("D", mqTransactionEvent.getTransactionType());
    }

    @Test
    @DisplayName("Should set and get amount as BigDecimal")
    void testSetAndGetAmount() {
        // Arrange
        BigDecimal expectedAmount = new BigDecimal("1234.56");

        // Act
        mqTransactionEvent.setAmount(expectedAmount);

        // Assert
        assertEquals(expectedAmount, mqTransactionEvent.getAmount());
    }

    @Test
    @DisplayName("Should set and get currency")
    void testSetAndGetCurrency() {
        // Act
        mqTransactionEvent.setCurrency("CAD");

        // Assert
        assertEquals("CAD", mqTransactionEvent.getCurrency());
    }

    @Test
    @DisplayName("Should set and get timestamp")
    void testSetAndGetTimestamp() {
        // Act
        mqTransactionEvent.setTimestamp("20230101120000");

        // Assert
        assertEquals("20230101120000", mqTransactionEvent.getTimestamp());
    }

    @Test
    @DisplayName("Should set and get merchant name")
    void testSetAndGetMerchantName() {
        // Act
        mqTransactionEvent.setMerchantName("Acme Store");

        // Assert
        assertEquals("Acme Store", mqTransactionEvent.getMerchantName());
    }

    @Test
    @DisplayName("Should set and get channel")
    void testSetAndGetChannel() {
        // Act
        mqTransactionEvent.setChannel("ATM");

        // Assert
        assertEquals("ATM", mqTransactionEvent.getChannel());
    }

    @Test
    @DisplayName("Should populate all fields and retrieve them correctly")
    void testPopulateAllFieldsSuccessfully() {
        // Arrange
        String recordType = "TXN";
        String transactionId = "1234567890";
        String accountNumber = "0987654321";
        String transactionType = "D";
        BigDecimal amount = new BigDecimal("9999.99");
        String currency = "USD";
        String timestamp = "20231225120000";
        String merchantName = "Test Merchant";
        String channel = "POS";

        // Act
        mqTransactionEvent.setRecordType(recordType);
        mqTransactionEvent.setTransactionId(transactionId);
        mqTransactionEvent.setAccountNumber(accountNumber);
        mqTransactionEvent.setTransactionType(transactionType);
        mqTransactionEvent.setAmount(amount);
        mqTransactionEvent.setCurrency(currency);
        mqTransactionEvent.setTimestamp(timestamp);
        mqTransactionEvent.setMerchantName(merchantName);
        mqTransactionEvent.setChannel(channel);

        // Assert
        assertEquals(recordType, mqTransactionEvent.getRecordType());
        assertEquals(transactionId, mqTransactionEvent.getTransactionId());
        assertEquals(accountNumber, mqTransactionEvent.getAccountNumber());
        assertEquals(transactionType, mqTransactionEvent.getTransactionType());
        assertEquals(amount, mqTransactionEvent.getAmount());
        assertEquals(currency, mqTransactionEvent.getCurrency());
        assertEquals(timestamp, mqTransactionEvent.getTimestamp());
        assertEquals(merchantName, mqTransactionEvent.getMerchantName());
        assertEquals(channel, mqTransactionEvent.getChannel());
    }

    @Test
    @DisplayName("Should handle BigDecimal amount with various precisions")
    void testAmountWithVariousPrecisions() {
        // Arrange
        BigDecimal amount1 = new BigDecimal("100");
        BigDecimal amount2 = new BigDecimal("100.5");
        BigDecimal amount3 = new BigDecimal("100.99");
        BigDecimal amount4 = new BigDecimal("0.01");

        // Act & Assert
        mqTransactionEvent.setAmount(amount1);
        assertEquals(amount1, mqTransactionEvent.getAmount());

        mqTransactionEvent.setAmount(amount2);
        assertEquals(amount2, mqTransactionEvent.getAmount());

        mqTransactionEvent.setAmount(amount3);
        assertEquals(amount3, mqTransactionEvent.getAmount());

        mqTransactionEvent.setAmount(amount4);
        assertEquals(amount4, mqTransactionEvent.getAmount());
    }

    @Test
    @DisplayName("Should handle null amount")
    void testHandleNullAmount() {
        // Act
        mqTransactionEvent.setAmount(null);

        // Assert
        assertNull(mqTransactionEvent.getAmount());
    }

    @Test
    @DisplayName("Should handle empty strings")
    void testHandleEmptyStrings() {
        // Act
        mqTransactionEvent.setRecordType("");
        mqTransactionEvent.setTransactionId("");
        mqTransactionEvent.setAccountNumber("");
        mqTransactionEvent.setTransactionType("");
        mqTransactionEvent.setCurrency("");
        mqTransactionEvent.setTimestamp("");
        mqTransactionEvent.setMerchantName("");
        mqTransactionEvent.setChannel("");

        // Assert
        assertEquals("", mqTransactionEvent.getRecordType());
        assertEquals("", mqTransactionEvent.getTransactionId());
        assertEquals("", mqTransactionEvent.getAccountNumber());
        assertEquals("", mqTransactionEvent.getTransactionType());
        assertEquals("", mqTransactionEvent.getCurrency());
        assertEquals("", mqTransactionEvent.getTimestamp());
        assertEquals("", mqTransactionEvent.getMerchantName());
        assertEquals("", mqTransactionEvent.getChannel());
    }

    @Test
    @DisplayName("Should generate toString representation")
    void testToStringRepresentation() {
        // Arrange
        mqTransactionEvent.setRecordType("TXN");
        mqTransactionEvent.setTransactionId("1234567890");
        mqTransactionEvent.setAccountNumber("0987654321");
        mqTransactionEvent.setTransactionType("D");
        mqTransactionEvent.setAmount(new BigDecimal("1000.00"));
        mqTransactionEvent.setCurrency("CAD");
        mqTransactionEvent.setTimestamp("20230101120000");
        mqTransactionEvent.setMerchantName("Test Merchant");
        mqTransactionEvent.setChannel("ATM");

        // Act
        String result = mqTransactionEvent.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("recordType"));
        assertTrue(result.contains("transactionId"));
        assertTrue(result.contains("accountNumber"));
        assertTrue(result.contains("transactionType"));
        assertTrue(result.contains("amount"));
        assertTrue(result.contains("currency"));
        assertTrue(result.contains("timestamp"));
        assertTrue(result.contains("merchantName"));
        assertTrue(result.contains("channel"));
    }

    @Test
    @DisplayName("Should maintain immutability of BigDecimal amount")
    void testBigDecimalImmutability() {
        // Arrange
        BigDecimal originalAmount = new BigDecimal("1000.00");
        BigDecimal mutableAmount = new BigDecimal("1000.00");

        // Act
        mqTransactionEvent.setAmount(mutableAmount);
        BigDecimal retrievedAmount = mqTransactionEvent.getAmount();

        // Assert
        assertEquals(originalAmount, retrievedAmount);
    }

    @Test
    @DisplayName("Should allow updating amount multiple times")
    void testUpdateAmountMultipleTimes() {
        // Arrange
        BigDecimal amount1 = new BigDecimal("100.00");
        BigDecimal amount2 = new BigDecimal("200.00");
        BigDecimal amount3 = new BigDecimal("300.00");

        // Act
        mqTransactionEvent.setAmount(amount1);
        assertEquals(amount1, mqTransactionEvent.getAmount());

        mqTransactionEvent.setAmount(amount2);
        assertEquals(amount2, mqTransactionEvent.getAmount());

        mqTransactionEvent.setAmount(amount3);
        assertEquals(amount3, mqTransactionEvent.getAmount());

        // Assert
        assertEquals(amount3, mqTransactionEvent.getAmount());
    }

    @Test
    @DisplayName("Should allow updating string fields multiple times")
    void testUpdateStringFieldsMultipleTimes() {
        // Act & Assert
        mqTransactionEvent.setTransactionId("ID1");
        assertEquals("ID1", mqTransactionEvent.getTransactionId());

        mqTransactionEvent.setTransactionId("ID2");
        assertEquals("ID2", mqTransactionEvent.getTransactionId());

        mqTransactionEvent.setTransactionId("ID3");
        assertEquals("ID3", mqTransactionEvent.getTransactionId());
    }
}
