package com.roottec.test.spring.properties.binder.properties.configuration.provider;

import com.roottec.test.spring.properties.binder.properties.ConfigurationBase;
import com.roottec.test.spring.properties.binder.properties.ConfigurationClassA;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationProviderA implements ConfigurationProvider {
    @Override
    public Class<? extends ConfigurationBase> getConfigClass() {
        return ConfigurationClassA.class;
    }

    @Override
    public String getType() {
        return "A";
    }
}
