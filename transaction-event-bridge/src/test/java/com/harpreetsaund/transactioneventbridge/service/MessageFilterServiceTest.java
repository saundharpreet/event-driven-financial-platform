package com.harpreetsaund.transactioneventbridge.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MessageFilterService Tests")
class MessageFilterServiceTest {

    private MessageFilterService messageFilterService;

    @BeforeEach
    void setUp() {
        messageFilterService = new MessageFilterService();
    }

    @Test
    @DisplayName("Should accept valid transaction message with 69 characters starting with TXN")
    void testFilterInboundMessageWithValidTransaction() {
        // Arrange
        String validPayload = "TXN0000009876ACC2001234C0000004500CAD20250612123000AMAZON         ONL";
        Message<String> inboundMessage = MessageBuilder.withPayload(validPayload).build();

        // Act
        Boolean result = messageFilterService.filterInboundMessage(inboundMessage);

        // Assert
        assertTrue(result, "Should accept valid transaction message");
    }

    @Test
    @DisplayName("Should reject message not starting with TXN")
    void testFilterInboundMessageWithInvalidPrefix() {
        // Arrange
        String invalidPayload = "ABC1234567890123456789012345678901234567890123456789012345678";
        Message<String> inboundMessage = MessageBuilder.withPayload(invalidPayload).build();

        // Act
        Boolean result = messageFilterService.filterInboundMessage(inboundMessage);

        // Assert
        assertFalse(result, "Should reject message with invalid prefix");
    }

    @Test
    @DisplayName("Should reject message with incorrect length")
    void testFilterInboundMessageWithInvalidLength() {
        // Arrange - 68 characters instead of 69
        String shortPayload = "TXN123456789012345678901234567890123456789012345678901234567";
        Message<String> inboundMessage = MessageBuilder.withPayload(shortPayload).build();

        // Act
        Boolean result = messageFilterService.filterInboundMessage(inboundMessage);

        // Assert
        assertFalse(result, "Should reject message with incorrect length");
    }

    @Test
    @DisplayName("Should reject message longer than 69 characters")
    void testFilterInboundMessageWithTooLongLength() {
        // Arrange - 70 characters instead of 69
        String longPayload = "TXN12345678901234567890123456789012345678901234567890123456789X";
        Message<String> inboundMessage = MessageBuilder.withPayload(longPayload).build();

        // Act
        Boolean result = messageFilterService.filterInboundMessage(inboundMessage);

        // Assert
        assertFalse(result, "Should reject message longer than 69 characters");
    }

    @Test
    @DisplayName("Should reject empty message")
    void testFilterInboundMessageWithEmptyPayload() {
        // Arrange
        String emptyPayload = "";
        Message<String> inboundMessage = MessageBuilder.withPayload(emptyPayload).build();

        // Act
        Boolean result = messageFilterService.filterInboundMessage(inboundMessage);

        // Assert
        assertFalse(result, "Should reject empty message");
    }

    @Test
    @DisplayName("Should reject null message payload")
    void testFilterInboundMessageWithNullPayload() {
        // Arrange
        String nullPayload = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> messageFilterService.filterInboundMessage(MessageBuilder.withPayload(nullPayload).build()),
                "Should throw NullPointerException for null payload");
    }

    @Test
    @DisplayName("Should accept message with TXN prefix and exactly 69 characters")
    void testFilterInboundMessageWithExactBoundary() {
        // Arrange - exactly 69 characters with TXN prefix
        String boundaryPayload = "TXN0000009876ACC2001234C0000004500CAD20250612123000AMAZON         ONL";
        Message<String> inboundMessage = MessageBuilder.withPayload(boundaryPayload).build();

        // Act
        Boolean result = messageFilterService.filterInboundMessage(inboundMessage);

        // Assert
        assertTrue(result, "Should accept message with exactly 69 characters starting with TXN");
        assertEquals(69, boundaryPayload.length());
    }
}
