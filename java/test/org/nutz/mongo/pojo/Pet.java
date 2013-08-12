package org.nutz.mongo.pojo;

import java.util.Date;

import org.nutz.castor.Castors;
import org.nutz.lang.Times;
import org.nutz.lang.random.R;
import org.nutz.mongo.annotation.*;

public class Pet {

    public static Pet NEW(String name) {
        return new Pet().setName(name);
    }

    public static Pet[] ARR(String... names) {
        Pet[] pets = new Pet[names.length];
        for (int i = 0; i < names.length; i++) {
            pets[i] = NEW(names[i]).setAge(R.random(2, 20))
                                   .setBornAt(Times.now())
                                   .setColor(Castors.me().castTo(R.random(0, 6), PetColor.class))
                                   .setType(Castors.me().castTo(R.random(0, 4), PetType.class));
        }
        return pets;
    }

    public static final String CNAME = "pet";

    private String _id;

    @MoField("nm")
    private String name;

    private int age;

    @MoField("ba")
    private Date bornAt;

    @MoField("tp")
    private PetType type;

    @MoEnum(str = false)
    private PetColor color;

    @MoIgnore
    private String comment;

    @MoField("ma")
    private Human master;

    private String[] labels;

    public String[] getLabels() {
        return labels;
    }

    public Pet setLabels(String[] labels) {
        this.labels = labels;
        return this;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public Pet setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public Pet setAge(int age) {
        this.age = age;
        return this;
    }

    public Date getBornAt() {
        return bornAt;
    }

    public Pet setBornAt(Date bornAt) {
        this.bornAt = bornAt;
        return this;
    }

    public PetType getType() {
        return type;
    }

    public Pet setType(PetType type) {
        this.type = type;
        return this;
    }

    public PetColor getColor() {
        return color;
    }

    public Pet setColor(PetColor color) {
        this.color = color;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Pet setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Human getMaster() {
        return master;
    }

    public void setMaster(Human master) {
        this.master = master;
    }

}
