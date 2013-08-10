package org.nutz.mongo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.types.ObjectId;
import org.nutz.lang.Lang;
import org.nutz.mongo.adaptor.ZMoAs;
import org.nutz.mongo.entity.ZMoEntity;
import org.nutz.mongo.entity.ZMoEntityHolder;
import org.nutz.mongo.entity.ZMoEntityMaker;
import org.nutz.mongo.entity.ZMoField;
import org.nutz.mongo.entity.ZMoGeneralMapEntity;

import com.mongodb.DBObject;

/**
 * 一个工厂类，用来转换普通 JavaObject 与 ZMoDoc 对象
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class ZMo {

    /**
     * @return 一个新创建的文档
     */
    public ZMoDoc newDoc() {
        return ZMoDoc.NEW();
    }

    /**
     * @return 对象映射生成器
     */
    public ZMoEntityMaker maker() {
        return maker;
    }

    /**
     * 将任何 Java 对象（也包括 ZMoDoc）转换成 ZMoDoc
     * 
     * @param obj
     *            Java 对象，可以是 Pojo 或者 Map
     * @return 文档对象
     */
    public ZMoDoc toDoc(Object obj) {
        ZMoEntity en;
        // 如果 NULL 直接返回咯
        if (null == obj) {
            return null;
        }
        // 本身就是 DBObject
        else if (obj instanceof DBObject) {
            return ZMoDoc.WRAP((DBObject) obj);
        }
        // 普通 Map
        else if (obj instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) obj;
            // 获取 Map 的 KEY
            String _key = (String) map.get("_key");
            // 获取映射关系
            if (null == _key) {
                en = getEntity(obj.getClass());
            } else {
                en = holder.get(_key);
            }
            // 确定一定会有映射关系
            if (null == en) {
                throw Lang.makeThrow("Map[%s] without define!", _key);
            }
        }
        // POJO
        else {
            Class<? extends Object> objType = obj.getClass();
            // 数组不可以
            if (objType.isArray()) {
                throw Lang.makeThrow("Array can not toDoc : %s", objType.getName());
            }
            // 集合不可以
            else if (obj instanceof Collection) {
                throw Lang.makeThrow("Collection can not toDoc : %s", objType.getName());
            }
            // POJO
            else {
                en = getEntity(objType);
            }
        }
        // 执行转换
        return toDoc(obj, en);
    }

    /**
     * 将任何一个对象转换成 ZMoDoc，并强制指定映射关系
     * 
     * @param obj
     *            Java 对象，可以是 Pojo 或者 Map
     * @param en
     *            映射关系，如果为 null 相当于 toDoc(obj)
     * @return 文档对象
     */
    public ZMoDoc toDoc(Object obj, ZMoEntity en) {
        ZMoDoc doc = ZMoDoc.NEW();
        Set<String> keys = en.getJavaNames(obj);
        for (String key : keys) {
            Object v = en.getValue(obj, key);
            // 空值
            if (null == v) {
                doc.put(key, v);
            }
            // _id
            else if ("_id".equals(key)) {
                if (v instanceof ObjectId) {
                    doc.put(key, v);
                } else {
                    doc.put(key, new ObjectId(v.toString()));
                }
            }
            // 其他值适配
            else {
                ZMoField fld = en.javaField(key);
                Object dbv = fld.getAdaptor().toMongo(fld, v);
                doc.put(key, dbv);
            }
        }
        return doc;
    }

    /**
     * 将一组 Java 对象变成文档数组
     * 
     * @param objs
     *            Java 对象数组
     * @return 文档数组
     */
    public DBObject[] toDocArray(Object[] objs) {
        return toDocArray(null, objs);
    }

    /**
     * 将一组 Java 对象变成文档数组
     * 
     * @param objs
     *            Java 对象数组
     * @return 文档数组
     */
    public DBObject[] toDocArray(List<?> objs) {
        return toDocArray(null, objs);
    }

    /**
     * 将一组 Java 对象变成文档数组，并强制指定映射关系，以便提高速度
     * 
     * @param en
     *            映射关系，如果为 null 则自动判断如何映射
     * @param objs
     *            Java 对象数组
     * @return 文档数组
     */
    public DBObject[] toDocArray(ZMoEntity en, Object[] objs) {
        DBObject[] docs = new DBObject[objs.length];
        int i = 0;
        for (Object obj : objs)
            docs[i++] = toDoc(obj, en);
        return docs;
    }

    /**
     * 将一组 Java 对象变成文档数组，并强制指定映射关系，以便提高速度
     * 
     * @param en
     *            映射关系，如果为 null 则自动判断如何映射
     * @param objs
     *            Java 对象数组
     * @return 文档数组
     */
    public DBObject[] toDocArray(ZMoEntity en, List<?> objs) {
        DBObject[] docs = new DBObject[objs.size()];
        int i = 0;
        for (Object obj : objs)
            docs[i++] = toDoc(obj, en);
        return docs;
    }

    /**
     * 将一组 Java 对象变成文档列表
     * 
     * @param objs
     *            Java 对象数组
     * @return 文档列表
     */
    public List<DBObject> toDocList(Object[] objs) {
        return toDocList(null, objs);
    }

    /**
     * 将一组 Java 对象变成文档列表
     * 
     * @param objs
     *            Java 对象数组
     * @return 文档列表
     */
    public List<DBObject> toDocList(List<?> objs) {
        return toDocList(null, objs);
    }

    /**
     * 将一组 Java 对象变成文档列表，并强制指定映射关系，以便提高速度
     * 
     * @param en
     *            映射关系，如果为 null 则自动判断如何映射
     * @param objs
     *            Java 对象数组
     * @return 文档列表
     */
    public List<DBObject> toDocList(ZMoEntity en, Object[] objs) {
        List<DBObject> docs = new ArrayList<DBObject>(objs.length);
        for (Object obj : objs)
            docs.add(toDoc(obj, null));
        return docs;
    }

    /**
     * 将一组 Java 对象变成文档列表，并强制指定映射关系，以便提高速度
     * 
     * @param en
     *            映射关系，如果为 null 则自动判断如何映射
     * @param objs
     *            Java 对象数组
     * @return 文档列表
     */
    public List<DBObject> toDocList(ZMoEntity en, List<?> objs) {
        List<DBObject> docs = new ArrayList<DBObject>(objs.size());
        for (Object obj : objs)
            docs.add(toDoc(obj, null));
        return docs;
    }

    /**
     * 将任何一个文档对象转换成 ZMoDoc，<br>
     * 根据传入的映射关系来决定是变成 Pojo还是Map
     * 
     * @param doc
     *            文档对象
     * @param en
     *            映射关系，如果为 null，则变成普通 Map
     * @return 普通Java对象
     */
    public Object fromDoc(ZMoDoc doc, ZMoEntity en) {
        if (null == en) {
            en = holder.get(DFT_MAP_KEY);
        }
        Object obj = en.born();
        Set<String> keys = en.getMongoNames(doc);
        for (String key : keys) {
            Object v = en.getValue(obj, key);
            ZMoField fld = en.mongoField(key);
            Object pojov;
            // 空值
            if (null == v) {
                pojov = null;
            }
            // _id
            else if ("_id".equals(key)) {
                pojov = ZMoAs.id().toJava(fld, v);
            }
            // 其他值适配
            else {
                pojov = fld.getAdaptor().toJava(fld, v);
            }
            // 设置值
            String javaName = en.getJavaNameFromMongo(key);
            en.setValue(obj, javaName, pojov);
        }
        return obj;
    }

    /**
     * 将任何一个文档对象转换成指定 Java 对象（不可以是 Map 等容器）
     * 
     * @param <T>
     *            对象的类型参数
     * @param doc
     *            文档对象
     * @param classOfT
     *            对象类型，根据这个类型可以自动获得映射关系
     * @return 特定的Java对象
     */
    @SuppressWarnings("unchecked")
    public <T> T fromDocToObj(ZMoDoc doc, Class<T> classOfT) {
        return (T) (fromDoc(doc, getEntity(classOfT)));
    }

    /**
     * 将任何一个文档对象转换成指定 Java 对象（不可以是 Map 等容器）
     * 
     * @param <T>
     *            对象的类型参数
     * @param doc
     *            文档对象
     * @param en
     *            映射关系，如果为 null，则直接变成 Map
     * @return Map 对象
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> fromDocToMap(ZMoDoc doc) {
        return (Map<String, Object>) fromDoc(doc, null);
    }

    /**
     * 根据对象类型，得到一个映射关系（懒加载）
     * 
     * @param type
     *            对象类型可以是 POJO 或者 Map
     * @return 映射关系
     */
    public ZMoEntity getEntity(Class<? extends Object> type) {
        ZMoEntity en = holder.get(type.getName());
        // 如果木有加载过，那么尝试加载
        if (null == en) {
            synchronized (this) {
                en = holder.get(type.getName());
                if (null == en) {
                    // 如果是 Map 或者 DBObject 用默认Map映射对象来搞
                    if (Map.class.isAssignableFrom(type) || DBObject.class.isAssignableFrom(type)) {
                        en = holder.get(DFT_MAP_KEY).clone();
                        holder.add(DFT_MAP_KEY, en);
                    }
                    // 普通 POJO
                    else {
                        en = maker.make(type);
                        holder.add(type.getName(), en);
                    }
                }
            }
        }
        return en;
    }

    /**
     * @return 对象映射持有器
     */
    public ZMoEntityHolder holder() {
        return holder;
    }

    // ------------------------------------------------------------
    // 下面是这个类的字段和单例方法
    private static ZMo _me_ = new ZMo();

    /**
     * 默认的 Map 映射实体键值
     */
    public static final String DFT_MAP_KEY = "$nutz-mongo-dftmap-key$";

    // 这里创建一个默认的 Map 实体
    static {
        _me_.holder.add(DFT_MAP_KEY, new ZMoGeneralMapEntity());
    }

    /**
     * @return 单例
     */
    public static ZMo me() {
        return _me_;
    }

    private ZMoEntityHolder holder;

    private ZMoEntityMaker maker;

    private ZMo() {
        holder = new ZMoEntityHolder();
        maker = new ZMoEntityMaker();
    }

}
