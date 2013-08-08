package org.nutz.mongo.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.nutz.lang.Lang;
import org.nutz.lang.born.Borning;

/**
 * 描述了一个对象字段到 Mongo Document 之间的字段映射关系
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class ZMoEntity {

    enum Type {
        MAP, POJO
    }

    /**
     * 实体的键值，如果 Map 是一个自定义的字符串，如果是 POJO 是对象的 className
     */
    private String key;

    /**
     * 指定了这个映射是 Map 还是 Pojo
     */
    private Type type;

    /**
     * 对应的 Java 类型，如果是 Map 则表示 Map 的实现类
     */
    private Class<?> javaType;

    /**
     * 保存了对象的实例生成方法，以便能较快速的生成对象
     */
    private Borning<?> borning;

    /**
     * 以 java 字段名为索引的映射字段表
     */
    private Map<String, ZMoField> byJava;

    /**
     * 以 mongoDB 字段名为索引的映射字段表
     */
    private Map<String, ZMoField> byMongo;

    public ZMoEntity() {
        byJava = new HashMap<String, ZMoField>();
        byMongo = new HashMap<String, ZMoField>();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ZMoEntity forMap() {
        type = Type.MAP;
        return this;
    }

    public ZMoEntity forPojo() {
        type = Type.POJO;
        return this;
    }

    public boolean isForMap() {
        return Type.MAP == type;
    }

    public boolean isForPojo() {
        return Type.POJO == type;
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public void setJavaType(Class<?> javaType) {
        this.javaType = javaType;
    }

    public Object born(Object... args) {
        return borning.born(args);
    }

    public void setBorning(Borning<?> borning) {
        this.borning = borning;
    }

    public void addField(ZMoField fld) {

    }

    public Set<String> getJavaNames(Object obj) {
        return byJava.keySet();
    }

    public Set<String> getMongoNames(Object obj) {
        return byMongo.keySet();
    }

    public String getJavaNameFromMongo(String mongoName) {
        return mongoField(mongoName).getJavaName();
    }

    public String getMongoNameFromJava(String javaName) {
        return javaField(javaName).getMongoName();
    }

    /**
     * 获取实体字段
     * 
     * @param name
     *            字段名
     * @return 实体字段，如果没找到回 null
     */
    public ZMoField getJavaField(String name) {
        return byJava.get(name);
    }

    /**
     * 获取实体字段
     * 
     * @param name
     *            字段名
     * @return 实体字段，如果是 null 则抛错
     * @see #getField(String)
     */
    public ZMoField javaField(String name) {
        ZMoField fld = getJavaField(name);
        if (null == fld) {
            throw Lang.makeThrow("no such field! %s->%s", key, name);
        }
        return fld;
    }

    /**
     * 获取实体字段
     * 
     * @param name
     *            字段名
     * @return 实体字段，如果没找到回 null
     */
    public ZMoField getMongoField(String name) {
        return byMongo.get(name);
    }

    /**
     * 获取实体字段
     * 
     * @param name
     *            字段名
     * @return 实体字段，如果是 null 则抛错
     * @see #getField(String)
     */
    public ZMoField mongoField(String name) {
        ZMoField fld = getMongoField(name);
        if (null == fld) {
            throw Lang.makeThrow("no such field! %s->%s", key, name);
        }
        return fld;
    }

    /**
     * 获取对象某一特殊字段名称
     * 
     * @param obj
     *            对象
     * @param javaName
     *            字段名
     * @return 字段值
     */
    public Object getValue(Object obj, String javaName) {
        return javaField(javaName).getEjecting().eject(obj);
    }

    /**
     * 为对象某特殊字段设置值
     * 
     * @param obj
     *            对象
     * @param javaName
     *            字段名
     * @param value
     *            字段值
     */
    public void setValue(Object obj, String javaName, Object value) {
        javaField(javaName).getInjecting().inject(obj, value);
    }

    public ZMoEntity clone() {
        ZMoEntity en = new ZMoEntity();
        en.setKey(key);
        en.setJavaType(javaType);
        en.setBorning(borning);
        en.type = this.type;
        for (Map.Entry<String, ZMoField> fld : byJava.entrySet()) {
            ZMoField f2 = fld.getValue().clone();
            f2.setParent(en);
            en.byJava.put(fld.getKey(), f2);
        }
        for (Map.Entry<String, ZMoField> fld : byMongo.entrySet()) {
            ZMoField f2 = fld.getValue().clone();
            f2.setParent(en);
            en.byMongo.put(fld.getKey(), f2);
        }
        return en;
    }

}
