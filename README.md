nutzmongo
=========

MongoDB 驱动的薄封装

# 加入到项目中

快照版本在每次提交后会自动deploy到sonatype快照库,享受各种bug fix和新功能

```xml
	<repositories>
		<repository>
			<id>ossrh</id>
			<url>https://oss.sonatype.org/content/repositories/snapshots</url>
			<snapshots>
				<enabled>true</enabled>
			</snapshots>
		</repository>
	</repositories>
	<dependencies>
		<dependency>
			<groupId>org.nutz</groupId>
			<artifactId>nutzmongo</artifactId>
			<version>1.r.57-SNAPSHOT</version>
		</dependency>
		<!-- 其他依赖 -->
	</dependencies>
```

也可以将repositories配置放入$HOME/.m2/settings.xml中

或者直接去[快照库下载](https://oss.sonatype.org/content/repositories/snapshots/org/nutz/nutz/1.r.57-SNAPSHOT/)

# 声明Ioc Bean

	var ioc={
		zMongo : {
			args : ["127.0.0.1", 27019], // 或者不写参数，默认就是127.0.0.1和27019
			factory : "org.nutz.mongo.ZMongo#me"
		},
		zMoDB : {
			args : ["nutzbook"], // 数据库名称
			factory : "$zMongo#db"
		},
		zMoCoUser : {
			args : ["user"],
			factory : "$zMoDB#c"
		}
		/* // 还可以声明几个常用的集合,也可以在Service中生成
		,zMoCoTopic : {
			args : ["topic"],
			factory : "$zMoDB#c"
		},
		zMoCoReply : {
			args : ["reply"],
			factory : "$zMoDB#c"
		}
		*/
	}

# Service中的注入和使用

	@IocBean
	public class XXXService {
		// 按需注入几个核心对象
		@Inject 
		protected ZMongo zmongo;  //注意大小写与配置的名字一致
		@Inject
		protected ZMoDB zMoDB; // 当前
		
		@Inject ZMoCo zMoCoUser;
		
		public void insert(User...users) {
			zMoCoUser.insert(ZMo.toDocArray(users));
		}
		
		public List<User> query(ZMoDoc cnd) {
			List<User> list = new ArrayList<User>();
			DBCursor cursor = MoCoUser.find(cnd);
			if(cursor.hasNext()) {
       			DBObject obj = cursor.next();
       			list.add(ZMo.me().fromDocToObj(obj, User.class));
    		}
    		return list;
		}
		
		// ZMoCoUser还有很多方法哦，请挖掘
	}