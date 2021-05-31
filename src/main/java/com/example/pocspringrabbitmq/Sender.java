package com.example.pocspringrabbitmq;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class Sender {

	@Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange direct;

    AtomicInteger index = new AtomicInteger(0);

    AtomicInteger count = new AtomicInteger(0);
    
    AtomicInteger priority = new AtomicInteger(0);

    private final String[] keys = {"report", "vendor", "others"};

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        StringBuilder builder = new StringBuilder("-MSG- ");
        if (this.index.incrementAndGet() == 3) {
            this.index.set(0);
        }
        if (this.priority.get() == 0) {
        	this.priority.set(10);
        }
        else
        	this.priority.set(0);
        String key = keys[this.index.get()];
        builder.append(key).append(" -PRI- ").append(this.priority);
        String msg = builder.toString();
        template.convertAndSend(direct.getName(), key, msg,
        		message -> {
        			message.getMessageProperties().setPriority(this.priority.get());
        			return message;
        		});
        //template.convertAndSend(direct.getName(), key, msg);
        System.out.println("[S] '" + msg + "'");
    }
}