package com.roottec.test.spring.properties.binder.properties.binding.solution2;

import com.roottec.test.spring.properties.binder.properties.ConfigurationBase;

import java.util.List;

public class PropertiesProvider {
    private final List<ConfigurationBase> configurations;

    public PropertiesProvider(List<ConfigurationBase> configurations) {
        this.configurations = configurations;
    }

    public List<ConfigurationBase> getConfigurations() {
        return configurations;
    }
}
