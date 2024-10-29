package com.isw.ussd.whitelable.portal.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqProducer {

    private static final Logger log = LoggerFactory.getLogger(RabbitMqProducer.class);
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void produceMessage(String exchange, String routingKey, String msg) {
        amqpTemplate.convertAndSend(exchange, routingKey, msg, m -> {
            m.getMessageProperties().setContentType(MediaType.APPLICATION_JSON_VALUE);
            return m;
        });
        log.info(String.format("Send RabbitMQ message to %s:%s", exchange, routingKey));
    }
}
