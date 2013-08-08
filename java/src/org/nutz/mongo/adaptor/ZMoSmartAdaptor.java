package org.nutz.mongo.adaptor;

import org.nutz.lang.Lang;
import org.nutz.lang.Mirror;
import org.nutz.mongo.ZMoAdaptor;
import org.nutz.mongo.entity.ZMoField;

import com.mongodb.DBObject;

/**
 * 根据值的类型而不是字段类型类判断如何适配
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class ZMoSmartAdaptor implements ZMoAdaptor {

    ZMoSmartAdaptor() {}

    @Override
    public Object toJava(ZMoField fld, Object obj) {
        Mirror<?> mi = Mirror.me(obj);
        // 简单类型
        if (mi.isSimple()) {
            return ZMoAs.simple().toJava(fld, obj);
        }
        // 集合
        else if (mi.isCollection()) {
            return ZMoAs.collection().toJava(fld, obj);
        }
        // 数组
        else if (mi.isArray()) {
            return ZMoAs.array().toJava(fld, obj);
        }
        // 枚举
        else if (mi.isEnum()) {
            return ZMoAs.ENUM().toJava(fld, obj);
        }
        // Map
        else if (mi.isMap()) {
            return ZMoAs.map().toJava(fld, obj);
        }
        // POJO
        else if (mi.isPojo()) {
            return ZMoAs.pojo().toJava(fld, obj);
        }
        // 错误
        throw Lang.makeThrow("I am not such smart toJava -_-! : %s", obj.getClass());
    }

    @Override
    public Object toMongo(ZMoField fld, Object obj) {
        // 不用转咯
        if (obj instanceof DBObject) {
            return obj;
        }
        Mirror<?> mi = Mirror.me(obj);
        // 简单类型
        if (mi.isSimple()) {
            return ZMoAs.simple().toMongo(fld, obj);
        }
        // 集合
        else if (mi.isCollection()) {
            return ZMoAs.collection().toMongo(fld, obj);
        }
        // 数组
        else if (mi.isArray()) {
            return ZMoAs.array().toMongo(fld, obj);
        }
        // 枚举
        else if (mi.isEnum()) {
            return ZMoAs.ENUM().toMongo(fld, obj);
        }
        // Map
        else if (mi.isMap()) {
            return ZMoAs.map().toMongo(fld, obj);
        }
        // POJO
        else if (mi.isPojo()) {
            return ZMoAs.pojo().toMongo(fld, obj);
        }
        // 错误
        throw Lang.makeThrow("I am not such smart toMongo -_-! : %s", obj.getClass());
    }

}
