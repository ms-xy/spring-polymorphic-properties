package com.roottec.test.spring.properties.binder.properties;

public class ConfigurationSubclassA extends ConfigurationBase {
    private String somePropertyInherentForA;

    public String getSomePropertyInherentForA() {
        return somePropertyInherentForA;
    }

    public void setSomePropertyInherentForA(String somePropertyInherentForA) {
        this.somePropertyInherentForA = somePropertyInherentForA;
    }

    @Override
    public String toString() {
        return "ConfigurationSubclassA{" +
                "somePropertyInherentForA='" + somePropertyInherentForA + "', " +
                "name='" + getName() + "', " +
                "type='" + getType() + "'" +
                '}';
    }

}
