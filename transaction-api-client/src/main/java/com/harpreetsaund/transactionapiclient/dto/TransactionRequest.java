package com.harpreetsaund.transactionapiclient.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;

@Schema(name = "TransactionRequest", description = "Request object for transaction creation or update operations")
public class TransactionRequest {

    public TransactionRequest() {
    }

    /**
     * Version 1 - Transaction Request Schema with Swagger documentation and
     * validation constraints
     */
    @Schema(name = "TransactionRequestVersion1", description = "Request object for transaction creation or update operations (Versio 1)", example = "{\"eventId\":\"evt-12345\",\"transactionId\":\"txn-98765\",\"accountNumber\":\"ACC-001\",\"transactionType\":\"DEBIT\",\"amount\":100.50,\"currency\":\"USD\",\"merchantName\":\"Sample Merchant\",\"channel\":\"ATM\",\"transactionTimestamp\":\"2026-03-10T10:30:00Z\",\"sourceSystem\":\"CORE\",\"topicName\":\"transaction-events\"}")
    public static class Version1 {

        @Schema(description = "Unique event identifier", example = "evt-12345", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Event ID cannot be blank")
        @Size(max = 255, message = "Event ID must not exceed 255 characters")
        private String eventId;

        @Schema(description = "Unique transaction identifier", example = "txn-98765", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Transaction ID cannot be blank")
        @Size(max = 255, message = "Transaction ID must not exceed 255 characters")
        private String transactionId;

        @Schema(description = "Bank account number", example = "ACC-001", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Account number cannot be blank")
        @Size(max = 255, message = "Account number must not exceed 255 characters")
        private String accountNumber;

        @Schema(description = "Type of transaction (e.g., DEBIT, CREDIT, TRANSFER)", example = "DEBIT", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Transaction type cannot be blank")
        @Size(max = 50, message = "Transaction type must not exceed 50 characters")
        private String transactionType;

        @Schema(description = "Transaction amount", example = "100.50", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Amount cannot be null")
        @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
        @Digits(integer = 16, fraction = 2, message = "Amount must have at most 16 integer digits and 2 decimal places")
        private BigDecimal amount;

        @Schema(description = "Currency code (ISO 4217)", example = "USD", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Currency cannot be blank")
        @Size(min = 3, max = 3, message = "Currency must be exactly 3 characters")
        private String currency;

        @Schema(description = "Name of the merchant", example = "Sample Merchant", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Size(max = 255, message = "Merchant name must not exceed 255 characters")
        private String merchantName;

        @Schema(description = "Transaction channel (e.g., ATM, ONLINE, MOBILE)", example = "ATM", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Size(max = 50, message = "Channel must not exceed 50 characters")
        private String channel;

        @Schema(description = "Timestamp when the transaction occurred", example = "2026-03-10T10:30:00Z", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Transaction timestamp cannot be null")
        private Instant transactionTimestamp;

        @Schema(description = "Source system from which the transaction originated", example = "CORE", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Source system cannot be blank")
        @Size(max = 255, message = "Source system must not exceed 255 characters")
        private String sourceSystem;

        @Schema(description = "Kafka topic name for the transaction event", example = "transaction-events", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Size(max = 255, message = "Topic name must not exceed 255 characters")
        private String topicName;

        public String getEventId() {
            return eventId;
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(String transactionType) {
            this.transactionType = transactionType;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public Instant getTransactionTimestamp() {
            return transactionTimestamp;
        }

        public void setTransactionTimestamp(Instant transactionTimestamp) {
            this.transactionTimestamp = transactionTimestamp;
        }

        public String getSourceSystem() {
            return sourceSystem;
        }

        public void setSourceSystem(String sourceSystem) {
            this.sourceSystem = sourceSystem;
        }

        public String getTopicName() {
            return topicName;
        }

        public void setTopicName(String topicName) {
            this.topicName = topicName;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("eventId", eventId).append("transactionId", transactionId)
                    .append("accountNumber", accountNumber).append("transactionType", transactionType)
                    .append("amount", amount).append("currency", currency).append("merchantName", merchantName)
                    .append("channel", channel).append("transactionTimestamp", transactionTimestamp)
                    .append("sourceSystem", sourceSystem).append("topicName", topicName).toString();
        }
    }
}
