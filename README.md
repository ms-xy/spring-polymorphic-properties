# Example Project for Spring Boot: List of polymorphic properties in yaml application properties file

## Use Case:

A project with an application.yaml containing the following:

```yaml
configurations:
    - name: item1
      type: A
      propertySpecificToTypeA: someValue

    - name: item2
      type: B
      propertySpecificToTypeB: someValue

    - name: item3
      type: C
      propertySpecificToTypeC: someValue
```

Each configuration type is described by a `ConfigurationClassX` which inherits from a common base class
`ConfigurationBase`.

```java
public class ConfigurationBase {
    private String name;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ConfigurationBase{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

public class ConfigurationClassA extends ConfigurationBase {
    private String somePropertySpecificToA;

    public String getSomePropertySpecificToA() {
        return somePropertySpecificToA;
    }

    public void setSomePropertySpecificToA(String somePropertySpecificToA) {
        this.somePropertySpecificToA = somePropertySpecificToA;
    }

    @Override
    public String toString() {
        return "ConfigurationSubclassA{" +
                "somePropertyInherentForA='" + somePropertySpecificToA + "', " +
                "name='" + getName() + "', " +
                "type='" + getType() + "'" +
                '}';
    }

}

public class ConfigurationClassB extends ConfigurationBase {
    private String somePropertySpecificToB;

    public String getSomePropertySpecificToB() {
        return somePropertySpecificToB;
    }

    public void setSomePropertySpecificToB(String somePropertySpecificToB) {
        this.somePropertySpecificToB = somePropertySpecificToB;
    }

    @Override
    public String toString() {
        return "ConfigurationSubclassB{" +
                "somePropertyInherentForB='" + somePropertySpecificToB + "', " +
                "name='" + getName() + "', " +
                "type='" + getType() + "'" +
                '}';
    }
}
```


## Problem:

**Spring Boot** in its current version (`2.1.3.RELEASE` at the time of this writing) does not support this use case
out of the box.

- A `Converter<A,B>` doesn't seem to work, even though it is the first thing you find when using Google
(the `Binder` - used when auto-wiring `@ConfigurationProperties` annotated beans - doesn't use it, instead it creates
bean instances and uses the `JavaBeanBinder` to assign values to fields directly).

- **SnakeYaml** provides tags and it should be possible to convert classes using global tags.
However, this is cumbersome as each entry in the `configurations` list would need to be prefixed by something like
`!!path.to.my.class.ClassName` or similar. (Untested and there are only examples concerning scalar types available it
seems after a quick search using Google)

- **Spring Boot** currently doesn't provide a way to add custom tags (more precisely it lacks the option to specify
`Constructor`'s for **SnakeYaml**). As such, it is currently impossible to add short custom tags like `!A`.


## Solutions

Depending on your **Spring Boot** version you may be able to chose between solution 1 and 2 or only use solution 2.
I only tested versus `2.0.3.RELEASE` and `2.1.3.RELEASE`. The former misses the
`ConfigurationPropertiesBindHandlerAdvisor` interface (and subsequently the `Binder` does not possess the ability to
invoke a custom bind handler). As a result in version `2.0.3.RELEASE` one can only use solution 2.


### Common Setup for both Solutions

There is one `ConfigurationProvider` implementation for each available type,
providing the type (as a string) and the configuration class. These providers are auto-wired and used to retrieve
the necessary information.


### Solution 1: Custom BindHandler

This solution actually works using **Spring Boot** functionality added probably for exactly such purposes.
Adding a class `CustomBindHandler` which extends `AbstractBindHandler`, one can customize the return value used by the
`Binder`.

```java
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
```

To enable this custom bind handler, it is necessary to inform the `Binder` that it exists, doing so requires a
`ConfigurationPropertiesBindHandlerAdvisor` implementation:

```java
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
```

Finally, this will provide the correct configuration classes directly in the `@ConfigurationProperties` annotated bean:

```java
@Component
@ConfigurationProperties
public class ApplicationProperties {
    private List<ConfigurationBase> configurations;

    public List<ConfigurationBase> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<ConfigurationBase> configurations) {
        this.configurations = configurations;
    }
}
```

### Solution 2: Configuration Provider

The second solution is to abuse the fact that **SnakeYaml** by default returns a `Map<String, Object>` for objects.
As such we can cheat and have **Spring Boot** auto-wire a `List<Map<String, Object>>` instead of a
`List<ConfigurationBase>`:

```java
@Component
@ConfigurationProperties
public class ApplicationProperties {
    private List<Map<String, Object>> configurations;

    public List<Map<String, Object>> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<Map<String, Object>> configurations) {
        this.configurations = configurations;
    }
}
```

The list by itself is pretty much useless (for the given use case) - however, it is quite simple to create a bean
providing a `List<ConfigurationBase>`:

```java
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
```

The provider class:

```java
public class PropertiesProvider {
    private final List<ConfigurationBase> configurations;

    public PropertiesProvider(List<ConfigurationBase> configurations) {
        this.configurations = configurations;
    }

    public List<ConfigurationBase> getConfigurations() {
        return configurations;
    }
}
```
