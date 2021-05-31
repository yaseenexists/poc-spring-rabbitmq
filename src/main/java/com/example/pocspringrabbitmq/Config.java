package com.example.pocspringrabbitmq;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"poc"})
@Configuration
public class Config {
    
	static final String reportQueueName = "report-generation-queue";
	static final String vendorQueueName = "vendor-data-request-queue";
	static final String deadLetterQueueName = "dead-letter-queue";

	@Bean
    public DirectExchange direct() {
        return new DirectExchange("exchange.regular");
    }
	
	@Bean
	public DirectExchange deadLetterExchange() {
		return new DirectExchange("exchange.dead");
	}
	
    @Profile("r")
    private static class ReceiverConfig {

    	@Bean
    	Queue reportQueue() {
    		Map<String, Object> args = new HashMap<String, Object>();
    		args.put("x-max-priority", 10);
    		args.put("x-dead-letter-exchange", "deadLetterExchange");
    		return new Queue(reportQueueName, true, false, false, args);
    	}

    	@Bean
    	Queue vendorQueue() {
    		Map<String, Object> args = new HashMap<String, Object>();
    		args.put("x-max-priority", 10);
    		args.put("x-dead-letter-exchange", "deadLetterExchange");
    		return new Queue(vendorQueueName, true, false, false, args);
    	}

        @Bean
        public Binding bindingReport(DirectExchange direct,
            Queue reportQueue) {
            return BindingBuilder.bind(reportQueue)
                .to(direct)
                .with("report");
        }

        /*@Bean
        public Binding bindingDeadReport(DirectExchange deadLetterExchange,
            Queue reportQueue) {
            return BindingBuilder.bind(reportQueue)
                .to(deadLetterExchange)
                .with("deaded");
        }*/

        @Bean
        public Binding bindingVendor(DirectExchange direct,
            Queue vendorQueue) {
            return BindingBuilder.bind(vendorQueue)
                .to(direct)
                .with("vendor");
        }

        /*@Bean
        public Binding bindingDeadVendor(DirectExchange deadLetterExchange,
            Queue vendorQueue) {
            return BindingBuilder.bind(vendorQueue)
                .to(deadLetterExchange)
                .with("dead");
        }*/

        @Bean
        public Receiver receiver() {
            return new Receiver();
        }
    }

    @Profile("s")
    @Bean
    public Sender sender() {
        return new Sender();
    }
}