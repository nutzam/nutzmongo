package org.nutz.mongo;

import java.net.UnknownHostException;
import java.util.List;

import org.nutz.lang.Lang;
import org.nutz.lang.Strings;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;

/**
 * 维持了一个与 MongoDB 的连接方式，包括用户名密码等
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class ZMongo {

    public static ZMongo me() {
        return me(null, null, "localhost", 0);
    }

    public static ZMongo me(String host) {
        return me(null, null, host, ServerAddress.defaultPort());
    }

    public static ZMongo me(String userName, String password, String host) {
        return me(userName, password, host, ServerAddress.defaultPort());
    }

    public static ZMongo me(String userName, String password, String host, int port) {
        return me(userName, password, host, port, null);
    }

    public static ZMongo me(String userName,
                            String password,
                            String host,
                            int port,
                            MongoClientOptions mopt) {
        try {
            return new ZMongo(userName, password, Lang.list(new ServerAddress(host, port)), mopt);
        }
        catch (UnknownHostException e) {
            throw Lang.wrapThrow(e);
        }
    }

    public static ZMongo me(String userName, String password, List<ServerAddress> sas) {
        return me(userName, password, sas, null);
    }

    public static ZMongo me(String userName,
                            String password,
                            List<ServerAddress> sas,
                            MongoClientOptions mopt) {
        return new ZMongo(userName, password, sas, mopt);
    }

    /**
     * 声明了用户名，则表示连接 DB 需要提供一个认证信息
     */
    private String userName;

    private char[] password;

    /**
     * 保持一个连接实例
     */
    private MongoClient moclient;

    /**
     * @param userName
     *            连接的认证名，null 表示不需要认证信息
     * @param password
     *            连接时认证密码，仅在 userName 不为 null 时有效
     * @param sas
     *            集群的 replica set
     * @param mopt
     *            连接配置信息
     */
    private ZMongo(String userName,
                   String password,
                   List<ServerAddress> sas,
                   MongoClientOptions mopt) {
        this.userName = userName;
        this.password = Strings.isBlank(password) ? new char[0] : password.toCharArray();
        // 确保配置参数
        if (null == mopt)
            mopt = new MongoClientOptions.Builder().build();
        // 单一 host
        if (sas.size() == 1) {
            this.moclient = new MongoClient(sas.get(0), mopt);
        }
        // 多个 host
        else {
            this.moclient = new MongoClient(sas, mopt);
        }

    }

    /**
     * 获取数据库访问对象
     * 
     * @param dbname
     *            数据库名称
     * @return 数据库封装对象
     */
    public ZMoDB db(String dbname) {
        DB db = moclient.getDB(dbname);
        if (!Strings.isBlank(userName)) {
            db.authenticate(userName, password);
        }
        return new ZMoDB(db);
    }

    /**
     * @return 当前服务器的数据库名称列表
     */
    public List<String> dbnames() {
        return moclient.getDatabaseNames();
    }
}
