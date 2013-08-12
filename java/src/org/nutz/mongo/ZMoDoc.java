package org.nutz.mongo;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.bson.BSONObject;
import org.bson.types.ObjectId;
import org.nutz.lang.Lang;

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
        return NEW().set(key, v);
    }

    public static ZMoDoc NEW(String json) {
        return NEW(Lang.map(json));
    }

    public static ZMoDoc WRAP(DBObject obj) {
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

    public DBObject getDBobj() {
        return DBobj;
    }

    public ZMoDoc setDBobj(DBObject dBobj) {
        DBobj = dBobj;
        return this;
    }

    public ZMoDoc set(String key, Object v) {
        put(key, v);
        return this;
    }

    // ------------------------------------------------------------
    // 下面是一些便捷的方法赖访问字段的值

    // ------------------------------------------------------------
    // 下面都是委托方法

    public Object put(String key, Object v) {
        // 检查一下错误，防止 _id 输入错误
        if ("_id".equals(key)) {
            // 空值
            if (v == null) {
                DBobj.removeField("_id");
            }
            // 错误类型
            else if (!(v instanceof ObjectId)) {
                throw Lang.makeThrow("doc._id should be ObjectID(), but '%s'", v.getClass()
                                                                                .getName());
            }
        }
        // 确定值不是空
        else if (null != v) {
            // 如果是 DBObject 就允许
            if (v instanceof DBObject) {}
            // 如果 v 是 Map 或者 Collection 或者 Array 统统禁止
            else if (v instanceof Map || v instanceof Collection || v.getClass().isArray()) {
                throw Lang.makeThrow("ZMoDoc can not put : %s", v.getClass().getName());
            }
        }
        return DBobj.put(key, v);
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

}
