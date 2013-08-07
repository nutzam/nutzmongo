package org.nutz.mongo;

import java.util.List;

import com.mongodb.AggregationOutput;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBDecoderFactory;
import com.mongodb.DBEncoder;
import com.mongodb.DBEncoderFactory;
import com.mongodb.DBObject;
import com.mongodb.GroupCommand;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceCommand.OutputType;
import com.mongodb.MapReduceOutput;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

/**
 * 对于集合类的薄封装
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class ZMongoCollection {

    private DBCollection dbc;

    public WriteResult insert(DBObject[] arr, WriteConcern concern) {
        return dbc.insert(arr, concern);
    }

    public WriteResult insert(DBObject[] arr, WriteConcern concern, DBEncoder encoder) {
        return dbc.insert(arr, concern, encoder);
    }

    public WriteResult insert(DBObject o, WriteConcern concern) {
        return dbc.insert(o, concern);
    }

    public WriteResult insert(DBObject... arr) {
        return dbc.insert(arr);
    }

    public WriteResult insert(WriteConcern concern, DBObject... arr) {
        return dbc.insert(concern, arr);
    }

    public WriteResult insert(List<DBObject> list) {
        return dbc.insert(list);
    }

    public WriteResult insert(List<DBObject> list, WriteConcern concern) {
        return dbc.insert(list, concern);
    }

    public WriteResult insert(List<DBObject> list, WriteConcern concern, DBEncoder encoder) {
        return dbc.insert(list, concern, encoder);
    }

    public WriteResult update(DBObject q,
                              DBObject o,
                              boolean upsert,
                              boolean multi,
                              WriteConcern concern) {
        return dbc.update(q, o, upsert, multi, concern);
    }

    public WriteResult update(DBObject q,
                              DBObject o,
                              boolean upsert,
                              boolean multi,
                              WriteConcern concern,
                              DBEncoder encoder) {
        return dbc.update(q, o, upsert, multi, concern, encoder);
    }

    public WriteResult update(DBObject q, DBObject o, boolean upsert, boolean multi) {
        return dbc.update(q, o, upsert, multi);
    }

    public WriteResult update(DBObject q, DBObject o) {
        return dbc.update(q, o);
    }

    public WriteResult updateMulti(DBObject q, DBObject o) {
        return dbc.updateMulti(q, o);
    }

    public WriteResult remove(DBObject o, WriteConcern concern) {
        return dbc.remove(o, concern);
    }

    public WriteResult remove(DBObject o, WriteConcern concern, DBEncoder encoder) {
        return dbc.remove(o, concern, encoder);
    }

    public WriteResult remove(DBObject o) {
        return dbc.remove(o);
    }

    public DBObject findAndModify(DBObject query,
                                  DBObject fields,
                                  DBObject sort,
                                  boolean remove,
                                  DBObject update,
                                  boolean returnNew,
                                  boolean upsert) {
        return dbc.findAndModify(query, fields, sort, remove, update, returnNew, upsert);
    }

    public DBObject findAndModify(DBObject query, DBObject sort, DBObject update) {
        return dbc.findAndModify(query, sort, update);
    }

    public DBObject findAndModify(DBObject query, DBObject update) {
        return dbc.findAndModify(query, update);
    }

    public DBObject findAndRemove(DBObject query) {
        return dbc.findAndRemove(query);
    }

    public void createIndex(DBObject keys) {
        dbc.createIndex(keys);
    }

    public void createIndex(DBObject keys, DBObject options) {
        dbc.createIndex(keys, options);
    }

    public void createIndex(DBObject keys, DBObject options, DBEncoder encoder) {
        dbc.createIndex(keys, options, encoder);
    }

    public void ensureIndex(String name) {
        dbc.ensureIndex(name);
    }

    public void ensureIndex(DBObject keys) {
        dbc.ensureIndex(keys);
    }

    public void ensureIndex(DBObject keys, String name) {
        dbc.ensureIndex(keys, name);
    }

    public void ensureIndex(DBObject keys, String name, boolean unique) {
        dbc.ensureIndex(keys, name, unique);
    }

    public void ensureIndex(DBObject keys, DBObject optionsIN) {
        dbc.ensureIndex(keys, optionsIN);
    }

    public void resetIndexCache() {
        dbc.resetIndexCache();
    }

    public void setHintFields(List<DBObject> lst) {
        dbc.setHintFields(lst);
    }

    public DBCursor find(DBObject ref) {
        return dbc.find(ref);
    }

    public DBCursor find(DBObject ref, DBObject keys) {
        return dbc.find(ref, keys);
    }

    public DBCursor find() {
        return dbc.find();
    }

    public DBObject findOne() {
        return dbc.findOne();
    }

    public DBObject findOne(DBObject o) {
        return dbc.findOne(o);
    }

    public DBObject findOne(DBObject o, DBObject fields) {
        return dbc.findOne(o, fields);
    }

    public DBObject findOne(DBObject o, DBObject fields, DBObject orderBy) {
        return dbc.findOne(o, fields, orderBy);
    }

    public DBObject findOne(DBObject o, DBObject fields, ReadPreference readPref) {
        return dbc.findOne(o, fields, readPref);
    }

    public DBObject findOne(DBObject o, DBObject fields, DBObject orderBy, ReadPreference readPref) {
        return dbc.findOne(o, fields, orderBy, readPref);
    }

    public Object apply(DBObject o) {
        return dbc.apply(o);
    }

    public Object apply(DBObject jo, boolean ensureID) {
        return dbc.apply(jo, ensureID);
    }

    public WriteResult save(DBObject jo) {
        return dbc.save(jo);
    }

    public WriteResult save(DBObject jo, WriteConcern concern) {
        return dbc.save(jo, concern);
    }

    public void dropIndexes() {
        dbc.dropIndexes();
    }

    public void dropIndexes(String name) {
        dbc.dropIndexes(name);
    }

    public void drop() {
        dbc.drop();
    }

    public long count() {
        return dbc.count();
    }

    public long count(DBObject query) {
        return dbc.count(query);
    }

    public long count(DBObject query, ReadPreference readPrefs) {
        return dbc.count(query, readPrefs);
    }

    public long getCount() {
        return dbc.getCount();
    }

    public long getCount(ReadPreference readPrefs) {
        return dbc.getCount(readPrefs);
    }

    public long getCount(DBObject query) {
        return dbc.getCount(query);
    }

    public long getCount(DBObject query, DBObject fields) {
        return dbc.getCount(query, fields);
    }

    public long getCount(DBObject query, DBObject fields, ReadPreference readPrefs) {
        return dbc.getCount(query, fields, readPrefs);
    }

    public long getCount(DBObject query, DBObject fields, long limit, long skip) {
        return dbc.getCount(query, fields, limit, skip);
    }

    public long getCount(DBObject query,
                         DBObject fields,
                         long limit,
                         long skip,
                         ReadPreference readPrefs) {
        return dbc.getCount(query, fields, limit, skip, readPrefs);
    }

    public DBCollection rename(String newName) {
        return dbc.rename(newName);
    }

    public DBCollection rename(String newName, boolean dropTarget) {
        return dbc.rename(newName, dropTarget);
    }

    public DBObject group(DBObject key, DBObject cond, DBObject initial, String reduce) {
        return dbc.group(key, cond, initial, reduce);
    }

    public DBObject group(DBObject key,
                          DBObject cond,
                          DBObject initial,
                          String reduce,
                          String finalize) {
        return dbc.group(key, cond, initial, reduce, finalize);
    }

    public DBObject group(DBObject key,
                          DBObject cond,
                          DBObject initial,
                          String reduce,
                          String finalize,
                          ReadPreference readPrefs) {
        return dbc.group(key, cond, initial, reduce, finalize, readPrefs);
    }

    public DBObject group(GroupCommand cmd) {
        return dbc.group(cmd);
    }

    public DBObject group(GroupCommand cmd, ReadPreference readPrefs) {
        return dbc.group(cmd, readPrefs);
    }

    public List<?> distinct(String key) {
        return dbc.distinct(key);
    }

    public List<?> distinct(String key, ReadPreference readPrefs) {
        return dbc.distinct(key, readPrefs);
    }

    public List<?> distinct(String key, DBObject query) {
        return dbc.distinct(key, query);
    }

    public List<?> distinct(String key, DBObject query, ReadPreference readPrefs) {
        return dbc.distinct(key, query, readPrefs);
    }

    public MapReduceOutput mapReduce(String map, String reduce, String outputTarget, DBObject query) {
        return dbc.mapReduce(map, reduce, outputTarget, query);
    }

    public MapReduceOutput mapReduce(String map,
                                     String reduce,
                                     String outputTarget,
                                     OutputType outputType,
                                     DBObject query) {
        return dbc.mapReduce(map, reduce, outputTarget, outputType, query);
    }

    public MapReduceOutput mapReduce(String map,
                                     String reduce,
                                     String outputTarget,
                                     OutputType outputType,
                                     DBObject query,
                                     ReadPreference readPrefs) {
        return dbc.mapReduce(map, reduce, outputTarget, outputType, query, readPrefs);
    }

    public MapReduceOutput mapReduce(MapReduceCommand command) {
        return dbc.mapReduce(command);
    }

    public MapReduceOutput mapReduce(DBObject command) {
        return dbc.mapReduce(command);
    }

    public AggregationOutput aggregate(DBObject firstOp, DBObject... additionalOps) {
        return dbc.aggregate(firstOp, additionalOps);
    }

    public List<DBObject> getIndexInfo() {
        return dbc.getIndexInfo();
    }

    public void dropIndex(DBObject keys) {
        dbc.dropIndex(keys);
    }

    public void dropIndex(String name) {
        dbc.dropIndex(name);
    }

    public CommandResult getStats() {
        return dbc.getStats();
    }

    public boolean isCapped() {
        return dbc.isCapped();
    }

    public DBCollection getCollection(String n) {
        return dbc.getCollection(n);
    }

    public String getName() {
        return dbc.getName();
    }

    public String getFullName() {
        return dbc.getFullName();
    }

    public DB getDB() {
        return dbc.getDB();
    }

    public int hashCode() {
        return dbc.hashCode();
    }

    public boolean equals(Object o) {
        return dbc.equals(o);
    }

    public String toString() {
        return dbc.toString();
    }

    public void setObjectClass(Class<?> c) {
        dbc.setObjectClass(c);
    }

    public Class<?> getObjectClass() {
        return dbc.getObjectClass();
    }

    public void setInternalClass(String path, Class<?> c) {
        dbc.setInternalClass(path, c);
    }

    public void setWriteConcern(WriteConcern concern) {
        dbc.setWriteConcern(concern);
    }

    public WriteConcern getWriteConcern() {
        return dbc.getWriteConcern();
    }

    public void setReadPreference(ReadPreference preference) {
        dbc.setReadPreference(preference);
    }

    public ReadPreference getReadPreference() {
        return dbc.getReadPreference();
    }

    public void addOption(int option) {
        dbc.addOption(option);
    }

    public void setOptions(int options) {
        dbc.setOptions(options);
    }

    public void resetOptions() {
        dbc.resetOptions();
    }

    public int getOptions() {
        return dbc.getOptions();
    }

    public void setDBDecoderFactory(DBDecoderFactory fact) {
        dbc.setDBDecoderFactory(fact);
    }

    public DBDecoderFactory getDBDecoderFactory() {
        return dbc.getDBDecoderFactory();
    }

    public void setDBEncoderFactory(DBEncoderFactory fact) {
        dbc.setDBEncoderFactory(fact);
    }

    public DBEncoderFactory getDBEncoderFactory() {
        return dbc.getDBEncoderFactory();
    }

    public ZMongoCollection(DBCollection c) {
        this.dbc = c;
    }

}
