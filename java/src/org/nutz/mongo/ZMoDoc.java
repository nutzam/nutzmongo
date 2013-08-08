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

    private DBObject dbobj;

    public static ZMoDoc NEW() {
        ZMoDoc doc = new ZMoDoc();
        doc.dbobj = new BasicDBObject();
        return doc;
    }

    public static ZMoDoc NEW(int size) {
        ZMoDoc doc = new ZMoDoc();
        doc.dbobj = new BasicDBObject(size);
        return doc;
    }

    public static ZMoDoc NEW(Map<String, Object> m) {
        ZMoDoc doc = new ZMoDoc();
        doc.putAll(m);
        return doc;
    }

    public static ZMoDoc NEW(String key, Object v) {
        ZMoDoc doc = new ZMoDoc();
        doc.put(key, v);
        return doc;
    }

    public static ZMoDoc NEW(String json) {
        return NEW(Lang.map(json));
    }

    public static ZMoDoc WRAP(DBObject obj) {
        if (obj instanceof ZMoDoc)
            return (ZMoDoc) obj;
        ZMoDoc doc = new ZMoDoc();
        doc.dbobj = obj;
        return doc;
    }

    /**
     * 重新生成 _id
     * 
     * @return 自身以便链式赋值
     */
    public ZMoDoc genID() {
        dbobj.put("_id", new ObjectId());
        return this;
    }

    // ------------------------------------------------------------
    // 下面是一些便捷的方法赖访问字段的值

    // ------------------------------------------------------------
    // 下面都是委托方法

    public Object put(String key, Object v) {
        // 检查一下错误，防止 _id 输入错误
        if ("_id".equals(key)) {
            String msg = "doc._id should be ObjectID(), but '%s'";
            // 空值
            if (v == null) {
                throw Lang.makeThrow(msg, "null");
            }
            // 错误类型
            else if (!(v instanceof ObjectId)) {
                throw Lang.makeThrow(msg, v.getClass().getName());
            }
        }
        // 如果 v 是 Map 或者 Collection 或者 Array 统统禁止
        else if (null != v) {
            if (v instanceof Map || v instanceof Collection || v.getClass().isArray()) {
                throw Lang.makeThrow("ZMoDoc can not put : %s", v.getClass().getName());
            }
        }
        return dbobj.put(key, v);
    }

    public void markAsPartialObject() {
        dbobj.markAsPartialObject();
    }

    public boolean isPartialObject() {
        return dbobj.isPartialObject();
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
        return dbobj.get(key);
    }

    @SuppressWarnings("rawtypes")
    public Map toMap() {
        return dbobj.toMap();
    }

    public Object removeField(String key) {
        return dbobj.removeField(key);
    }

    public boolean containsKey(String s) {
        return dbobj.containsField(s);
    }

    public boolean containsField(String s) {
        return dbobj.containsField(s);
    }

    public Set<String> keySet() {
        return dbobj.keySet();
    }

}
