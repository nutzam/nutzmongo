package org.nutz.mongo.adaptor;

import java.lang.reflect.Array;
import java.util.Iterator;

import org.nutz.lang.Lang;
import org.nutz.lang.Mirror;
import org.nutz.mongo.ZMo;
import org.nutz.mongo.ZMoAdaptor;
import org.nutz.mongo.ZMoDoc;
import org.nutz.mongo.entity.ZMoEntity;
import org.nutz.mongo.entity.ZMoField;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

public class ZMoArrayAdaptor implements ZMoAdaptor {

    ZMoArrayAdaptor() {}

    @Override
    public Object toJava(ZMoField fld, Object obj) {
        if (obj instanceof BasicDBList) {
            BasicDBList list = (BasicDBList) obj;

            // 获取元素的实体
            ZMoEntity en = null;

            // 创建数组
            Object arr = null;
            if (fld == null) {
                arr = Array.newInstance(Object.class, list.size());
            } else {
                arr = Array.newInstance(fld.getEleType(), list.size());
                en = ZMo.me().getEntity(fld.getEleType());
            }

            // 开始循环数组
            int i = 0;
            Iterator<Object> it = list.iterator();
            while (it.hasNext()) {
                Object eleMongo = it.next();
                Object elePojo;
                // 确保已经获得过实体过了，这里这个代码考虑到效率
                // 就是说一个集合或者数组，映射方式总是一样的
                // 如果有不一样的，那么就完蛋了
                if (null == en) {
                    en = ZMo.me().getEntity(eleMongo.getClass());
                }
                // 如果元素是个 Mongo 类型
                if (eleMongo instanceof DBObject) {
                    ZMoDoc doc = ZMoDoc.WRAP((DBObject) eleMongo);
                    elePojo = ZMo.me().fromDoc(doc, en);
                }
                // 其他类型用 smart 转一下咯
                else {
                    elePojo = ZMoAs.smart().toJava(null, eleMongo);
                }
                // 加入到数组中
                Array.set(arr, i++, elePojo);
            }

            return arr;
        }
        throw Lang.makeThrow("toJava error: %s", obj.getClass());
    }

    @Override
    public Object toMongo(ZMoField fld, Object obj) {
        if (obj.getClass().isArray()) {
            BasicDBList list = new BasicDBList();
            int len = Array.getLength(obj);
            for (int i = 0; i < len; i++) {
                Object objPojo = Array.get(obj, i);
                Object objMongo;
                Mirror<?> mi = Mirror.me(objPojo);
                // Map 或者 POJO
                if (mi.isMap() || mi.isPojo()) {
                    objMongo = ZMo.me().toDoc(objPojo);
                }
                // 其他类型用 smart 转一下咯
                else {
                    objMongo = ZMoAs.smart().toMongo(null, objPojo);
                }
                // 加入到列表
                list.add(objMongo);
            }

            return list;
        }
        throw Lang.makeThrow("toMongo error: %s", obj.getClass());
    }

}
