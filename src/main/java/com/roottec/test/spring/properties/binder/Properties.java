package com.roottec.test.spring.properties.binder;

import com.roottec.test.spring.properties.binder.properties.ConfigurationBase;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties
public class Properties {
    private List<ConfigurationBase> properties;

    public List<ConfigurationBase> getProperties() {
        return properties;
    }

    public void setProperties(List<ConfigurationBase> properties) {
        this.properties = properties;
    }
}
