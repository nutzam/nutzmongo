package org.nutz.mongo.pojo;

import org.nutz.lang.random.R;
import org.nutz.mongo.annotation.MoField;

public class Human {

    public static Human NEW(String name) {
        return new Human().setName(name);
    }

    public static Human[] ARR(String... names) {
        Human[] people = new Human[names.length];
        for (int i = 0; i < names.length; i++) {
            people[i] = NEW(names[i]).setAge(R.random(2, 20));
        }
        return people;
    }

    public static final String CNAME = "human";

    @MoField("nm")
    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public Human setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public Human setAge(int age) {
        this.age = age;
        return this;
    }

}
