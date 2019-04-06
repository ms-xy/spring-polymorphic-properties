package com.roottec.test.spring.properties.binder.factories;

import com.roottec.test.spring.properties.binder.properties.ConfigurationBase;

public interface Factory {
    Class<? extends ConfigurationBase> getConfigClass();
    String getType();
}
