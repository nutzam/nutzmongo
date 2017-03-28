package org.nutz.mongo.pojo;

import java.util.List;

import org.nutz.mongo.annotation.MoField;

public class Pet2 {

    private String _id;

    @MoField("nm")
    private String name;

    private int age;

    private List<Pet> pets;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
