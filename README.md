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
