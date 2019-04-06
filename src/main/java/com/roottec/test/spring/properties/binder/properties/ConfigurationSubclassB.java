package com.roottec.test.spring.properties.binder.properties;

public class ConfigurationSubclassB extends ConfigurationBase {
    private String somePropertyInherentForB;

    public String getSomePropertyInherentForB() {
        return somePropertyInherentForB;
    }

    public void setSomePropertyInherentForB(String somePropertyInherentForB) {
        this.somePropertyInherentForB = somePropertyInherentForB;
    }

    @Override
    public String toString() {
        return "ConfigurationSubclassB{" +
                "somePropertyInherentForB='" + somePropertyInherentForB + "', " +
                "name='" + getName() + "', " +
                "type='" + getType() + "'" +
                '}';
    }
}
