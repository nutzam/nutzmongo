package org.nutz.mongo;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.mongo.pojo.Human;
import org.nutz.mongo.pojo.Pet;
import org.nutz.mongo.pojo.PetColor;
import org.nutz.mongo.pojo.PetType;

import com.mongodb.DBCursor;

public class ZMoPetTest extends ZMoBaseTest {

    private ZMoCo c;

    @Override
    protected void prepare() {
        c = db.cc(Pet.CNAME, false);
        c.remove(ZMoDoc.NEW());
    }

    @Test
    public void test_query_by_age() {
        c.save(mo.toDoc(Pet.NEW("A").setAge(10)));
        c.save(mo.toDoc(Pet.NEW("B").setAge(11)));
        c.save(mo.toDoc(Pet.NEW("D").setAge(13)));
        c.save(mo.toDoc(Pet.NEW("C").setAge(12)));

        ZMoDoc q = ZMoDoc.NEW("{age:{$gt:12}}");
        ZMoDoc doc = c.findOne(q);
        Pet pet = mo.fromDocToObj(doc, Pet.class);
        assertEquals("D", pet.getName());
        assertEquals(13, pet.getAge());
    }

    @Test
    public void test_update_inner_obj() {
        // 初始数据
        c.save(mo.toDoc(Pet.NEW("xiaobai")));

        // 条件
        ZMoDoc q = ZMoDoc.NEW("nm", "xiaobai");

        // 查出来看看
        Pet pet = mo.fromDocToObj(c.findOne(q), Pet.class);
        assertEquals(0, pet.getAge());
        assertNull(pet.getMaster());

        // 记录 ID
        String _id = pet.get_id();

        // 改值 & 保存
        pet.setMaster(Human.NEW("zozoh"));
        c.save(mo.toDoc(pet));

        // 再查出来看看
        ZMoDoc doc = c.findOne(q);
        pet = mo.fromDocToObj(doc, Pet.class);
        assertEquals(_id, pet.get_id());
        assertEquals("zozoh", pet.getMaster().getName());
    }

    @Test
    public void test_update_string_array() {
        // 初始数据
        c.save(mo.toDoc(Pet.NEW("xiaobai")));

        // 条件
        ZMoDoc q = ZMoDoc.NEW("nm", "xiaobai");

        // 查出来看看
        Pet pet = mo.fromDocToObj(c.findOne(q), Pet.class);
        assertEquals(0, pet.getAge());
        assertNull(pet.getLabels());

        // 记录 ID
        String _id = pet.get_id();

        // 改值 & 保存
        pet.setAge(10);
        pet.setLabels(Lang.array("x", "y", "z"));
        c.save(mo.toDoc(pet));

        // 再查出来看看
        ZMoDoc doc = c.findOne(q);
        pet = mo.fromDocToObj(doc, Pet.class);
        assertEquals(_id, pet.get_id());
        assertEquals(10, pet.getAge());
        assertArray(Lang.array("x", "y", "z"), pet.getLabels());
    }

    @Test
    public void test_simple_query() {
        Pet[] pets = Pet.ARR("A", "B", "C");
        c.insert(mo.toDocArray(pets));

        assertEquals(3, c.count());
        List<Pet> list = new ArrayList<Pet>(pets.length);
        DBCursor cur = c.find().sort(ZMoDoc.NEW("nm", -1));
        while (cur.hasNext()) {
            list.add(mo.fromDocToObj(cur.next(), Pet.class));
        }
        assertEquals(3, list.size());
        assertEquals("C", list.get(0).getName());
        assertEquals("B", list.get(1).getName());
        assertEquals("A", list.get(2).getName());

        c.remove(ZMoDoc.NEW("nm", "B"));

        assertEquals(2, c.count());
        list = new ArrayList<Pet>(pets.length);
        cur = c.find().sort(ZMoDoc.NEW("nm", -1));
        while (cur.hasNext()) {
            list.add(mo.fromDocToObj(cur.next(), Pet.class));
        }
        assertEquals(2, list.size());
        assertEquals("C", list.get(0).getName());
        assertEquals("A", list.get(1).getName());

    }

    @Test
    public void test_findOne() {
        Pet p0 = Pet.NEW("wendal")
                    .setAge(28)
                    .setBornAt(Times.D("1985-04-21 12:45:21"))
                    .setColor(PetColor.BLUE)
                    .setType(PetType.HAMSTER)
                    .setComment("V4 animal");
        ZMoDoc d0 = mo.toDoc(p0);
        c.save(d0);

        ZMoDoc d1 = c.findOne();
        Pet p1 = mo.fromDocToObj(d1, Pet.class);

        assertEquals(p0.getName(), p1.getName());
        assertEquals(Times.sD(p0.getBornAt()), Times.sD(p1.getBornAt()));
        assertEquals(p0.getAge(), p1.getAge());
        assertEquals(p0.getColor(), p1.getColor());
        assertEquals(p0.getType(), p1.getType());
        assertTrue(ZMo.isObjectId(p1.get_id()));
        assertNull(p1.getComment());
    }
}
