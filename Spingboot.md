# Spring boot

### 11.3 编写代码

要完成我们的应用程序，我们需要创建一个的Java文件。 默认情况下，Maven将从src/main/java编译源代码，因此您需要创建该文件夹结构，然后添加一个名为src/main/java/Example.java的文件：

```
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class Example {

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Example.class, args);
    }

}
```

####  @RestController和@RequestMapping 注解：

我们的Example类的第一个注解是@RestController。 这被称为 stereotype annotation。它为人们阅读代码提供了一些提示，对于Spring来说，这个类具有特定的作用。在这里，我们的类是一个web @Controller，所以Spring在处理传入的Web请求时会考虑这个类。

@RequestMapping注解提供“路由”信息。 告诉Spring，任何具有路径“/”的HTTP请求都应映射到home方法。 @RestController注解告诉Spring将生成的字符串直接返回给调用者。





#### 15 配置类

Spring Boot支持基于Java的配置。虽然可以使用XML配置用SpringApplication.run()，但我们通常建议您的主source是@Configuration类。 通常，定义main方法的类也是作为主要的@Configuration一个很好的选择。 

#### 15.1 导入其他配置类

您不需要将所有的@Configuration放在一个类中。 @Import注解可用于导入其他配置类。 或者，您可以使用@ComponentScan自动扫描所有Spring组件，包括@Configuration类。

#### 15.2 导入XML配置

如果您必须使用基于XML的配置，我们建议您仍然从@Configuration类开始。 然后，您可以使用的@ImportResource注释来加载XML配置文件。



##  Spring Beans 和 依赖注入

我们可以自由使用任何标准 的Spring Framework技术来定义您的bean及其依赖注入关系。 为了简单起见，我们发现使用@ComponentScan搜索bean，结合@Autowired构造函数(constructor)注入效果很好。

如果按照上述建议（将应用程序类放在根包(root package)中）构建代码，则可以使用  @ComponentScan而不使用任何参数。 所有应用程序组件（@Component，@Service，@Repository，@Controller等）将自动注册为Spring Bean。  

## 23. SpringApplication

SpringApplication类提供了一种方便的方法来引导将从main()方法启动的Spring应用程序。 在许多情况下，您只需委派静态SpringApplication.run()方法： 

```
public static void main(String[] args) {
    SpringApplication.run(MySpringConfiguration.class, args);
}
```

### 23.2 自定义Banner

可以通过在您的类路径中添加一个 banner.txt 文件，或者将banner.location设置到banner文件的位置来更改启动时打印的banner。 

您可以在banner.txt文件中使用以下占位符： 

#### 表23.1. banner变量

| 变量名                                                       | 描述                                                         |
| :----------------------------------------------------------- | ------------------------------------------------------------ |
| ${application.version}                                       | 在MANIFEST.MF中声明的应用程序的版本号。例如， Implementation-Version: 1.0 被打印为 1.0. |
| ${application.formatted-version                              | 在MANIFEST.MF中声明的应用程序版本号的格式化显示（用括号括起来，以v为前缀）。 例如 (v1.0)。 |
| ${spring-boot.version}                                       | 您正在使用的Spring Boot版本。 例如1.5.2.RELEASE。            |
| ${spring-boot.formatted-version}                             | 您正在使用格式化显示的Spring Boot版本（用括号括起来，以v为前缀）。 例如（v1.5.2.RELEASE）。 |
| Ansi.NAME(orAnsi.NAME(or{AnsiColor.NAME}, AnsiBackground.NAME,AnsiBackground.NAME,{AnsiStyle.NAME}) |                                                              |
|                                                              |                                                              |

