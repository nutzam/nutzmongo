package org.nutz.mongo;

import static org.junit.Assert.*;

import org.bson.types.ObjectId;
import org.junit.Test;

public class ZMoDocTest {

    @Test
    public void test_fld_is_objectId() {
        ZMoDoc doc = ZMoDoc.NEW("abc", new ObjectId("521c6ffa3004c3c9cbfe59d3"));
        ObjectId id = doc.getAsId("abc");
        assertEquals("521c6ffa3004c3c9cbfe59d3", id.toString());
    }

}
