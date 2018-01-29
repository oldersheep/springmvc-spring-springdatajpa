# springmvc-spring-springdatajpa
此项目跟据SSH框架改造，主要以熟悉Spring data JPA为目的，其中数据库设计没有变动，依然使用了外键关联，实体类中加入了OneToMany、ManyToMany等注解，当然，这些注解中加入MappedBy就会查不出来数据，不加的话，有一堆的中间表，很是苦恼，至今仍未解决。
当然，项目中也出现了很多问题，整理如下：

一、在maven依赖中没有当前项目中使用的pinyin4j的jar包，所以将自己下载的jar包，放在本地的maven仓库中，命令如下：
	mvn install:install-file -Dfile=E:\DWorksPiece\Repo\net\sourceforge\pinyin4j\pinyin4j-2.5.0.jar -DgroupId=net.sourceforge -DartifactId=pinyin4j -Dversion=2.5.0 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true
	注意：DgourpId配合DartifactId和Dversion的路径一定要和包路径一致

二、fastjson剔除不序列化的字段的几种解决办法
	1、加@Transient注解，不过加了这个注解，就不能和数据库中字段映射了
	2、@JSONField(serialize=false) 
	3、只要某些字段，和2互补，适用于只要少量字段的情况
	SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Region.class, "id","name"); 
	return JSONObject.toJSONString(regionList, filter);
	
三、json传到前台乱码
	1、@RequestMapping(value = "/listByAjax", produces = "text/html;charset=UTF-8") //text/json;charset=UTF-8
	2、在配置Spring MVC的注解驱动那里，加上一段代码
	<!-- MVC注解驱动 -->
	<mvc:annotation-driven>
		<!-- 采用自定义方案 -->
		<mvc:message-converters>
			<!-- 定义文本转化器 -->
			<bean class="org.springframework.http.converter.StringHttpMessageConverter">
				<constructor-arg index="0" value="UTF-8" />
			</bean>
		</mvc:message-converters>
	</mvc:annotation-driven>
	
四、将json中的某个字段改名，提供一个get方法即可。

五、在模拟CRM的项目中，没有加入<context:component-scan base-package="com.yyy.crm"/>，导致Autowired的注解并未生效，但是这个配置不单是只有这一个作用

六、在Spring data JPA中加入驼峰转换，<prop key="hibernate.naming.physical-strategy">org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl</prop>

七、Spring data JPA提供了很多在Repository中通过方法名来断定查询条件的方式，比如findByUsername，但是这种方式，并不能只查询一个字段，比如findUsernameById，这个方法名的话还是通过id查询一整条记录。另外，在定义的的更新语句中，返回值只能是空或者int，不然报错：spring data jpa Modifying queries can only use void or int/Integer as return type!

八、页面中时间类型的参数传往Controller的参数时，在Controller中加入这段代码
	@InitBinder 
	public void InitBinder(ServletRequestDataBinder binder) {
		binder.registerCustomEditor(java.util.Date.class,
				new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

九、Repository中save和saveAndFlush的区别：
	这两个方法都是保存单个对象，并返回保存的对象，在使用外键这种方式时，使用saveAndFlush这种方式可以返回当前实体存入数据库的id，如果这个id是自动生成的。

十、Spring data JPA高版本和低版本的差异还是很大的，包括XML文件中的配置，当前使用的版本为：1.10.9.RELEASE


整体总结：Spring data JPA对于单表的简单查询，非常的方便，只需要通过定义DAO层接口的方法名即可，和MyBatis中自定义插件的效果一致，但是Spring data JPA针对复杂查询的支持很无力，而且与Hibernate不同的是，在Hibernate中配置的级联关系，可以同步外键的保存，然而，我从来没有成功过，不知道是不是配置的不对。所以导致DAO层的方法中定义了执行很多原生SQL的方法，当然，这些SQL都很长。这个项目中有一个关于activiti的使用，我跟了一半，根据官网的快速入门，很容易完成配置以及与spring的整合，个人觉得暂时用不到，不想继续跟进，总之，Spring data JPA没有想象中那么舒服，复杂的业务逻辑又要用原生SQL，而且个人不太喜欢用外键，所以还是MyBatis这种ORM框架比较好用。当然，最初的想法是使用Spring data Redis试一下redis的整合，但是对JPA的失望值太高，不想去尝试了。
