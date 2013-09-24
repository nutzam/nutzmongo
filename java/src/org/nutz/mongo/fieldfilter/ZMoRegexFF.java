package org.nutz.mongo.fieldfilter;

import java.util.regex.Pattern;

import org.nutz.mongo.entity.ZMoField;

/**
 * 根据给定的正则表达式，来判断Java字段或者Mongo字段是否忽略该字段
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class ZMoRegexFF extends ZMoFF {

    private Pattern regex;

    /**
     * @param asIgnore
     *            如果字段匹配正则表达式，true 表示忽略，false 表示不忽略
     * @param regex
     *            正则表达式匹配字段名
     * @param fftype
     *            用 Java 字段还是 Mongo 字段匹配
     */
    public ZMoRegexFF(boolean asIgnore, String regex, ZMoFFType fftype) {
        super(asIgnore, fftype);
        this.regex = Pattern.compile(regex);
    }

    public ZMoRegexFF(String regex, ZMoFFType fftype) {
        this(false, regex, fftype);
    }

    public ZMoRegexFF(String regex) {
        this(false, regex, ZMoFFType.JAVA);
    }

    @Override
    public boolean isIgnore(ZMoField fld) {
        String nm = this.getMatchName(fld);
        if (asIgnore) {
            return regex.matcher(nm).find();
        }
        return !regex.matcher(nm).find();
    }

}
