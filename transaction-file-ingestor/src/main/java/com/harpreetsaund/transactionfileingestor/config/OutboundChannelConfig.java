package com.harpreetsaund.transactionfileingestor.config;

import com.harpreetsaund.transaction.avro.EodTransactionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageHandler;

@Configuration
public class OutboundChannelConfig implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(OutboundChannelConfig.class);

    @Value("${outbound-channel.topic-name}")
    private String outboundTopicName;

    @Bean
    public DirectChannel batchJobToKafkaRequestChannel() {
        return new DirectChannel();
    }

    @Bean
    public QueueChannel batchJobToKafkaReplyChannel() {
        return new QueueChannel();
    }

    @Bean
    public DirectChannel kafkaSendSuccessChannel() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel kafkaSendFailureChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessagingTemplate messagingTemplate(DirectChannel batchJobToKafkaRequestChannel) {
        MessagingTemplate messagingTemplate = new MessagingTemplate();
        messagingTemplate.setDefaultChannel(batchJobToKafkaRequestChannel);

        return messagingTemplate;
    }

    @Bean
    public IntegrationFlow outboundKafkaFlow(KafkaTemplate<String, EodTransactionEvent> kafkaTemplate) {
        return IntegrationFlow.from("batchJobToKafkaRequestChannel")
                .split()
                .log()
                .handle(Kafka.outboundChannelAdapter(kafkaTemplate)
                        .topic(outboundTopicName)
                        .sendSuccessChannel("kafkaSendSuccessChannel")
                        .sendFailureChannel("kafkaSendFailureChannel"))
                .aggregate()
                .channel("batchJobToKafkaReplyChannel")
                .get();
    }

    @Bean
    @ServiceActivator(inputChannel = "kafkaSendSuccessChannel")
    public MessageHandler kafkaSendSuccessHandler() {
        return message -> logger.debug("Message sent to Kafka successfully: {}", message);
    }

    @Bean
    @ServiceActivator(inputChannel = "kafkaSendFailureChannel")
    public MessageHandler kafkaSendFailureHandler() {
        return message -> logger.error("Failed to send message to Kafka: {}", message);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("Outbound channel configuration enabled.");
        logger.info("outbound-channel.topic-name: {}", outboundTopicName);
    }
}
