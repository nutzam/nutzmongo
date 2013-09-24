package org.nutz.mongo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.BSONObject;
import org.bson.types.ObjectId;
import org.nutz.castor.Castors;
import org.nutz.lang.Each;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.mongo.adaptor.ZMoAs;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 包裹了 DBObject，并提供了一些更便利的方法
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class ZMoDoc implements DBObject {

    private DBObject DBobj;

    public static ZMoDoc NEW() {
        return new ZMoDoc().setDBobj(new BasicDBObject());
    }

    public static ZMoDoc NEW(int size) {
        return NEW().setDBobj(new BasicDBObject(size));
    }

    public static ZMoDoc NEW(Map<String, Object> m) {
        ZMoDoc doc = NEW();
        doc.putAll(m);
        return doc;
    }

    public static ZMoDoc NEW(String key, Object v) {
        return NEW().putv(key, v);
    }

    public static ZMoDoc ID(Object id) {
        return NEW().putv("_id", id);
    }

    public static ZMoDoc NOID() {
        return NEW().putv("_id", 0);
    }

    public static ZMoDoc NOID(String key, int v) {
        return NOID().putv(key, v);
    }

    public static ZMoDoc SET(String key, Object v) {
        return NEW().set(key, v);
    }

    public static ZMoDoc SET(DBObject dbo) {
        return NEW().set(dbo);
    }

    public static ZMoDoc NEW(String json) {
        return NEW(Lang.map(json));
    }

    public static ZMoDoc NEWf(String jsonf, Object... args) {
        return NEW(Lang.mapf(jsonf, args));
    }

    public static ZMoDoc WRAP(DBObject obj) {
        if (null == obj)
            return null;
        if (obj instanceof ZMoDoc)
            return (ZMoDoc) obj;
        return new ZMoDoc().setDBobj(obj);
    }

    /**
     * 重新生成 _id
     * 
     * @return 自身以便链式赋值
     */
    public ZMoDoc genID() {
        DBobj.put("_id", new ObjectId());
        return this;
    }

    /**
     * 删除自身的 _id 字段，以便作为一个新对象插入到数据库中
     */
    public ZMoDoc asNew() {
        DBobj.removeField("_id");
        return this;
    }

    public DBObject getDBobj() {
        return DBobj;
    }

    public ZMoDoc setDBobj(DBObject dBobj) {
        DBobj = dBobj;
        return this;
    }

    public ZMoDoc putv(String key, Object v) {
        put(key, v);
        return this;
    }

    public <T> ZMoDoc in(String key, T[] vs) {
        put(key, NEW("$in", vs));
        return this;
    }

    // ------------------------------------------------------------
    // 下面是一些便捷的方法用来设置常用的值
    /**
     * 本函数会设置 "$set" : {...} ，如果没有 "$set" 键，会添加
     * 
     * @param name
     *            要设置字段的名称
     * @param v
     *            要设置字段的值
     * @return 自身以便链式赋值
     */
    public ZMoDoc set(String name, Object v) {
        DBObject o = getAs("$set", DBObject.class);
        if (null == o) {
            o = ZMoDoc.NEW();
            put("$set", o);
        }
        o.put(name, v);
        return this;
    }

    /**
     * 本函数会将一个字段对象变为 $set，如果已经有了 $set 则合并
     * 
     * @param dbo
     *            文档对象
     * @return 自身以便链式赋值
     */
    public ZMoDoc set(DBObject dbo) {
        DBObject o = getAs("$set", DBObject.class);
        if (null == o) {
            put("$set", dbo);
        } else {
            o.putAll(dbo);
        }
        return this;
    }

    // ------------------------------------------------------------
    // 下面是一些便捷的方法赖访问字段的值
    public ObjectId getId() {
        return this.getAs("_id", ObjectId.class);
    }

    public ObjectId getAsId(String key) {
        Object obj = this.get(key);
        if (null == obj)
            return null;
        if (obj instanceof ObjectId)
            return (ObjectId) obj;
        return new ObjectId(obj.toString());
    }

    public int getInt(String key) {
        return getInt(key, -1);
    }

    public int getInt(String key, int dft) {
        Object v = get(key);
        return null == v ? dft : Castors.me().castTo(v, int.class);
    }

    public float getFloat(String key) {
        return getFloat(key, 0.0f);
    }

    public float getFloat(String key, float dft) {
        Object v = get(key);
        return null == v ? dft : Castors.me().castTo(v, float.class);
    }

    public long getLong(String key) {
        return getLong(key, -1);
    }

    public long getLong(String key, long dft) {
        Object v = get(key);
        return null == v ? dft : Castors.me().castTo(v, long.class);
    }

    public double getDouble(String key) {
        return getDouble(key, 0.0);
    }

    public double getDouble(String key, double dft) {
        Object v = get(key);
        return null == v ? dft : Castors.me().castTo(v, double.class);
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean dft) {
        Object v = get(key);
        return null == v ? dft : Castors.me().castTo(v, boolean.class);
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public String getString(String key, String dft) {
        Object v = get(key);
        return null == v ? dft : Castors.me().castTo(v, String.class);
    }

    public Date getTime(String key) {
        return getTime(key, null);
    }

    public Date getTime(String key, Date dft) {
        Object v = get(key);
        return null == v ? dft : Castors.me().castTo(v, Date.class);
    }

    public <T extends Enum<?>> T getEnum(String key, Class<T> classOfEnum) {
        String s = getString(key);
        if (Strings.isBlank(s))
            return null;
        return Castors.me().castTo(s, classOfEnum);
    }

    public boolean isEnum(String key, Enum<?>... eus) {
        if (null == eus || eus.length == 0)
            return false;
        try {
            Enum<?> v = getEnum(key, eus[0].getClass());
            for (Enum<?> eu : eus)
                if (!v.equals(eu))
                    return false;
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public <T> T getAs(String key, Class<T> classOfT) {
        return getAs(key, classOfT, null);
    }

    public <T> T getAs(String key, Class<T> classOfT, T dft) {
        Object v = get(key);
        return null == v ? dft : Castors.me().castTo(v, classOfT);
    }

    /**
     * 将一个字段转换成列表。因为返回的是容器，所以本函数永远不会返回 null
     * 
     * @param <T>
     * @param key
     * @param eleType
     * @return 列表对象，如果字段不存在或者为空，则返回一个空列表
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getList(String key, final Class<T> eleType) {
        Object v = get(key);
        if (null == v)
            return new ArrayList<T>();

        if (v instanceof CharSequence) {
            return Lang.list(Castors.me().castTo(v, eleType));
        }

        int len = Lang.length(v);
        final List<T> list = new ArrayList<T>(len);
        Lang.each(v, new Each<Object>() {
            public void invoke(int index, Object ele, int length) {
                list.add(Castors.me().castTo(ele, eleType));
            }
        });

        return list;

    }

    /**
     * 将一个字段转换成数组。因为返回的是容器，所以本函数永远不会返回 null
     * 
     * @param <T>
     * @param key
     * @param eleType
     * @return 数组对象，如果字段不存在或者为空，则返回一个空数组
     */
    @SuppressWarnings("unchecked")
    public <T> T[] getArray(String key, final Class<T> eleType) {
        Object v = get(key);
        if (null == v)
            return (T[]) Array.newInstance(eleType, 0);

        if (v instanceof CharSequence) {
            return Lang.array(Castors.me().castTo(v, eleType));
        }

        int len = Lang.length(v);
        final Object arr = Array.newInstance(eleType, len);
        final int[] i = new int[]{0};
        Lang.each(v, new Each<Object>() {
            public void invoke(int index, Object ele, int length) {
                Array.set(arr, i[0]++, Castors.me().castTo(ele, eleType));
            }
        });

        return (T[]) arr;

    }

    // ------------------------------------------------------------
    // 下面都是委托方法

    public Object put(String key, Object v) {
        // 检查一下错误，防止 _id 输入错误
        if ("_id".equals(key)) {
            // 空值，表示移除
            if (v == null) {
                DBobj.removeField("_id");
                return null;
            }
            // 普通的 ID
            else if (v instanceof ObjectId) {
                DBobj.put(key, v);
                return v;
            }
            // 如果是字符串，尝试转换
            else if (v instanceof CharSequence) {
                try {
                    ObjectId id = new ObjectId(v.toString());
                    DBobj.put(key, id);
                    return id;
                }
                catch (Exception e) {
                    throw Lang.makeThrow("'%s' not ObjectId", v);
                }
            }
            // 如果是 boolean 或者整数表示过滤
            else if (v instanceof Boolean || v instanceof Integer) {
                DBobj.put(key, v);
                return v;
            }
            // 否则不能接受
            else {
                throw Lang.makeThrow("doc._id should be ObjectID(), but '%s'",
                                     v.getClass().getName());
            }
        }
        // 空值，直接压入
        else if (null == v) {
            DBobj.put(key, null);
            return null;
        }
        // 对于 ObjectId
        else if (v instanceof ObjectId) {
            DBobj.put(key, v);
            return v;
        }
        /*
         * 确定值不是空
         */
        // 如果是 DBObject 就允许
        if (v instanceof DBObject) {
            return DBobj.put(key, v);
        }

        // 其他情况，适配一下
        Object o = ZMoAs.smart().toMongo(null, v);
        DBobj.put(key, o);
        return o;
    }

    public void markAsPartialObject() {
        DBobj.markAsPartialObject();
    }

    public boolean isPartialObject() {
        return DBobj.isPartialObject();
    }

    public void putAll(BSONObject o) {
        for (String key : o.keySet()) {
            put(key, o.get(key));
        }
    }

    @SuppressWarnings("rawtypes")
    public void putAll(Map m) {
        for (Object key : m.keySet()) {
            if (null != key)
                put(key.toString(), m.get(key));
        }
    }

    public Object get(String key) {
        return DBobj.get(key);
    }

    @SuppressWarnings("rawtypes")
    public Map toMap() {
        return DBobj.toMap();
    }

    public Object removeField(String key) {
        return DBobj.removeField(key);
    }

    public boolean containsKey(String s) {
        return DBobj.containsField(s);
    }

    public boolean containsField(String s) {
        return DBobj.containsField(s);
    }

    public Set<String> keySet() {
        return DBobj.keySet();
    }

    public int size() {
        return DBobj.keySet().size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public String toString() {
        return DBobj.toString();
    }

    public ZMoDoc clone() {
        ZMoDoc doc = ZMoDoc.NEW();
        for (String key : keySet())
            doc.put(key, get(key));
        return doc;
    }
}
