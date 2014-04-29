package org.nutz.mongo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;

public abstract class ZMoBaseTest {

    private static final String DB_NAME = "nutzmongo";

    protected static final ZMo mo = ZMo.me();

    protected ZMoDB db;

    @Before
    public void before() {
        db = ZMongo.me("localhost").db(DB_NAME);
        prepare();
    }

    @SuppressWarnings("deprecation")
    @After
    public void after() {
        db.cleanCursors(true);
    }

    protected void prepare() {};

    protected <T> void assertArray(T[] expects, T[] objs) {
        assertEquals(expects.length, objs.length);
        for (int i = 0; i < objs.length; i++) {
            assertEquals(expects[i], objs[i]);
        }
    }

}
