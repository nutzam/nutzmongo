package org.nutz.mongo.web;

import org.nutz.mongo.ZMoDoc;
import org.nutz.web.query.WebOrderField;
import org.nutz.web.query.WebQuery;

import com.mongodb.DBCursor;

public class MoWebQuery extends WebQuery {

    /**
     * 根据自身的配置，设置 DBCursor 的限制条件
     * 
     * @param cu
     *            游标
     * 
     * @return 游标对象
     */
    public DBCursor setup(DBCursor cu) {
        cu.skip(offset()).limit(pageSize);

        if (hasOrder()) {
            ZMoDoc sort = ZMoDoc.NEW(orderFields.length);
            for (WebOrderField of : orderFields) {
                sort.put(of.getName(), of.getSort().value());
            }
            cu.sort(sort);
        }
        return cu;
    }

}
