package com.harpreetsaund.transactioneventbridge.service;

import com.harpreetsaund.transactioneventbridge.mapper.TransactionEventMapper;
import com.harpreetsaund.transactioneventbridge.model.MqTransactionEvent;
import com.harpreetsaund.transaction.avro.TransactionEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MessageTransformService Tests")
class MessageTransformServiceTest {

    private TransactionEventMapper transactionEventMapper;

    private MessageTransformService messageTransformService;

    private String testPayload;

    @BeforeEach
    void setUp() {
        transactionEventMapper = new TransactionEventMapper();
        messageTransformService = new MessageTransformService(transactionEventMapper);
        testPayload = "TXN0000009876ACC2001234C0000004500CAD20250612123000AMAZON         ONL";
    }

    @Test
    @DisplayName("Should transform String message to MqTransactionEvent message")
    void testTransformToMqTransactionEventSuccess() {
        // Arrange
        MqTransactionEvent expectedEvent = createTestMqTransactionEvent();
        Message<String> inboundMessage = MessageBuilder.withPayload(testPayload)
                .setHeader("originalHeader", "headerValue").build();

        // Act
        Message<MqTransactionEvent> result = messageTransformService.transformToMqTransactionEvent(inboundMessage);

        // Assert
        assertNotNull(result);
        assertEquals("headerValue", result.getHeaders().get("originalHeader"));
    }

    @Test
    @DisplayName("Should preserve message headers during MqTransactionEvent transformation")
    void testTransformToMqTransactionEventPreservesHeaders() {
        // Arrange
        MqTransactionEvent expectedEvent = createTestMqTransactionEvent();
        Message<String> inboundMessage = MessageBuilder.withPayload(testPayload).setHeader("header1", "value1")
                .setHeader("header2", "value2").setHeader("header3", 123).build();

        // Act
        Message<MqTransactionEvent> result = messageTransformService.transformToMqTransactionEvent(inboundMessage);

        // Assert
        assertEquals("value1", result.getHeaders().get("header1"));
        assertEquals("value2", result.getHeaders().get("header2"));
        assertEquals(123, result.getHeaders().get("header3"));
    }

    @Test
    @DisplayName("Should transform MqTransactionEvent message to TransactionEvent message")
    void testTransformToTransactionEventSuccess() {
        // Arrange
        MqTransactionEvent mqEvent = createTestMqTransactionEvent();
        TransactionEvent expectedEvent = createTestTransactionEvent();

        Message<MqTransactionEvent> inboundMessage = MessageBuilder.withPayload(mqEvent)
                .setHeader("originalHeader", "headerValue").build();

        // Act
        Message<TransactionEvent> result = messageTransformService.transformToTransactionEvent(inboundMessage);

        // Assert
        assertNotNull(result);
        assertEquals("headerValue", result.getHeaders().get("originalHeader"));
    }

    @Test
    @DisplayName("Should set Kafka key header from transaction ID")
    void testTransformToTransactionEventSetsKafkaKey() {
        // Arrange
        MqTransactionEvent mqEvent = createTestMqTransactionEvent();
        TransactionEvent expectedEvent = createTestTransactionEvent();

        Message<MqTransactionEvent> inboundMessage = MessageBuilder.withPayload(mqEvent).build();

        // Act
        Message<TransactionEvent> result = messageTransformService.transformToTransactionEvent(inboundMessage);

        // Assert
        assertEquals("0000009876", result.getHeaders().get("kafka_messageKey"));
    }

    @Test
    @DisplayName("Should preserve message headers during TransactionEvent transformation")
    void testTransformToTransactionEventPreservesHeaders() {
        // Arrange
        MqTransactionEvent mqEvent = createTestMqTransactionEvent();
        TransactionEvent expectedEvent = createTestTransactionEvent();

        Message<MqTransactionEvent> inboundMessage = MessageBuilder.withPayload(mqEvent).setHeader("header1", "value1")
                .setHeader("header2", "value2").build();

        // Act
        Message<TransactionEvent> result = messageTransformService.transformToTransactionEvent(inboundMessage);

        // Assert
        assertEquals("value1", result.getHeaders().get("header1"));
        assertEquals("value2", result.getHeaders().get("header2"));
    }

    @Test
    @DisplayName("Should handle null payload in MqTransactionEvent transformation")
    void testTransformToMqTransactionEventWithNullPayload() {
        // Arrange
        String nullPayload = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> messageTransformService
                .transformToMqTransactionEvent(MessageBuilder.withPayload(nullPayload).build()));
    }

    @Test
    @DisplayName("Should handle null payload in TransactionEvent transformation")
    void testTransformToTransactionEventWithNullPayload() {
        // Arrange
        MqTransactionEvent nullPayload = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> messageTransformService
                .transformToTransactionEvent(MessageBuilder.withPayload(nullPayload).build()));
    }

    @Test
    @DisplayName("Should handle transformation with empty headers")
    void testTransformToMqTransactionEventWithoutHeaders() {
        // Arrange
        MqTransactionEvent expectedEvent = createTestMqTransactionEvent();

        Message<String> inboundMessage = MessageBuilder.withPayload(testPayload).build();

        // Act
        Message<MqTransactionEvent> result = messageTransformService.transformToMqTransactionEvent(inboundMessage);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getHeaders());
    }

    @Test
    @DisplayName("Should maintain payload integrity through transformations")
    void testPayloadIntegrityThroughTransformations() {
        // Arrange
        Message<String> inboundMessage = MessageBuilder.withPayload(testPayload).build();

        // Act
        Message<MqTransactionEvent> mqMessage = messageTransformService.transformToMqTransactionEvent(inboundMessage);
        Message<TransactionEvent> txnMessage = messageTransformService.transformToTransactionEvent(mqMessage);

        // Assert
        assertEquals("0000009876", txnMessage.getPayload().getPayload().getTransactionId());
        assertEquals("0000009876", mqMessage.getPayload().getTransactionId());
    }

    // Helper methods
    private MqTransactionEvent createTestMqTransactionEvent() {
        MqTransactionEvent event = new MqTransactionEvent();
        event.setRecordType("TXN");
        event.setTransactionId("0000009876");
        event.setAccountNumber("ACC2001234");
        event.setTransactionType("C");
        event.setAmount(new BigDecimal("450.00"));
        event.setCurrency("CAD");
        event.setTimestamp("20250612123000");
        event.setMerchantName("AMAZON");
        event.setChannel("ONL");
        return event;
    }

    private TransactionEvent createTestTransactionEvent() {
        return new TransactionEvent();
    }
}
