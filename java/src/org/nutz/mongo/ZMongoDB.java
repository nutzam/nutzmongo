package org.nutz.mongo;

import java.util.Set;

import org.nutz.lang.Lang;

import com.mongodb.DB;

/**
 * 对于 DB 对象的薄封装
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class ZMongoDB {

    private DB db;

    public ZMongoDB(DB db) {
        this.db = db;
    }

    /**
     * 获取集合，如果集合不存在，则抛错
     * 
     * @param name
     *            集合名称
     * @return 集合薄封装
     */
    public ZMongoCollection c(String name) {
        if (!db.collectionExists(name))
            throw Lang.makeThrow("Colection noexitst: %s.%s", db.getName(), name);
        return new ZMongoCollection(db.getCollection(name));
    }

    /**
     * 获取一个集合，如果集合不存在，就创建它
     * 
     * @param name
     *            集合名
     * @param dropIfExists
     *            true 如果存在就清除
     * @return 集合薄封装
     */
    public ZMongoCollection cc(String name, boolean dropIfExists) {
        // 不存在则创建
        if (!db.collectionExists(name)) {
            return createCollection(name, null);
        }
        // 固定清除
        else if (dropIfExists) {
            db.getCollection(name).drop();
            return createCollection(name, null);
        }
        // 已经存在
        return new ZMongoCollection(db.getCollection(name));
    }

    /**
     * 创建一个集合
     * 
     * @param name
     *            集合名
     * @param options
     *            集合配置信息
     * @return 集合薄封装
     */
    public ZMongoCollection createCollection(String name, ZMoDoc options) {
        if (db.collectionExists(name)) {
            throw Lang.makeThrow("Colection exitst: %s.%s", db.getName(), name);
        }

        // 创建默认配置信息
        if (null == options) {
            options = new ZMoDoc("capped:true, size:-1, max:-1");
        }

        return new ZMongoCollection(db.createCollection(name, options));
    }

    /**
     * @return 当前数据库所有可用集合名称
     */
    public Set<String> cnames() {
        return db.getCollectionNames();
    }

}
