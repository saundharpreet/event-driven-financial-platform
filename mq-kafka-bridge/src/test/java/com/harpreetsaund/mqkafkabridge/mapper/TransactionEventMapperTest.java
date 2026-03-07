package com.harpreetsaund.mqkafkabridge.mapper;

import com.harpreetsaund.mqkafkabridge.model.MqTransactionEvent;
import com.harpreetsaund.transaction.avro.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TransactionEventMapper Tests")
class TransactionEventMapperTest {

    private TransactionEventMapper transactionEventMapper;

    private String validPayload;

    @BeforeEach
    void setUp() {
        transactionEventMapper = new TransactionEventMapper();

        // Valid 69-character payload format:
        // TXN + TransactionId(10) + AccountNumber(10) + Type(1) + Amount(10) +
        // Currency(3) + Timestamp(14) + MerchantName(15) + Channel(3)
        // 3 + 10 + 10 + 1 + 10 + 3 + 14 + 15 + 3 = 69 characters
        validPayload = "TXN0000009876ACC2001234C0000004500CAD20250612123000AMAZON         ONL";
    }

    @Test
    @DisplayName("Should map payload string to MqTransactionEvent successfully")
    void testToMqTransactionEventWithValidPayload() {
        // Act
        MqTransactionEvent result = transactionEventMapper.toMqTransactionEvent(validPayload);

        // Assert
        assertNotNull(result);
        assertEquals("TXN", result.getRecordType());
        assertEquals("0000009876", result.getTransactionId());
        assertEquals("ACC2001234", result.getAccountNumber());
        assertEquals("C", result.getTransactionType());
        assertEquals(new BigDecimal("4500"), result.getAmount());
        assertEquals("CAD", result.getCurrency());
        assertEquals("20250612123000", result.getTimestamp());
        assertEquals("AMAZON", result.getMerchantName());
        assertEquals("ONL", result.getChannel());
    }

    @Test
    @DisplayName("Should map MqTransactionEvent to TransactionEvent with all fields populated")
    void testToTransactionEventWithValidMqTransactionEvent() {
        // Arrange
        MqTransactionEvent mqEvent = createTestMqTransactionEvent();

        // Act
        TransactionEvent result = transactionEventMapper.toTransactionEvent(mqEvent);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getHeaders());
        assertNotNull(result.getPayload());

        EventHeaders headers = result.getHeaders();
        assertEquals("EodTransactionEvent", headers.getEventType());
        assertEquals("SFTP", headers.getSourceSystem());
        assertEquals("Kafka", headers.getTargetSystem());
        assertEquals("1", headers.getPayloadSchemaVersion());
        assertNotNull(headers.getEventId());
        assertNotNull(headers.getEventTimestamp());

        EventPayload payload = result.getPayload();
        assertEquals("1234567890", payload.getTransactionId());
        assertEquals("1234567890", payload.getAccountNumber());
        assertEquals(TransactionType.DEBIT, payload.getTransactionType());
        assertEquals(1234.0, payload.getAmount());
        assertEquals(Currency.CAD, payload.getCurrency());
        assertEquals("Acme Store AB", payload.getMerchantName());
        assertEquals(Channel.ATM, payload.getChannel());
    }

    @Test
    @DisplayName("Should map transaction type D to DEBIT")
    void testMapTransactionTypeDebit() {
        // Act
        TransactionType result = transactionEventMapper.mapTransactionType("D");

        // Assert
        assertEquals(TransactionType.DEBIT, result);
    }

    @Test
    @DisplayName("Should map transaction type C to CREDIT")
    void testMapTransactionTypeCredit() {
        // Act
        TransactionType result = transactionEventMapper.mapTransactionType("C");

        // Assert
        assertEquals(TransactionType.CREDIT, result);
    }

    @Test
    @DisplayName("Should throw exception for unknown transaction type")
    void testMapTransactionTypeWithUnknownType() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> transactionEventMapper.mapTransactionType("X"));
    }

    @Test
    @DisplayName("Should throw exception for null transaction type")
    void testMapTransactionTypeWithNullType() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> transactionEventMapper.mapTransactionType(null));
    }

    @Test
    @DisplayName("Should map currency CAD")
    void testMapCurrencyCAD() {
        // Act
        Currency result = transactionEventMapper.mapCurrency("CAD");

        // Assert
        assertEquals(Currency.CAD, result);
    }

    @Test
    @DisplayName("Should map currency USD")
    void testMapCurrencyUSD() {
        // Act
        Currency result = transactionEventMapper.mapCurrency("USD");

        // Assert
        assertEquals(Currency.USD, result);
    }

    @Test
    @DisplayName("Should map currency EUR")
    void testMapCurrencyEUR() {
        // Act
        Currency result = transactionEventMapper.mapCurrency("EUR");

        // Assert
        assertEquals(Currency.EUR, result);
    }

    @Test
    @DisplayName("Should map currency GBP")
    void testMapCurrencyGBP() {
        // Act
        Currency result = transactionEventMapper.mapCurrency("GBP");

        // Assert
        assertEquals(Currency.GBP, result);
    }

    @Test
    @DisplayName("Should throw exception for unknown currency")
    void testMapCurrencyWithUnknownCurrency() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> transactionEventMapper.mapCurrency("XXX"));
    }

    @Test
    @DisplayName("Should throw exception for null currency")
    void testMapCurrencyWithNullCurrency() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> transactionEventMapper.mapCurrency(null));
    }

    @Test
    @DisplayName("Should map channel ATM")
    void testMapChannelATM() {
        // Act
        Channel result = transactionEventMapper.mapChannel("ATM");

        // Assert
        assertEquals(Channel.ATM, result);
    }

    @Test
    @DisplayName("Should map channel POS")
    void testMapChannelPOS() {
        // Act
        Channel result = transactionEventMapper.mapChannel("POS");

        // Assert
        assertEquals(Channel.POS, result);
    }

    @Test
    @DisplayName("Should map channel ONL to ONLINE")
    void testMapChannelONL() {
        // Act
        Channel result = transactionEventMapper.mapChannel("ONL");

        // Assert
        assertEquals(Channel.ONLINE, result);
    }

    @Test
    @DisplayName("Should map channel MOB to MOBILE")
    void testMapChannelMOB() {
        // Act
        Channel result = transactionEventMapper.mapChannel("MOB");

        // Assert
        assertEquals(Channel.MOBILE, result);
    }

    @Test
    @DisplayName("Should map channel ETR to ETRANSFER")
    void testMapChannelETR() {
        // Act
        Channel result = transactionEventMapper.mapChannel("ETR");

        // Assert
        assertEquals(Channel.ETRANSFER, result);
    }

    @Test
    @DisplayName("Should throw exception for unknown channel")
    void testMapChannelWithUnknownChannel() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> transactionEventMapper.mapChannel("XXX"));
    }

    @Test
    @DisplayName("Should throw exception for null channel")
    void testMapChannelWithNullChannel() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> transactionEventMapper.mapChannel(null));
    }

    @Test
    @DisplayName("Should handle case insensitive transaction type mapping")
    void testMapTransactionTypeCaseInsensitive() {
        // Act
        TransactionType resultLower = transactionEventMapper.mapTransactionType("d");
        TransactionType resultUpper = transactionEventMapper.mapTransactionType("D");

        // Assert
        assertEquals(resultLower, resultUpper);
    }

    @Test
    @DisplayName("Should handle case insensitive currency mapping")
    void testMapCurrencyCaseInsensitive() {
        // Act
        Currency resultLower = transactionEventMapper.mapCurrency("cad");
        Currency resultUpper = transactionEventMapper.mapCurrency("CAD");

        // Assert
        assertEquals(resultLower, resultUpper);
    }

    @Test
    @DisplayName("Should handle case insensitive channel mapping")
    void testMapChannelCaseInsensitive() {
        // Act
        Channel resultLower = transactionEventMapper.mapChannel("atm");
        Channel resultUpper = transactionEventMapper.mapChannel("ATM");

        // Assert
        assertEquals(resultLower, resultUpper);
    }

    @Test
    @DisplayName("Should map timestamp correctly to Instant")
    void testMapTimestamp() {
        // Act
        Instant result = transactionEventMapper.mapTimestamp("20230101120000");

        // Assert
        assertNotNull(result);
        // Verify the Instant is valid
        assertEquals(1672574400L, result.getEpochSecond());
    }

    @Test
    @DisplayName("Should map different timestamps correctly")
    void testMapMultipleTimestamps() {
        // Act
        Instant result1 = transactionEventMapper.mapTimestamp("20231225235959");
        Instant result2 = transactionEventMapper.mapTimestamp("20230615120000");

        // Assert
        assertNotNull(result1);
        assertNotNull(result2);
        assertTrue(result1.isAfter(result2));
    }

    @Test
    @DisplayName("Should preserve transaction ID through mapping pipeline")
    void testTransactionIdPreservationThroughMapping() {
        // Act
        MqTransactionEvent mqEvent = transactionEventMapper.toMqTransactionEvent(validPayload);
        TransactionEvent txnEvent = transactionEventMapper.toTransactionEvent(mqEvent);

        // Assert
        assertEquals("0000009876", txnEvent.getPayload().getTransactionId());
        assertEquals("0000009876", mqEvent.getTransactionId());
    }

    @Test
    @DisplayName("Should preserve account number through mapping pipeline")
    void testAccountNumberPreservationThroughMapping() {
        // Act
        MqTransactionEvent mqEvent = transactionEventMapper.toMqTransactionEvent(validPayload);
        TransactionEvent txnEvent = transactionEventMapper.toTransactionEvent(mqEvent);

        // Assert
        assertEquals("ACC2001234", txnEvent.getPayload().getAccountNumber());
        assertEquals("ACC2001234", mqEvent.getAccountNumber());
    }

    // Helper method
    private MqTransactionEvent createTestMqTransactionEvent() {
        MqTransactionEvent event = new MqTransactionEvent();
        event.setRecordType("TXN");
        event.setTransactionId("1234567890");
        event.setAccountNumber("1234567890");
        event.setTransactionType("D");
        event.setAmount(new BigDecimal("1234"));
        event.setCurrency("CAD");
        event.setTimestamp("20230101120000");
        event.setMerchantName("Acme Store AB");
        event.setChannel("ATM");
        return event;
    }
}
