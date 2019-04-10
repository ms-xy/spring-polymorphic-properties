package com.roottec.test.spring.properties.binder.properties.binding.solution1;

import com.roottec.test.spring.properties.binder.properties.ConfigurationBase;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties
public class ApplicationPropertiesSolution1 {
    private List<ConfigurationBase> configurations;

    public List<ConfigurationBase> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<ConfigurationBase> configurations) {
        this.configurations = configurations;
    }
}
