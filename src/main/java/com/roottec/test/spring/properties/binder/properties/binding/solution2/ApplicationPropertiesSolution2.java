package com.roottec.test.spring.properties.binder.properties.binding.solution2;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties
public class ApplicationPropertiesSolution2 {
    private List<Map<String, Object>> configurations;

    public List<Map<String, Object>> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<Map<String, Object>> configurations) {
        this.configurations = configurations;
    }
}
