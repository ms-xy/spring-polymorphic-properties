package com.roottec.test.spring.properties.binder.properties.binding.solution2;

import com.roottec.test.spring.properties.binder.properties.ConfigurationBase;
import com.roottec.test.spring.properties.binder.properties.configuration.provider.ConfigurationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class PropertiesProviderConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesProviderConfiguration.class);

    @Bean
    public PropertiesProvider propertiesProvider(ApplicationPropertiesSolution2 applicationProperties, List<ConfigurationProvider> providers) {

        Map<String, Class<? extends ConfigurationBase>> classes =
                providers.stream().collect(Collectors.toMap(o -> o.getType(), o -> o.getConfigClass()));

        List<ConfigurationBase> configurationList = new ArrayList<>();

        for (Map<String, Object> parameters : applicationProperties.getConfigurations()) {
            try {
                String type = mapGetTyped(parameters, "type", String.class);
                Class<? extends ConfigurationBase> clazz = classes.get(type);
                if (clazz != null) {
                    Binder binder = new Binder(new MapConfigurationPropertySource(parameters));
                    BindResult<? extends ConfigurationBase> bindResult = binder.bind("", Bindable.of(clazz));
                    configurationList.add(bindResult.get());
                }
            } catch (ClassCastException e) {
                logger.error(e.getMessage(), e);
            }
        }

        return new PropertiesProvider(configurationList);
    }

    private <T> T mapGetTyped(Map map, Object key, Class<T> type) throws ClassCastException {
        Object o = map.get(key);
        if (type.isInstance(o)) {
            return type.cast(o);
        } else {
            throw new ClassCastException();
        }
    }
}
