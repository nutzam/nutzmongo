package org.nutz.mongo.fieldfilter;

import org.nutz.lang.Lang;
import org.nutz.mongo.entity.ZMoField;

/**
 * 根据给定的 java 字段名字，来判断是否忽略该字段
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class ZMoSimpleFF extends ZMoFF {

    private String[] names;

    private boolean asIgnore;

    /**
     * @param asIgnore
     *            如果字段匹配正则表达式，true 表示忽略，false 表示不忽略
     * @param names
     *            给定一个字段名称列表（大小写敏感）
     * @param fftype
     *            用 Java 字段还是 Mongo 字段匹配
     */
    public ZMoSimpleFF(boolean asIgnore, ZMoFFType fftype, String... names) {
        super(asIgnore, fftype);
        this.names = names;
    }

    public ZMoSimpleFF(ZMoFFType fftype, String... names) {
        this(false, fftype, names);
    }

    public ZMoSimpleFF(String... names) {
        this(false, ZMoFFType.JAVA, names);
    }

    @Override
    public boolean isIgnore(ZMoField fld) {
        String nm = this.getMatchName(fld);
        if (asIgnore) {
            return Lang.contains(names, nm);
        }
        return !Lang.contains(names, nm);
    }

}
