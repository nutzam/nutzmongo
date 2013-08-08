package org.nutz.mongo.adaptor;

import org.nutz.mongo.ZMoAdaptor;

/**
 * 各个适配器的单例工厂方法
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class ZMoAs {

    private static ZMoAdaptor _id = new ZMoIdAdaptor();

    private static ZMoAdaptor _array = new ZMoArrayAdaptor();

    private static ZMoAdaptor _collection = new ZMoCollectionAdaptor();

    private static ZMoAdaptor _enum = new ZMoEnumAdaptor();

    private static ZMoAdaptor _map = new ZMoMapAdaptor();

    private static ZMoAdaptor _pojo = new ZMoPojoAdaptor();

    private static ZMoAdaptor _simple = new ZMoSimpleAdaptor();

    private static ZMoAdaptor _smart = new ZMoSmartAdaptor();

    public static ZMoAdaptor id() {
        return _id;
    }

    public static ZMoAdaptor array() {
        return _array;
    }

    public static ZMoAdaptor collection() {
        return _collection;
    }

    public static ZMoAdaptor ENUM() {
        return _enum;
    }

    public static ZMoAdaptor map() {
        return _map;
    }

    public static ZMoAdaptor pojo() {
        return _pojo;
    }

    public static ZMoAdaptor simple() {
        return _simple;
    }

    public static ZMoAdaptor smart() {
        return _smart;
    }

}
