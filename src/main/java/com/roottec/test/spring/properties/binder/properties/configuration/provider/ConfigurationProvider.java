package com.roottec.test.spring.properties.binder.properties.configuration.provider;

import com.roottec.test.spring.properties.binder.properties.ConfigurationBase;

public interface ConfigurationProvider {
    Class<? extends ConfigurationBase> getConfigClass();
    String getType();
}
