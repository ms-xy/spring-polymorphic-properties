package com.roottec.test.spring.properties.binder.properties;

import com.roottec.test.spring.properties.binder.factories.Factory;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindHandlerAdvisor;
import org.springframework.boot.context.properties.bind.BindHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomBindHandlerAdvisor implements ConfigurationPropertiesBindHandlerAdvisor {
    private final List<Factory> factoryList;

    public CustomBindHandlerAdvisor(List<Factory> factoryList) {
        this.factoryList = factoryList;
    }

    @Override
    public BindHandler apply(BindHandler bindHandler) {
        return new CustomBindHandler(bindHandler, factoryList);
    }
}
