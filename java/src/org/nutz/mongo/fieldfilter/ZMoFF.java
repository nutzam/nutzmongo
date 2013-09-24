package org.nutz.mongo.fieldfilter;

import org.nutz.lang.util.Closer;
import org.nutz.mongo.entity.ZMoField;

/**
 * 字段过滤器，在 toDoc 的时候生效，通过 ZMo.setFilterFilter 设置
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public abstract class ZMoFF {

    private static ThreadLocal<ZMoFF> _field_filters_ = new ThreadLocal<ZMoFF>();

    public static void set(ZMoFF ff) {
        if (null != ff) {
            _field_filters_.set(ff);
        }
    }

    public static ZMoFF get() {
        return _field_filters_.get();
    }

    public static void remove() {
        _field_filters_.remove();
    }

    public <T> T run(Closer<T> closer) {
        set(this);
        try {
            return closer.invoke();
        }
        finally {
            remove();
        }
    }

    protected boolean asIgnore;

    protected ZMoFFType fftype;

    /**
     * @param asIgnore
     *            如果字段匹配正则表达式，true 表示忽略，false 表示不忽略
     * @param fftype
     *            用 Java 字段还是 Mongo 字段匹配
     */
    protected ZMoFF(boolean asIgnore, ZMoFFType fftype) {
        this.asIgnore = asIgnore;
        this.fftype = fftype == null ? ZMoFFType.JAVA : fftype;
    }

    protected String getMatchName(ZMoField fld) {
        return ZMoFFType.JAVA == fftype ? fld.getJavaName()
                                       : fld.getMongoName();
    }

    /**
     * @param fld
     *            当前字段
     * @return false 表示忽略这个字段
     */
    public abstract boolean isIgnore(ZMoField fld);

}
