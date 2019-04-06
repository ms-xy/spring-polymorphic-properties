package com.roottec.test.spring.properties.binder.factories;

import com.roottec.test.spring.properties.binder.properties.ConfigurationBase;
import com.roottec.test.spring.properties.binder.properties.ConfigurationSubclassB;
import org.springframework.stereotype.Service;

@Service
public class FactoryB implements Factory {
    @Override
    public Class<? extends ConfigurationBase> getConfigClass() {
        return ConfigurationSubclassB.class;
    }

    @Override
    public String getType() {
        return "B";
    }
}
