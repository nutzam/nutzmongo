package org.nutz.mongo;

import static org.junit.Assert.*;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.nutz.lang.Lang;

import com.mongodb.BasicDBList;

public class ZMoDocTest {

    @Test
    public void test_fld_is_ObjectIdArray() {
        ZMoDoc d2 = ZMoDoc.NEW("nm", "B");
        ObjectId[] ids = Lang.array(new ObjectId());

        d2.putv("frs", ids);

        BasicDBList frs = (BasicDBList) d2.get("frs");
        assertEquals(1, frs.size());
        ObjectId theId = (ObjectId) frs.get(0);
        assertEquals(ids[0], theId);
    }

    @Test
    public void test_fld_is_ObjectId() {
        ZMoDoc doc = ZMoDoc.NEW("abc", new ObjectId("521c6ffa3004c3c9cbfe59d3"));
        ObjectId id = doc.getAsId("abc");
        assertEquals("521c6ffa3004c3c9cbfe59d3", id.toString());
    }

}
