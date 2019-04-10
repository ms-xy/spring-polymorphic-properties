package com.roottec.test.spring.properties.binder.properties;

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
