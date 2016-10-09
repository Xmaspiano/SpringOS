
## 介绍

这是一个以学习为目标,以开发各种奇思妙想的功能为乐趣的系统...

目前主要用到的框架: Spring4.0 SpringMVC Shiro Ehcache EasyUI FontAwesome

持久层:hibernate Jpa "SpringDataJpa"

数据库:MySql

此版用最简单的方式实现以下功能:

+ 用户登陆,菜单编辑,用户添加,角色添加,组织机构添加,资源扫描;
+ 用户角色关联,用户组织机构关联,组织机构角色关联;
+ 角色资源关联;
+ 代码中Shiro授权,资源和程序代码关联(关联后扫描写入资源档);
+ 构建快捷菜单功能,实现显示出当前页签的二级菜单;
+ 构建收藏夹菜单功能,实现在页签工具栏收藏后可以在收藏夹菜单快速访问;

[演示](http://xmas.oschina.mopaasapp.com/home/)

-

### tag<0.2.4>
#### 2016年10月09日
更换连接池

```
<!-- c3p0数据库连接池 -->
<dependency>
	<groupId>com.mchange</groupId>
	<artifactId>c3p0</artifactId>
	<version>0.9.2</version>
</dependency>

```

连接配置

```
<!-- 用户名-->
<property name="user" value="${jdbc.username}"/>
<!-- 用户密码-->
<property name="password" value="${jdbc.password}"/>
<property name="driverClass" value="${jdbc.driver}"/>
<property name="jdbcUrl" value="${jdbc.url}"/>

<!--连接池中保留的最大连接数。默认值: 15 -->
<property name="maxPoolSize" value="20"/>
<!-- 连接池中保留的最小连接数，默认为：3-->
<property name="minPoolSize" value="2"/>
<!-- 初始化连接池中的连接数，取值应在minPoolSize与maxPoolSize之间，默认为3-->
<property name="initialPoolSize" value="10"/>

<!--最大空闲时间，25200毫秒内未使用则连接被丢弃。若为0则永不丢弃。默认值: 0 -->
<property name="maxIdleTime" value="25200"></property>

<!-- 当连接池连接耗尽时，客户端调用getConnection()后等待获取新连接的时间，超时后将抛出SQLException，如设为0则无限期等待。单位毫秒。默认: 0 -->
<property name="checkoutTimeout" value="3000"/>

<!--当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。默认值: 3 -->
<property name="acquireIncrement" value="2"/>

<!--定义在从数据库获取新连接失败后重复尝试的次数。默认值: 30 ；小于等于0表示无限次-->
<property name="acquireRetryAttempts" value="0"/>

<!--重新尝试的时间间隔，默认为：10000毫秒-->
<property name="acquireRetryDelay" value="10000" />

<!--关闭连接时，是否提交未提交的事务，默认为false，即关闭连接，回滚未提交的事务 -->
<property name="autoCommitOnClose" value="false"></property>

<!--c3p0将建一张名为Test的空表，并使用其自带的查询语句进行测试。如果定义了这个参数那么属性preferredTestQuery将被忽略。你不能在这张Test表上进行任何操作，它将只供c3p0测试使用。默认值: null -->
<property name="automaticTestTable" value="Os_Test"></property>

<!--如果为false，则获取连接失败将会引起所有等待连接池来获取连接的线程抛出异常，但是数据源仍有效保留，并在下次调用getConnection()的时候继续尝试获取连接。如果设为true，那么在尝试获取连接失败后该数据源将申明已断开并永久关闭。默认: false-->
<property name="breakAfterAcquireFailure" value="false"></property>

<!--每60秒检查所有连接池中的空闲连接。默认值: 0，不检查 -->
<property name="idleConnectionTestPeriod" value="60"></property>
<!--c3p0全局的PreparedStatements缓存的大小。如果maxStatements与maxStatementsPerConnection均为0，则缓存不生效，只要有一个不为0，则语句的缓存就能生效。如果默认值: 0-->
<property name="maxStatements" value="100"></property>
<!--maxStatementsPerConnection定义了连接池内单个连接所拥有的最大缓存statements数。默认值: 0 -->
<property name="maxStatementsPerConnection" value="0"></property>

```

-
### tag<0.2.3>
#### 2016年9月29日
经过测试,异常问题依然没解决!!!

异常说明,由于Mysql连接空闲8小时,则断开连接, 由于spring配置了tomcat得连接池,当数据库连接不使用的时候,就把该连接重新放到数据池中,方便下次使用调用,此时就造成了8小时候,Mysql数据库连接已经断开,而连接池连接还存活的情况,这就是导致问题的关键所在.

再次变更配置:

```
	<property name="validationQuery" value="select 1"/>
	<property name="validationQueryTimeout" value="360000" /><!-- 6分钟超时时间 -->
```

#### 2016年9月28日
关于9月28日服务器访问异常的处理:
经过检查控制台输出,可log文件报出的异常,基本确定了异常产生起点:

		org.springframework.transaction.CannotCreateTransactionException:
		Could not open JPA EntityManager for transaction; 
		
		nested exception is javax.persistence.PersistenceException:
		com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException:
		
		Connection.close() has already been called. 
		Invalid operation in this state.

由此可以看出,异常是由于连接关闭导致的.

详细讲解:

在java web 应用中使用dbcp做为连接池，当数据库重启或数据库连接超过设置的最大timeout时间，数据库会强行断开已有的链接，此时当web程序访问数据库时就会出现错误，错误信息:

`java.io.EOFException: Can not read response from server. Expected to read 4 bytes, read 0 bytes before connection was unexpectedly lost`，原因是数据库这边已有的连接强行断开，而连接池中不知道已经断开，还是从连接池取出数据库连接交给程序去执行数据库操作，所以导致出错。

mysql的默认最大timeout时间是8小时，对空闲超过8小时的数据库连接会强行断开。timeout有两种，一个是非交互式的最大等待时间`wait_timeout`，另一个是交互式的最大等待时间`interactive_time`，交互连接如mysql gui tool中的连接。一般情况下interactive_timeout的设置将要对你的web 应用没有多大的影响。wait_timeout的时间设置太小话会导致连接关闭很快，从而使一些持久连接不起作用，反之设置过大，容易造成连接打开时间过长，在show processlist时，能看到太多的sleep状态的连接，从而造成too many connections错误。修改wait_timeout可以在my.cnf的mysqld段中设置。

可以通过dbcp的配置来解决上述的报错。可以用两种方式。

方式一：通过设置validationQuery，例如:

```
	<property name="validationQuery">
		<value>select 1</value>
	</property>
```

使用上述配置，连接池在返回数据库连接给申请者时会多执行一条sql语句来确保该连接的有效性。如果数据库方已经关闭了，连接池会重新建立连接并返回给申请者。通过测试似乎跟testWhileIdle没有关系，不管其是true或false都正常工作。

方式二：通过配置timeBetweenEvictionRunsMillis和minEvictableIdleTimeMillis，例如：

```
	<property name="minEvictableIdleTimeMillis">
		<value>60000</value>
	</property>
	<property name="timeBetweenEvictionRunsMillis">
		<value>10000</value>
	</property>
```

在构造GenericObjectPool `[BasicDataSource在其createDataSource () 方法中也会使用GenericObjectPool]`时，会生成一个内嵌类Evictor，实现自Runnable接口。如果`timeBetweenEvictionRunsMillis`大于0，每过 `timeBetweenEvictionRunsMillis`毫秒Evictor会调用evict()方法，检查连接池中的连接的闲置时间是否大于 `minEvictableIdleTimeMillis`毫秒（_minEvictableIdleTimeMillis小于等于0时则忽略，默认为30分钟），是则销毁此对象，然后调用ensureMinIdle方法检查确保池中对象个数不小于_minIdle。如果连接池的连接数小于最小空闲连接数，则创建数据库连接，同时检查连接池的连接是否小于maxIdle，是则把刚创建的连接放入连接池中，否则销毁此对象。

上述方式一能确保不出现本文开头提到的错误，但是不好的方面是每次执行sql时都会额外执行一条提供的validationQuery sql；第二种方式在数据库重启后`minEvictableIdleTimeMillis`毫秒前访问web应用，连接数据库使用的还是连接池中老的连接，所以还会出现上述的错误，`timeBetweenEvictionRunsMillis`和`minEvictableIdleTimeMillis`也不宜设置过小，会加重系统开销。根据具体情况来考虑使用哪种方式。对于数据库可能会经常重启，web应用和数据库机器的网络连接不稳定，可以采取第一种方式，否则使用第二种。由于mysql的默认最大空闲时间8小时，所以只要把`minEvictableIdleTimeMillis`设置小于此值即可。例如配置每十分钟检查超过空闲一个小时的连接

```
	<property name="minEvictableIdleTimeMillis">
		<value>3600000</value>
	</property>
	<property name="timeBetweenEvictionRunsMillis">
		<value>600000</value>
	</property>
```

配置变更:

```
	<property name="timeBetweenEvictionRunsMillis" value="600000" />
	<property name="minEvictableIdleTimeMillis" value="3600000" />
```

-
### tag<0.2.2>
尝试解决9月23日发现的服务器访问问题:

说明:对于Mysql,如果连接闲置8小时 (8小时内没有进行数据库操作), mysql就会自动断开连接, 需要重启tomcat.

目前添加连接参数,尝试设置连接断开后自动重新连接:

```
autoReconnect=false&failOverReadOnly=false
```

附参数表:

参数名称               |参数说明            |缺省值                |最低版本要求
----------------------|------------------|----------------------|-----------|
user                  |数据库用户名(用于连接数据库)                                                         |      |所有版本
password              |用户密码(用于连接数据库)                                                            |      |所有版本 
useUnicode            |是否使用Unicode字符集，如果参数characterEncoding设置为gb2312或gbk，本参数值必须设置为true|false |1.1g
characterEncoding     |当useUnicode设置为true时，指定字符编码。比如可设置为gb2312或gbk                        |false |1.1g
autoReconnect         |当数据库连接异常中断时，是否自动重新连接？                                             |false |1.1
autoReconnectForPools |是否使用针对数据库连接池的重连策略                                                    |false |3.1.3
failOverReadOnly      |自动重连成功后，连接是否设置为只读？                                                  |true  |3.0.12
maxReconnects         |autoReconnect设置为true时，重试连接的次数                                           |3     |1.1
initialTimeout        |autoReconnect设置为true时，两次重连之间的时间间隔，单位：秒                            |2     |1.1
connectTimeout        |和数据库服务器建立socket连接时的超时，单位：毫秒。 0表示永不超时，适用于JDK 1.4及更高版本   |0     |3.0.1
socketTimeout         |socket操作（读写）超时，单位：毫秒。 0表示永不超时                                     |0     |3.0.1

-
### tag<0.2.1>
#### 设计方案
权限的授予分为两部分:

1. 作业授权;作业->组织机构:权限->将权限授予对应职位(角色):可新增角色<br>
说明:

        1. 对于业务操作: 业务人员进入作业授权画面,该画面将分为左右下三侧;
        	左侧显示组织机构树状图,带复选框,并且提供查询功能,迅速定位到人员;
        	右侧显示该权限所包含的业务操作,带复选框,用户通过勾选组织机构下的人员
        	和勾选作业操作对人员进行操作权限授予,其中人员和作业操作必填;
        	
        	下侧显示当前用户勾选的用户列表
        2. 对于后台: 表单提交后,数据分为授权用户业务层接受数据后进行解析,
        3. 当用户点击页签右侧授权工具按钮时弹出授权对话框.
2.	机构角色授权;组织机构->创建角色->授予权限(菜单tree:作业对应的权限)

-
### tag<0.2>
#### 2016年9月23日
发现服务器访问问题,目前猜测为:

		若长时间不访问数据库,数据库连接会关闭导致无法访问!
-
### tag<0.2>
#### 2016年9月23日
新增组织机构,构建用户-角色(组织机构)-资源-权限体系,完善授权

说明:目前按照所有表关系按照多对多进行构建中间表,对于一对多和多对一的关系也使用中间表表示,这样的目的是为了标准表结构关系,构成数据表组和合关系表组,数据表组保存数据,关系表组存储关系,以这种方式将数据和关系分离.目前尚不清楚是说明结果,做出来再看.

#### 结构:
+ 用户- 用户角色关系- 角色
+ 用户- 用户组织机构关系- 组织机构
+ 组织机构- 组织机构角色关系- 角色
+ 角色- 角色资源关系- 资源
+ 资源即是权限关系

PS:此种结构导致用户和角色之间会有多层对应, 用户有多个角色,其中角色可以是系统赋予的,也可以是组织机构赋予的;

1. 通过这样授权会具有一定的灵活性,权限是授予角色的,而用户可由多种途径获得角色(例如角色直接授予,组织机构固定角色);
2. 说明:在大型企业erp授权体系中,有时会出现特殊权限人群,这部分人群可能来自不同的单位,因此组织机构不同,并且某一人员所属的组织机构下仅有该人员具有权限,而其他横向平级人员无权限,因此也不能在组织机构中创建新角色,此时创建一个特殊角色并将特殊权限授予给这个角色,然后将角色直接赋予特殊权限人员,即可解决这种非常规的授权需求.

#### 总结:
+ 用户-> 角色-> 资源-> 权限
+ 用户-> 组织机构-> 角色-> 资源-> 权限

-
### tag<0.1.1> 2016年9月21日
		修改功能:
			点击页签菜单时,快捷菜单默认到二级菜单,原先为上级菜单

-
### tag<0.1> 2016年9月20日 前期总结:
		1) 完成了快捷菜单的开发.
		功能:
			1. 显示保持和当前页签(焦点显示)一致的二级菜单及子菜单
			2. 当用户点击不同页签时,同步切换至二级菜单

		2) 完成了收藏夹的开发.
		功能:
			1. 页签上添加收藏按钮
			2. 点击按钮操作:
				a. 页签标题前添加收藏图标,再次点击时取消收藏图标
				b. 提交收藏请求到服务器,新增收藏目录
				c. 收藏夹菜单添加当前页签的菜单
				(此处为同步数据库查询操作,等待b返回成功信息后执行)
				
     		3. 收藏夹功能作业(未完成,收藏夹的CRUD的操作)

		3) 完成了菜单操作页面CRUD时,快捷菜单和收藏夹的同步更新.
			1. 菜单页面操作CRUD时更新左侧目录菜单,快捷菜单,和收藏夹
			2. 完成了收藏夹的开发.

#### 接下来准备干嘛:
   1. 服务器session探究,存储菜单目录,收藏夹信息
   2. 浏览器本地存储探究,保存菜单信息
