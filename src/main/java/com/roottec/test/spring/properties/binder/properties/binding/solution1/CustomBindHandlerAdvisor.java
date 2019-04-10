package com.roottec.test.spring.properties.binder.properties.binding.solution1;

import com.roottec.test.spring.properties.binder.properties.configuration.provider.ConfigurationProvider;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindHandlerAdvisor;
import org.springframework.boot.context.properties.bind.BindHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomBindHandlerAdvisor implements ConfigurationPropertiesBindHandlerAdvisor {
    private final List<ConfigurationProvider> providers;

    public CustomBindHandlerAdvisor(List<ConfigurationProvider> providers) {
        this.providers = providers;
    }

    @Override
    public BindHandler apply(BindHandler bindHandler) {
        return new CustomBindHandler(bindHandler, providers);
    }
}
