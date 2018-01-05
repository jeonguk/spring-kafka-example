package com.jeonguk.kafka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.dsl.kafka.Kafka;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class ConsumerApplication {
    
    @Autowired
    PollableChannel consumerChannel;

    public static void main(String[] args) {

        ConfigurableApplicationContext context = new SpringApplicationBuilder(ConsumerApplication.class).run(args);

        List<String> validtopics = Arrays.asList("FANTASY", "HORROR", "ROMANCE", "THRILLER");

        List<String> topics = new ArrayList<>();
        if (args.length > 0) {
            for (String arg : args) {
                if (validtopics.contains(arg))
                    topics.add(arg);
            }
        }

        context.getBean(ConsumerApplication.class).run(context, topics);
        context.close();
    }

    private void run(ConfigurableApplicationContext context, List<String> topics) {
        log.info("Inside ProducerApplication run method...");
        PollableChannel consumerChannel = context.getBean("consumerChannel", PollableChannel.class);

        for (String topic : topics)
            addAnotherListenerForTopics(topic);

        Message<?> received = consumerChannel.receive();
        while (received != null) {
            received = consumerChannel.receive();
            //System.out.println("Received " + received.getPayload());
            log.info("Received" + received.getPayload());
        }
    }

    @Autowired
    private IntegrationFlowContext flowContext;

    @Autowired
    private KafkaProperties kafkaProperties;

    public void addAnotherListenerForTopics(String... topics) {
        Map<String, Object> consumerProperties = kafkaProperties.buildConsumerProperties();
        IntegrationFlow flow = IntegrationFlows
                .from(Kafka.messageDrivenChannelAdapter(
                        new DefaultKafkaConsumerFactory<String, String>(consumerProperties), topics))
                .channel("consumerChannel").get();
        this.flowContext.registration(flow).register();
    }
    
}
