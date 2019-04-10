package com.roottec.test.spring.properties.binder.properties.binding.solution1;

import com.roottec.test.spring.properties.binder.properties.configuration.provider.ConfigurationProvider;
import com.roottec.test.spring.properties.binder.properties.ConfigurationBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.*;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomBindHandler extends AbstractBindHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomBindHandler.class);
    private final Map<String, Class<? extends ConfigurationBase>> configClasses = new HashMap<>();

    public CustomBindHandler(BindHandler parent, List<ConfigurationProvider> providers) {
        super(parent);
        for (ConfigurationProvider provider : providers) {
            configClasses.put(provider.getType(), provider.getConfigClass());
        }
    }

    @Override
    public Object onSuccess(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Object result) {
        if (result instanceof ConfigurationBase) {
            ConfigurationBase config = ((ConfigurationBase) result);
            if (configClasses.containsKey(config.getType())) {
                Class<? extends ConfigurationBase> aClass = configClasses.get(config.getType());
                BindResult<? extends ConfigurationBase> r = context.getBinder().bind(name, Bindable.of(aClass));
                return r.get();
            } else {
                logger.error("unable to bind type {}", config.getType());
            }
        }
        return super.onSuccess(name, target, context, result);
    }
}
