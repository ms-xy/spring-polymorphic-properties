package com.roottec.test.spring.properties.binder.factories;

import com.roottec.test.spring.properties.binder.properties.ConfigurationBase;
import com.roottec.test.spring.properties.binder.properties.ConfigurationSubclassA;
import org.springframework.stereotype.Service;

@Service
public class FactoryA implements Factory {
    @Override
    public Class<? extends ConfigurationBase> getConfigClass() {
        return ConfigurationSubclassA.class;
    }

    @Override
    public String getType() {
        return "A";
    }
}
