package com.roottec.test.spring.properties.binder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ComponentUsingProperties {
    private static final Logger logger = LoggerFactory.getLogger(ComponentUsingProperties.class);
    private final Properties properties;

    public ComponentUsingProperties(Properties properties) {
        this.properties = properties;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doStuff() throws InterruptedException {
        logger.warn("properties: {}", properties.getProperties());
        logger.info("sleeping for 5s then terminating");
        Thread.sleep(5000);
    }
}
