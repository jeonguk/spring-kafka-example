package com.jeonguk.kafka;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import com.jeonguk.kafka.incoming.BookPublisher;
import com.jeonguk.kafka.model.Book;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class ProducerApplication {
    
    @Autowired
    private BookPublisher bookPublisher;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(ProducerApplication.class).web(false).run(args);
        context.getBean(ProducerApplication.class).run(context);
        context.close();
    }

    private void run(ConfigurableApplicationContext context) {
        log.info("Inside ProducerApplication run method...");
        MessageChannel producerChannel = context.getBean("producerChannel", MessageChannel.class);

        List<Book> books = bookPublisher.getBooks();

        for (Book book : books) {
            Map<String, Object> headers = Collections.singletonMap(KafkaHeaders.TOPIC, book.getGenre().toString());
            producerChannel.send(new GenericMessage<>(book.toString(), headers));
        }
        log.info("Finished ProducerApplication run method...");
    }
    
}