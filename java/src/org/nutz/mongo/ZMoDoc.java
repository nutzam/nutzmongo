package org.nutz.mongo;

import java.util.Map;

import org.nutz.lang.Lang;

import com.mongodb.BasicDBObject;

/**
 * 封装了一个 MongoDB 的 document 相关操作
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class ZMoDoc extends BasicDBObject {

    private static final long serialVersionUID = 6187255341476670016L;

    public ZMoDoc() {
        super();
    }

    public ZMoDoc(int size) {
        super(size);
    }

    public ZMoDoc(Map<String, Object> m) {
        super(m);
    }

    public ZMoDoc(String key, Object value) {
        super(key, value);
    }

    public ZMoDoc(String json) {
        super(Lang.map(json));
    }

}
