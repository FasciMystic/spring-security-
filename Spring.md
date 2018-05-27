## 7.1

①This chapter covers the Spring Framework implementation of the Inversion of Control (IoC) 1 principle. IoC is also known as dependency injection (DI).    

②The org.springframework.beans and org.springframework.context packages are the basis for Spring Framework’s IoC container.    

③In Spring, the objects that form the backbone of your application and that are managed by the Spring IoC container are called beans.    A bean is an object that is instantiated, assembled, and otherwise managed by a Spring IoC container. Otherwise, a bean is simply one of many objects in your application. Beans, and the dependencies among them, are reflected in the configuration metadata used by a container.    在Spring中，形成应用主干且被Spring IoC容器管理的对象称为*beans*。一个bean就是被Spring IoC容器实例化、装配和管理的对象。简单来说，一个bean就是你的应用中众多对象中一个。Beans和它们之间的依赖被容器中配置的元数据反射。（beans） 

④The interface org.springframework.context.ApplicationContext represents the Spring IoC container and is responsible for instantiating, configuring, and assembling the aforementioned beans.    **org.springframework.context.ApplicationContext**代表了Spring的IoC容器，并且负责实例化、配置和装配前面提及的bean。 

⑥Sping如何工作

![img](https://img-blog.csdn.net/20160416205158218) 

## 7.2

容器通过读取配置 元数据 获取指令来决定哪些对象将被实例化、配置和装配。<u>配置元数据反映在XML、Java注解或者Java代码中。它允许你表达组合成应用的对象及它们之间丰富的依赖关系。</u> 



1.配置元数据习惯上使用简单直观的XML形式 

（基于XML的元数据不是配置元数据的唯一方式，Spring IoC容器本身是与实际写配置元数据的形式完全解耦的，现在许多开发者选择基于Java的配置形式来配置Spring应用程序 ）

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="..." class="...">
        <!-- collaborators and configuration for this bean go here -->
    </bean>

    <bean id="..." class="...">
        <!-- collaborators and configuration for this bean go here -->
    </bean>

    <!-- more bean definitions go here -->

</beans>
```

**id **属性是一个字符串，它唯一标识一个bean的定义.(id属性的值指向了合作的对象。 )

**class **属性定义了bean的类型并且需要使用全路径。

2.基于java的配置，使用这些新特性，请参考**@Configuration**, **@Bean**, **@Import**和**@DepensOn**注解。 

3. 如何实例一个Spring IoC?   <u>(提供给**ApplicationContext**构造方法的一个或多个路径是实际的资源字符串，这使得容器可以从不同的外部文件加载配置元数据，比如本地的文件系统，Java的**CLASSPATH**，等等。 )</u>

```
ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"services.xml", "daos.xml"});     <!-- 路径①实际的资源字符串 ②不同的外部文件加载配置元素据-->
```

serverces.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- services -->

    <bean id="petStore" class="org.springframework.samples.jpetstore.services.PetStoreServiceImpl">
        <property name="accountDao" ref="accountDao"/>
        <property name="itemDao" ref="itemDao"/>
        <!-- additional collaborators and configuration for this bean go here -->
    </bean>

    <!-- more bean definitions for services go here -->

</beans>
```

**daos.xml** 

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="accountDao"
        class="org.springframework.samples.jpetstore.dao.jpa.JpaAccountDao">
        <!-- additional collaborators and configuration for this bean go here -->
    </bean>

    <bean id="itemDao" class="org.springframework.samples.jpetstore.dao.jpa.JpaItemDao">
        <!-- additional collaborators and configuration for this bean go here -->
    </bean>

    <!-- more bean definitions for data access objects go here -->

</beans>
```

service层包含类**PetStoreServiceImpl**和两个数据访问对象**JpaAccountDao**和**JpaItemDao**（基于JPA 对象关系映射标准）。**property**元素的**name**属性指向了JavaBean属性的名字，**ref**属性指向另一个bean定义的名字。**id**和**ref**之间的链接表达了两个合作者间的依赖关系。 

#### 4.

跨越多个XML文件配置bean定义是很有用的，通常一个独立的XML配置文件代表架构中的一个逻辑层或模块。 



## 7.3 Bean overview    

medate元数据   collaborators 合作者

1.Spring IoC容器管理了至少一个bean，这些bean通过提供给容器的配置元数据来创建，例如，XML形式的**<bean/>**定义。 

2.每一个bean都有一个或多个标识符。这些标识符在托管bean的容器中必须唯一。一个bean通常只有一个标识符，但如果需要更多标识符，可以通过别名来实现。在XML中，可以使用**id**和/或**name**属性来指定bean的标识符。**id**属性允许显式地指定一个id。 

>  **bean命名的约定**  命名bean时采用标准Java对字段的命名约定。bean名字以小写字母开头，然后是驼峰式。例如，（不带引号）**‘accountManager’**, **‘accountService’**, **‘userDao’**, **‘loginController’**, 等等。  按统一的规则命名bean更易于阅读和理解，而且，如果使用Spring AOP，当通过名字应用advice到一系列bean上将会非常有帮助 。

在XML配置中，可以使用**<alias/>**元素指定别名。 

```
<alias name="fromName" alias="toName"/>
```

**Java配置**  如果使用Java配置，**@Bean**注解可以用于提供别名 

### 1.

A Spring IoC container manages one or more beans. These beans are created with the configuration metadata that you supply to the container, for example, in the form of XML <bean/> definitions. Within the container itself, these bean definitions are represented as BeanDefinition objects, which contain (among other information) the following metadata: • A package-qualified class name: typically the actual implementation class of the bean being defined. • Bean behavioral configuration elements, which state how the bean should behave in the container (scope, lifecycle callbacks, and so forth). Spring Framework Reference Documentation 4.3.12.RELEASE Spring Framework 44 • References to other beans that are needed for the bean to do its work; these references are also called collaborators or dependencies. • Other configuration settings to set in the newly created object, for example, the number of connections to use in a bean that manages a connection pool, or the size limit of the pool.    



### 2.

#### Instantiating beans:

1.Instantiation with a constructor  使用构造方法实例化

2.Instantiation with a static factory method  静态工厂方法实例化

3.Instantiation using an instance factory method  使用实例的工厂方法实例化（非静态）

#### 3.

工厂bean本身也可以通过依赖注入管理和配置 



## 7.4 Dependencies    

定义一系列独立的bean使它们相互合作实现一个共同的目标。 

#### 1.

A typical enterprise application does not consist of a single object (or bean in the Spring parlance).     

#### 2.Dependency Injection 

Dependency injection (DI) is a process whereby objects define their dependencies, that is, the other objects they work with, only through constructor arguments, arguments to a factory method, or properties that are set on the object instance after it is constructed or returned from a factory method. The container then injects those dependencies when it creates the bean. This process is fundamentally the inverse, hence the name Inversion of Control (IoC), of the bean itself controlling the instantiation or location of its dependencies on its own by using direct construction of classes, or the Service Locator pattern.    依赖注入是一个对象定义其依赖的过程，它的依赖也就是与它一起合作的其它对象，这个过程只能通**过构造方法参数、工厂方法参数**、或者被构造或从工厂方法返回后通过**setter**方法设置其属性来实现。然后容器在创建这个bean时注入这些依赖。这个过程本质上是反过来的，由bean本身控制实例化，或者直接通过类的结构或Service定位器模式定位它自己的依赖，因此得其名曰控制反转 。



#### 3.Circular dependencies

If you use predominantly constructor injection, it is possible to create an unresolvable circular dependency scenario. Spring Framework Reference Documentation 4.3.12.RELEASE Spring Framework 53 For example: Class A requires an instance of class B through constructor injection, and class B requires an instance of class A through constructor injection. If you configure beans for classes A and B to be injected into each other, the Spring IoC container detects this circular reference at runtime, and throws a BeanCurrentlyInCreationException. One possible solution is to edit the source code of some classes to be configured by setters rather than constructors. Alternatively, avoid constructor injection and use setter injection only. In other words, although it is not recommended, you can configure circular dependencies with setter injection. Unlike the typical case (with no circular dependencies), a circular dependency between bean A and bean B forces one of the beans to be injected into the other prior to being fully initialized itself (a classic chicken/egg scenario).    如果你主要使用构造方法注入，很有可能创建一个无法解决的循环依赖场景。  例如，类A使用构造方法注入时需要类B的一个实例，类B使用构造方法注入时需要类A的一个实例。如果为类A和B配置bean互相注入，Spring IoC容器会在运行时检测出循环引用，并抛出异常**BeanCurrentlyInCreationException**。  一种解决方法是把一些类配置为使用setter方法注入而不是构造方法注入。作为替代方案，避免构造方法注入，而只使用setter方法注入。换句话说，尽管不推荐，但是可以通过setter方法注入配置循环依赖。  不像典型的案例（没有循环依赖），A和B之间的循环依赖使得一个bean在它本身完全初始化之前被注入了另一个bean（经典的先有鸡/先有蛋问题） 

#### 4.

**依赖注入**有两种主要的方式，[**基于构造方法的依赖注入**]和[**基于setter方法的依赖注入**]。 

一。[基于构造方法的依赖注入]

①

```
package examples;

public class ExampleBean {

    // Number of years to calculate the Ultimate Answer
    private int years;

    // The Answer to Life, the Universe, and Everything
    private String ultimateAnswer;

    public ExampleBean(int years, String ultimateAnswer) {
        this.years = years;
        this.ultimateAnswer = ultimateAnswer;
    }

}
```

使用**index**属性显式地指定参数的顺序。 

```
<bean id="exampleBean" class="examples.ExampleBean">
    <constructor-arg index="0" value="7500000"/>
    <constructor-arg index="1" value="42"/>
</bean>
```

或者

```
<bean id="exampleBean" class="examples.ExampleBean">
    <constructor-arg name="years" value="7500000"/>
    <constructor-arg name="ultimateAnswer" value="42"/>
</bean>
```

②为了使其可以立即使用，代码必须开启调试标记来编译，这样Spring才能从构造方法中找到参数的名字。如果没有开启调试标记（或不想）编译，可以使用JDK的注解[@ConstructorProperties](http://download.oracle.com/javase/6/docs/api/java/beans/ConstructorProperties.html)显式地指定参数的名字。 

```
package examples;

public class ExampleBean {

    // Fields omitted

    @ConstructorProperties({"years", "ultimateAnswer"})
    public ExampleBean(int years, String ultimateAnswer) {
        this.years = years;
        this.ultimateAnswer = ultimateAnswer;
    }

}
```

二，[基于setter方法的依赖注入]

基于setter方法的依赖注入，由容器在调用无参构造方法或无参静态工厂方法之后调用setter方法来实例化bean 。

```
public class SimpleMovieLister {

    // the SimpleMovieLister has a dependency on the MovieFinder
    private MovieFinder movieFinder;

    // a setter method so that the Spring container can inject a MovieFinder
    public void setMovieFinder(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }

    // business logic that actually uses the injected MovieFinder is omitted...

}
<!--注意，在setter方法上使用@Required注解可以使属性变成必需的依赖。 -->
```

**ApplicationContext**对它管理的bean支持①基于构造方法和基于setter方法的依赖注入，也支持②在使用构造方法注入依赖之后再使用setter方法注入依赖。以**BeanDefinition**的形式配置依赖，可以与**PropertyEditor**实例一起把属性从一种形式转化为另一种形式。但是，大多数Spring用户不直接（编程式地）使用这些类，而是使用XML **bean**定义、注解的组件（例如，以**@Component**、**@Controller**注解的类）或基于Java的**@Configuration**类的**@Bean**方法。这些资源然后都被内部转化为了**BeanDefinition**的实例，并用于加载完整的Spring IoC容器的实例。



三.依赖注入示例

下面的例子使用XML配置setter方法注入。XML的部分配置如下: ①

```
<bean id="exampleBean" class="examples.ExampleBean">
    <!-- setter injection using the nested ref element -->
    <property name="beanOne">
        <ref bean="anotherExampleBean"/>
    </property>

    <!-- setter injection using the neater ref attribute -->
    <property name="beanTwo" ref="yetAnotherBean"/>
    <property name="integerProperty" value="1"/>
</bean>

<bean id="anotherExampleBean" class="examples.AnotherBean"/>
<bean id="yetAnotherBean" class="examples.YetAnotherBean"/>
```

```
public class ExampleBean {

    private AnotherBean beanOne;
    private YetAnotherBean beanTwo;
    private int i;

    public void setBeanOne(AnotherBean beanOne) {
        this.beanOne = beanOne;
    }

    public void setBeanTwo(YetAnotherBean beanTwo) {
        this.beanTwo = beanTwo;
    }

    public void setIntegerProperty(int i) {
        this.i = i;
    }

}
```

上面的例子中，XML中setter方法与属性匹配，下面的例子使用构造方法注入： ②

```
<bean id="exampleBean" class="examples.ExampleBean">
    <!-- constructor injection using the nested ref element -->
    <constructor-arg>
        <ref bean="anotherExampleBean"/>
    </constructor-arg>

    <!-- constructor injection using the neater ref attribute -->
    <constructor-arg ref="yetAnotherBean"/>

    <constructor-arg type="int" value="1"/>
</bean>

<bean id="anotherExampleBean" class="examples.AnotherBean"/>
<bean id="yetAnotherBean" class="examples.YetAnotherBean"/>
```

```
public class ExampleBean {

    private AnotherBean beanOne;
    private YetAnotherBean beanTwo;
    private int i;

    public ExampleBean(
        AnotherBean anotherBean, YetAnotherBean yetAnotherBean, int i) {
        this.beanOne = anotherBean;
        this.beanTwo = yetAnotherBean;
        this.i = i;
    }

}
```

现在考虑使用另外一种方式，调用静态工厂方法代替构造方法返回这个对象的实例： ③

```
<bean id="exampleBean" class="examples.ExampleBean" factory-method="createInstance">
    <constructor-arg ref="anotherExampleBean"/>
    <constructor-arg ref="yetAnotherBean"/>
    <constructor-arg value="1"/>
</bean>

<bean id="anotherExampleBean" class="examples.AnotherBean"/>
<bean id="yetAnotherBean" class="examples.YetAnotherBean"/>
```

```
public class ExampleBean {

    // a private constructor
    private ExampleBean(...) {
        ...
    }

    // a static factory method; the arguments to this method can be
    // considered the dependencies of the bean that is returned,
    // regardless of how those arguments are actually used.
    public static ExampleBean createInstance (
        AnotherBean anotherBean, YetAnotherBean yetAnotherBean, int i) {

        ExampleBean eb = new ExampleBean (...);
        // some other operations...
        return eb;
    }

}
```

通过**<constructor-arg/>**元素为静态工厂方法提供参数，与实际使用构造方法时一样。工厂方法返回的类型不必与包含工厂方法的类的类型一致，即使在这个例子中是一样的。实例（非静态）工厂方法完全一样的使用方法（除了使用**factory-bean**代替**class**属性），所以这里就不再赘述了。 

四. 可以定义bean的属性和构造方法的参数引用其它的bean（合作者），或设置值。在XML配置中，可以使用<property/>或<constructor-arg/>的属性达到这个目的 

五.

①  **p命名空间**使你可以使用bean元素的属性，而不是内置的<property/>元素，来描述属性值和合作的bean。 

```
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:p="http://www.springframework.org/schema/p"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean name="classic" class="com.example.ExampleBean">
        <property name="email" value="foo@bar.com"/>
    </bean>

    <bean name="p-namespace" class="com.example.ExampleBean"
        p:email="foo@bar.com"/>
</beans>
```

以上两段XML是一样的结果，第一段使用标准的XML形式，第二段使用p命名空间形式 .

② **c命名空间**是Spring3.1新引入的，允许使用内联属性配置构造方法的参数，而不用嵌套constructor-arg元素。

```
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:c="http://www.springframework.org/schema/c"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="bar" class="x.y.Bar"/>
    <bean id="baz" class="x.y.Baz"/>

    <!-- traditional declaration -->
    <bean id="foo" class="x.y.Foo">
        <constructor-arg ref="bar"/>
        <constructor-arg ref="baz"/>
        <constructor-arg value="foo@bar.com"/>
    </bean>

    <!-- c-namespace declaration -->
    <bean id="foo" class="x.y.Foo" c:bar-ref="bar" c:baz-ref="baz" c:email="foo@bar.com"/>

</beans>

```

c: 命名空间与p: 命名空间使用一样的转换（后面的-ref用于bean引用），通过名字设置构造方法的参数。同样地，c命名空间也没有定义在XSD文件中（但是存在于Spring核心包中）。 



#### 5.使用depends-on

如果一个bean是另一个bean的依赖，那通常意味着这个bean被设置为另一个bean的属性。典型地，在XML配置中使用  <ref/>  元素来完成这件事。但是，有时bean之间的依赖并不是直接的，例如，一个静态的初始化器需要被触发，比如在数据库驱动注册时。depends-on属性可以明确地强制一个或多个bean在使用它（们）的bean初始化之前被初始化。 

下面的例子使用depends-on属性表示对一个单例bean的依赖： 

```
<bean id="beanOne" class="ExampleBean" depends-on="manager"/>
<bean id="manager" class="ManagerBean" />
```

表示对多个bean的依赖，可以为depends-on属性的值提供多个名字，使用逗号，空格或分号分割： 

```
<bean id="beanOne" class="ExampleBean" depends-on="manager,accountDao">
    <property name="manager" ref="manager" />
</bean>

<bean id="manager" class="ManagerBean" />
<bean id="accountDao" class="x.y.jdbc.JdbcAccountDao" />
```



## 7.5 bean 的作用域

当创建一个bean定义时，就给了一份创建那个类的实例的食谱。bean定义是一份食谱，这个想法很重要，因为那意味着，可以根据一份食谱创建很多实例。 

不仅可以控制bean实例的各种各样的依赖关系和配置值，还可以控制这些实例的作用域（scope).

singleton(单例)

prototype(原型)

request(请求)

session(会话)

globalSession（全局会话 )

application（应用） 

websocket 



## 7.9 基于注解的容器配置  Annotation-based container configuration    

#### 1.Are annotations better than XML for configuring Spring?    

一.

xml和注解方式哪一种更好用？

> 注解形式比XML形式更好吗？ 注解形式的引入引起了一个话题，它是否比XML形式更好。简单的回答是视情况而定。详细的回答是每一种方式都有它的优缺点，通常由开发者决定哪种方式更适合他们。由于他们定义的方式，注解提供了更多的上下文声明，导致了更短更简明的配置。然而，XML形式装配组件不会涉及到它们的源码或者重新编译它们。一些开发者更喜欢亲近源码，但另一些则认为注解类不是POJO，且配置很分散，难以控制。 不管做出什么选择，Spring都支持两种风格且可以混用它们。另外，通过Java配置的方式，Spring可以让注解变得非侵入式，不会触碰到目标组件的源码。而且，所有的配置方式Spring Tool Suite都支持。 

Spring 2.0引入了**@Required**注解，它强制属性必须获取一个值。Spring 2.5遵循同样的方式驱动依赖注入。本质上，**@Autowired**注解提供了相同的能力，如[7.4.5 自动装配合作者](https://blog.csdn.net/tangtong1/article/details/51960382#autowiring-collaborators)中描述的一样，不过它提供了更细粒度的控制和更广泛的适用性。Spring 2.5也支持JSR-250的注解，比如**@PostConstruct**和**@PreDestroy**。Spring 3.0支持JSR-330的注解，它们位于javax.inject包下，比如**@Inject**和**@Named**。 

二.

1.可以在构造方法上使用**@Autowired**

2.也可以在setter方法上使用**@Autowired** 

3.也可以应用在具有任意名字和多个参数的方法上： 

4.也可以应用在字段上，甚至可以与构造方法上混用： 

5.也可以从**ApplicationContext**中提供特定类型的所有bean，只要添加这个注解在一个那种类型的数组字段或方法上即可 

6.同样适用于集合类型 



**@Autowired， @Inject， @Resource**和**@Value**注解都是被Spring的**BeanPostProcessor**处理的，这反过来意味着我们不能使用自己的**BeanPostProcessor**或**BeanFactoryPostProcessor**类型来处理这些注解。这些类型必须通过XML或使用Spring的**@Bean**方法显式地装配。 



**@Resource**注解在字段和setter方法上进行注入 

**@Resource**拥有一个name属性，默认地，Spring会把这个name属性的值解释为将要注入的bean的名字。换句话说，它按照名字的语法进行注入，如下例所示： 

```
public class SimpleMovieLister {

    private MovieFinder movieFinder;

    @Resource(name="myMovieFinder")
    public void setMovieFinder(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }

}
```

如果没有提供名字，默认的名字从字段名或setter方法名派生而来。对于一个字段，它会取字段的名字，对于stter方法，它会取bean的属性名。所以，下面的方法会使用名字为“movieFinder”的bean注入到setter方法中。 

```
public class SimpleMovieLister {

    private MovieFinder movieFinder;

    @Resource
    public void setMovieFinder(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }

}
```

如果**@Resource**没有显式地指定名字，与**@Autowired**类似，它会寻找主要的（primary）类型匹配如果没有找到默认的名字，并解决众所周知的依赖：**BeanFactory, ApplicationContext, ResourceLoader, ApplicationEventPublisher,** 和**MessageSource**接口。 



## 7.10.1 @Component及其扩展注解

1.**@Repository**注解是一种用于标识存储类（也被称为数据访问对象或者DAO）的标记。异常的自动翻译是这个标记的用法之一 

2.Spring提供了一些扩展注解：**@Component， @Service**和**@Controller**。**@Component**可用于管理任何Spring的组件。**@Repository， @Service**和**@Controller**是**@Component**用于指定用例的特殊形式，比如，在持久层、服务层和表现层。使用**@Service**或**@Controller**能够让你的类更易于被合适的工具处理或与切面（aspect）关联。比如，这些注解可以使目标组件成为切入点。当然，**@Repository， @Service**和**@Controller**也能携带更多的语义。因此，如果你还在考虑使用**@Component**还是**@Service**用于注解service层，那么就选**@Service**吧，它更清晰 

3.元注解也可以组合起来形成组合注解。例如，**@RestController**注解是一种**@Controller**与**@ResponseBody**组合的注解。

 4.Spring能够自动检测被注解的类，并把它们注册到**ApplicationContext**中。例如，下面的两个会被自动检测到： 

```

```

```
@Repository
public class JpaMovieFinder implements MovieFinder {
    // implementation elided for clarity
}
```

为了能够自动检测到这些类并注册它们，需要为**@Configuration**类添加**@ComponentScan**注解，并设置它的**basePackage**属性为这两个类所在的父包（替代方案，也可以使用逗号、分号、空格分割这两个类所在的包）。 

```
@Configuration
@ComponentScan(basePackages = "org.example")
public class AppConfig  {
    ...
}
```

- 上面的配置也可以简单地使用这个注解的**value**属性，例如：**ComponentScan(“org.example”)**

5. Spring的组件也可以为容器贡献bean的定义元数据，只要在**@Component**注解的类内部使用**@Bean**注解即可。下面是一个简单的例子： 

   ```
   @Component
   public class FactoryMethodComponent {
   
       @Bean
       @Qualifier("public")
       public TestBean publicInstance() {
           return new TestBean("publicInstance");
       }
   
       public void doWork() {
           // Component method implementation omitted
       }
   
   }
   ```

   ### 7.11 使用JSR 330标准注解

   如果使用Maven，**javax.inject**也可以在标准Maven仓库中找到，添加如下配置到pom.xml即可 .

   ```
   
   ```

   #### 使用@Inject和@Named依赖注入

   可以像下面这样使用**@javax.inject.Inject**代替**@Autowired**： 

```
import javax.inject.Inject;

public class SimpleMovieLister {

    private MovieFinder movieFinder;

    @Inject
    public void setMovieFinder(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }

    public void listMovies() {
        this.movieFinder.findMovies(...);
        ...
    }
}
```

与**@Autowired**一样，可以在字段级别、方法级别或构造参数级别使用**@Inject**。另外，也可以定义注入点为**Provider**，以便按需访问短作用域的bean或通过调用**Provider.get()**延迟访问其它的bean。上面例子的一种变体如下： 

```
import javax.inject.Inject;
import javax.inject.Provider;

public class SimpleMovieLister {

    private Provider<MovieFinder> movieFinder;

    public void listMovies() {
        this.movieFinder.get().findMovies(...);
        ...
    }
}
```

如果你喜欢为依赖添加一个限定符，也可以像下面这样使用**@Named**注解 

```
import javax.inject.Inject;
import javax.inject.Named;

public class SimpleMovieLister {

    private MovieFinder movieFinder;

    @Inject
    public void setMovieFinder(@Named("main") MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }

    // ...
}
```

**7.11.2**

#### @Named：与@Component注解等价

### 7.12 基于Java的容器配置

#### @Bean和@Configuration   

Spring中基于Java的配置的核心内容是**@Configuration**注解的类和**@Bean**注解的方法。 

**@Bean**注解表示一个方法将会实例化、配置并初始化一个对象，且这个对象会被Spring容器管理。这就像在XML中**<beans/>**元素中**<bean/>**元素一样。**@Bean**注解可用于任何Spring的**@Component**注解的类中，但大部分都只用于**@Configuration**注解的类中。(可以在**@Configuration**或**@Component**注解的类中使用**@Bean**注解。 )

注解了**@Configuration**的类表示这个类的目的就是作为bean定义的地方。另外，**@Configuration**类内部的bean可以调用本类中定义的其它bean作为依赖。最简单的配置大致如下：

```
@Configuration
public class AppConfig {

    @Bean
    public MyService myService() {
        return new MyServiceImpl();
    }

}
```

上面的**AppConfig**类与下面的XML形式是等价的： 

```
<beans>
    <bean id="myService" class="com.acme.services.MyServiceImpl"/>
</beans>
```

> 全量的@Configuration和简化的@Bean模式？ 当@Bean方法不定义在@Configuration的类中时，它们会被一种简化的模式处理。例如，定义在@Component类或普通类中的@Bean方法。 不同于全量的@Configuration模式，简化的@Bean方法不能轻易地使用别的依赖。通常在简化械下一个@Bean方法不会调用另一个@Bean方法。 推荐在@Configuration类中使用@Bean方法，从而保证全量模式总是起作用。这样可以防止同一个@Bean方法被无意中调用多次，并减少一些狡猾的bug。 

##### 自定义bean的名称

默认地，使用@Bean默认的方法名为其bean的名字，然而这项功能可以使用name属性重写： 

```
@Configuration
public class AppConfig {

    @Bean(name = "myFoo")
    public Foo foo() {
        return new Foo();
    }

}
```

有时候可以想要给同一个bean多个名字，亦即别名，@Bean注解的name属性就可以达到这样的目的， 你可以为其提供一个String的数组。 

```
@Configuration
public class AppConfig {

    @Bean(name = { "dataSource", "subsystemA-dataSource", "subsystemB-dataSource" })
    public DataSource dataSource() {
        // instantiate, configure and return DataSource bean...
    }

}
```

##### 注入内部依赖:

```
@Configuration
public class AppConfig {

    @Bean
    public Foo foo() {
        return new Foo(bar());
    }

    @Bean
    public Bar bar() {
        return new Bar();
    }

}
```

在上例中，foo这个bean通过构造函数注入接收了bar的引用。 

##### 绑定Java与XML配置

> Spring的@Configuration类并不能100%地替代XML配置。一些情况下使用XML的命名空间仍然是最理想的方式来配置容器。在某些场景下，XML是很方便或必要的，你可以选择以XML为主，比如ClassPathXmlApplicationContext，或者以Java为主使用AnnotationConfigApplicationContext并在需要的时候使用@ImportResource注解导入XML配置。 

