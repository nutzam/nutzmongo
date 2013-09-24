package org.nutz.mongo;

import static org.junit.Assert.*;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.nutz.lang.Lang;
import org.nutz.lang.util.Closer;
import org.nutz.mongo.fieldfilter.ZMoFF;
import org.nutz.mongo.fieldfilter.ZMoRegexFF;
import org.nutz.mongo.fieldfilter.ZMoSimpleFF;
import org.nutz.mongo.pojo.Pet;
import org.nutz.mongo.pojo.PetType;

import com.mongodb.BasicDBList;

public class ZMoDocTest {

    @Test
    public void test_simple_field_filter() {
        final Pet pet = Pet.NEW("xiaobai").setAge(10).setType(PetType.CAT);
        ZMoSimpleFF ff = new ZMoSimpleFF("name");
        ZMoDoc doc = ff.run(new Closer<ZMoDoc>() {
            public ZMoDoc invoke() {
                return ZMo.me().toDoc(pet);
            }
        });
        assertEquals(1, doc.size());
        assertEquals("xiaobai", doc.get("nm").toString());
    }

    @Test
    public void test_regex_field_filter() {
        final Pet pet = Pet.NEW("xiaobai").setAge(10).setType(PetType.CAT);
        ZMoFF ff = new ZMoRegexFF("nm|tp").byJava(false);
        ZMoDoc doc = ff.run(new Closer<ZMoDoc>() {
            public ZMoDoc invoke() {
                return ZMo.me().toDoc(pet);
            }
        });
        assertEquals(2, doc.size());
        assertEquals("xiaobai", doc.get("nm").toString());
        assertEquals(PetType.CAT, doc.getAs("tp", PetType.class));
    }

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
