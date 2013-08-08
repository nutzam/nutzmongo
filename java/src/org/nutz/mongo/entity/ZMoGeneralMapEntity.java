package org.nutz.mongo.entity;

import java.util.Map;
import java.util.Set;

import org.nutz.lang.born.Borning;
import org.nutz.mongo.ZMo;

import com.mongodb.DBObject;

public class ZMoGeneralMapEntity extends ZMoEntity {

    private ZMoGeneralMapField dftfld;

    public ZMoGeneralMapEntity() {
        dftfld = new ZMoGeneralMapField();
    }

    @Override
    public String getKey() {
        return ZMo.DFT_MAP_KEY;
    }

    @Override
    public void setKey(String key) {}

    @Override
    public ZMoEntity forMap() {
        return this;
    }

    @Override
    public ZMoEntity forPojo() {
        return this;
    }

    @Override
    public boolean isForMap() {
        return true;
    }

    @Override
    public boolean isForPojo() {
        return false;
    }

    @Override
    public Class<?> getJavaType() {
        return super.getJavaType();
    }

    @Override
    public void setJavaType(Class<?> javaType) {
        super.setJavaType(javaType);
    }

    @Override
    public Object born(Object... args) {
        return super.born(args);
    }

    @Override
    public void setBorning(Borning<?> borning) {
        super.setBorning(borning);
    }

    @Override
    public void addField(ZMoField fld) {}

    @SuppressWarnings("unchecked")
    @Override
    public Set<String> getJavaNames(Object obj) {
        Map<String, Object> map = (Map<String, Object>) obj;
        return map.keySet();
    }

    @Override
    public Set<String> getMongoNames(Object obj) {
        if (obj instanceof DBObject) {
            return ((DBObject) obj).keySet();
        }
        return getJavaNames(obj);
    }

    @Override
    public ZMoField getJavaField(String name) {
        return dftfld;
    }

    @Override
    public ZMoField javaField(String name) {
        return dftfld;
    }

    @Override
    public ZMoField getMongoField(String name) {
        return dftfld;
    }

    @Override
    public ZMoField mongoField(String name) {
        return dftfld;
    }

    @Override
    public String getJavaNameFromMongo(String mongoName) {
        return mongoName;
    }

    @Override
    public String getMongoNameFromJava(String javaName) {
        return javaName;
    }

    @Override
    public Object getValue(Object obj, String javaName) {
        return ((Map<?, ?>) obj).get(javaName);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setValue(Object obj, String javaName, Object value) {
        ((Map<String, Object>) obj).put(javaName, value);
    }

    @Override
    public ZMoEntity clone() {
        return new ZMoGeneralMapEntity();
    }

}
