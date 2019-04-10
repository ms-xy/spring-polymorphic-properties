package com.roottec.test.spring.properties.binder.properties.configuration.provider;

import com.roottec.test.spring.properties.binder.properties.ConfigurationBase;
import com.roottec.test.spring.properties.binder.properties.ConfigurationClassB;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationProviderB implements ConfigurationProvider {
    @Override
    public Class<? extends ConfigurationBase> getConfigClass() {
        return ConfigurationClassB.class;
    }

    @Override
    public String getType() {
        return "B";
    }
}
