package com.roottec.test.spring.properties.binder.properties;

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
