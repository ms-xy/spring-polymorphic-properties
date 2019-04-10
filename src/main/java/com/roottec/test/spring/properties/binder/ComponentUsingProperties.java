package com.roottec.test.spring.properties.binder;

import com.roottec.test.spring.properties.binder.properties.binding.solution1.ApplicationPropertiesSolution1;
import com.roottec.test.spring.properties.binder.properties.binding.solution2.PropertiesProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ComponentUsingProperties {
    private static final Logger logger = LoggerFactory.getLogger(ComponentUsingProperties.class);
    private final ApplicationPropertiesSolution1 applicationProperties;
    private final PropertiesProvider propertiesProvider;

    public ComponentUsingProperties(ApplicationPropertiesSolution1 applicationProperties, PropertiesProvider propertiesProvider) {
        this.applicationProperties = applicationProperties;
        this.propertiesProvider = propertiesProvider;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doStuff() throws InterruptedException {
        logger.info("ApplicationProperties (Solution 1): {}", applicationProperties.getConfigurations());
        logger.info("PropertiesProvider (Solution 2): {}", propertiesProvider.getConfigurations());
    }
}
