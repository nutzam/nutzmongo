package org.nutz.mongo.entity;

import java.util.Map;

import org.nutz.lang.Lang;

/**
 * 一个 ZMoEntity 字段映射关系的生成器，可以给一个 Map 来自由的描述这个映射关系:<br>
 * Map 的格式定义如下
 * 
 * <pre>
 * {
 *      _key  : 'org.nutz.pojo.Pet',   // [必]映射对象的键，以便缓存和读取
 *      _type : 'MAP|POJO',            // [选]表示映射对象的类型
 *      _class: 'org.nutz.pojo.Pet',   // [选]对象的实现类，如果是 Map 也会指定实现类
 *      
 *      'name': 'nm',     // 下面就是数据库映射，  "Java名" : "数据库字段名"
 *      ...
 * }
 * </pre>
 * <ul>
 * <li>如果一个 map 必选字段是非法的
 * <li>指定了 _class 或者 _type 任何一个，都有办法来确认另外一个值
 * </ul>
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class ZMoEntityMaker {

    public ZMoEntity make(Object obj) {
        throw Lang.noImplement();
    }

    /**
     * 根据一个特殊的 Map 来构建 Map-ZMoDoc 映射关系
     * 
     * @param map
     *            一个描述映射关系的 Map 对象
     * @return 映射关系对象
     */
    private ZMoEntity makeMapEntity(Map<String, Object> map) {
        throw Lang.noImplement();
    }

    /**
     * 根据一个特殊的 Map 来构建 POJO-ZMoDoc 映射关系
     * 
     * @param map
     *            一个描述映射关系的 Map 对象
     * @return 映射关系对象
     */
    private ZMoEntity makePojoEntity(Map<String, Object> map) {
        throw Lang.noImplement();
    }

    /**
     * 根据一个特殊的 Map 来构建 POJO-ZMoDoc 映射关系
     * 
     * @param pojoType
     *            对象类型
     * @return 映射关系对象
     */
    private ZMoEntity makePojoEntity(Class<?> pojoType) {
        throw Lang.noImplement();
    }
}
