Spring security

### 1.getting started

introduce Spring Security 4.0 

### 2.introduction

①Spring Security provides comprehensive security services for Java EE-based enterprise software applications.

②  two major areas of application security are "**authentication**" and "**authorization" (or "access-control").**  身份验证与授权。

③At an authentication level认证级别, Spring Security supports a wide range of authentication models.  Spring Security还提供了自己的一套认证功能。具体而言，Spring Security目前支持与所有这些技术的认证集成： 

> - HTTP BASIC认证头（基于IETF RFC的标准）
> - HTTP摘要认证头（基于IETF RFC的标准）
> - HTTP X.509客户端证书交换（基于IETF RFC的标准）
> - LDAP（跨平台认证需求的一种非常常见的方法，特别是在大型环境中）
> - 基于表单的认证（用于简单的用户界面需求）
> - OpenID认证
> - 基于预先建立的请求标题的认证（例如Computer Associates Siteminder）
> - Jasig中央身份验证服务（也称为CAS，这是一种流行的开源单点登录系统）
> - 远程方法调用（RMI）和HttpInvoker（Spring远程协议）的透明身份验证上下文传播
> - 自动“记住我”身份验证（这样您可以勾选一个框以避免在预定时间段内重新验证）
> - 匿名身份验证（允许每个未经身份验证的呼叫自动采用特定的安全身份）
> - 运行身份验证（如果一次呼叫应该继续使用不同的安全身份，这很有用）
> - Java认证和授权服务（JAAS）
> - Java EE容器认证（因此如果需要，您仍然可以使用容器管理认证）
> - Kerberos的
> - Java开源单点登录（JOSSO）*
> - OpenNMS网络管理平台*
> - AppFuse *
> - AndroMDA *
> - Mule ESB *
> - 直接Web请求（DWR）*
> - Grails *
> - 挂毯*
> - JTrac *
> - Jasypt *
> - 滚筒 *
> - 弹性路径*
> - Atlassian人群*  

### 2.2 history

Spring Security在2003年底开始称为“春季Acegi安全系统”。 今天，Spring Security拥有一个强大且活跃的开源社区 。

### 2.4 Getting Spring Security

①使用maven

最小的Spring Security Maven依赖关系集通常如下所示： 

**pom.xml中**

```
<dependencies> 
<！ -  ...其他依赖项元素...  - > 
<dependency> 
	<groupId> org.springframework.security </ groupId> 
	<artifactId> spring-security-web </ artifactId> 
	<version> 4.2 .6.RELEASE </ version> 
</ dependency> 
<dependency> 
	<groupId> org.springframework.security </ groupId> 
	<artifactId> spring-security-config </ artifactId> 
	<version> 4.2.6.RELEASE </ version > 
</ dependency> 
</ dependencies>
```

### 2.4.2 Gradle

A minimal Spring Security Gradle set of dependencies typically looks like the following:

 **build.gradle.**  

```
dependencies {
	compile 'org.springframework.security:spring-security-web:4.2.6.RELEASE'
	compile 'org.springframework.security:spring-security-config:4.2.6.RELEASE'
}
```

### 2.4.4 Checking out the Source

使用git来检查源代码。

Since Spring Security is an Open Source project, we’d strongly encourage you to check out the source code using git.  

To obtain the source for the project, use the following git command: 

```
git clone https://github.com/spring-projects/spring-security.git
```

### 3. What’s New in Spring Security 4.2

一些关于web improvements和Configuration improvements 的改进。

### 4. Samples and Guides (Start Here)

实例

[①](https://github.com/spring-projects/spring-security/tree/4.2.6.RELEASE/samples/javaconfig/helloworld)     Spring Security  和    an existing application using Java-based configuration. 

[②](https://github.com/spring-projects/spring-security/tree/4.2.6.RELEASE/samples/javaconfig/form)     Spring Security  和     an existing Spring Boot application.  

[③](https://github.com/spring-projects/spring-security/tree/4.2.6.RELEASE/samples/javaconfig/form)     Spring Security  和     an existing application using XML-based configuration. 

[④](https://github.com/spring-projects/spring-security/tree/4.2.6.RELEASE/samples/javaconfig/form)     Spring Security  和    an existing Spring MVC application. 

[⑤](https://github.com/spring-projects/spring-security/tree/4.2.6.RELEASE/samples/javaconfig/form)     Demonstrates how to create a custom login form. 

### 5. Java Configuration    (java配置)

5.1 Hello Web Security Java Configuration

**第一步** 是创建我们的Spring Security Java配置。该配置会创建一个**Servlet过滤器**，该过滤器`springSecurityFilterChain`负责应用程序中的所有安全性（保护应用程序URL，验证提交的用户名和密码，重定向到登录表单等）。

```
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.stereotype.Controller;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("password")
				.roles("USER");
	}

	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage("/login")
				.and().httpBasic()
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")); // logout
	}

}

```

[WebSecurityConfig](https://docs.spring.io/spring-security/site/docs/4.2.6.RELEASE/reference/htmlsingle/#jc-hello-wsca)仅包含有关如何验证用户的信息 。

### 5.1.1 AbstractSecurityWebApplicationInitializer

下一步 is to register the `springSecurityFilterChain` with the war.   This can be done in Java Configuration with [Spring’s WebApplicationInitializer support](https://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/mvc.html#mvc-container-config) in a Servlet 3.0+ environment. Not suprisingly, Spring Security provides a base class `AbstractSecurityWebApplicationInitializer` that will ensure the `springSecurityFilterChain` gets registered for you. 

### 5.1.2 AbstractSecurityWebApplicationInitializer without Existing Spring   (↓)

**If you are not using Spring or Spring MVC, you will need to pass in the `WebSecurityConfig` into** the **superclass** to ensure the configuration is picked up.  

```
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

	public SecurityWebApplicationInitializer() {
		super(WebSecurityConfig.class);
	}
}
```

### 5.1.3 AbstractSecurityWebApplicationInitializer with Spring MVC（↑）

如果我们在应用程序的其他地方使用Spring，我们可能已经有了一个`WebApplicationInitializer`加载我们的Spring配置的东西。如果我们使用以前的配置，我们会得到一个错误。相反，我们应该向现有的Spring Security注册`ApplicationContext`。 

```
import org.springframework.security.web.context.*;

public class SecurityWebApplicationInitializer
	extends AbstractSecurityWebApplicationInitializer {

}
```

## 5.2 HttpSecurity

迄今为止，我们的[WebSecurityConfig](https://docs.spring.io/spring-security/site/docs/4.2.6.RELEASE/reference/htmlsingle/#jc-hello-wsca)仅包含有关如何验证用户的信息。Spring Security如何知道我们想要求所有用户进行身份验证？Spring Security如何知道我们想要支持基于表单的身份验证？原因是`WebSecurityConfigurerAdapter`在`configure(HttpSecurity http)`方法中提供了一个默认配置，如下所示： 

```
protected void configure(HttpSecurity http) throws Exception {
	http
		.authorizeRequests()
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.and()
		.httpBasic();
}
```

### 5.3 Java Configuration and Form Login

#### （Java配置和表单登录）

```
<c:url value="/login" var="loginUrl"/>
<form action="${loginUrl}" method="post">       1
	<c:if test="${param.error != null}">        2
		<p>
			Invalid username and password.
		</p>
	</c:if>
	<c:if test="${param.logout != null}">       3
		<p>
			You have been logged out.
		</p>
	</c:if>
	<p>
		<label for="username">Username</label>
		<input type="text" id="username" name="username"/>	4
	</p>
	<p>
		<label for="password">Password</label>
		<input type="password" id="password" name="password"/>	5
	</p>
	<input type="hidden"                        6
		name="${_csrf.parameterName}"
		value="${_csrf.token}"/>
	<button type="submit" class="btn">Log in</button>
</form>
```

| [![1](https://docs.spring.io/spring-security/site/docs/4.2.6.RELEASE/reference/htmlsingle/images/callouts/1.png)](https://docs.spring.io/spring-security/site/docs/4.2.6.RELEASE/reference/htmlsingle/#CO2-1) | A POST to the `/login` URL will attempt to authenticate the user |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [![2](https://docs.spring.io/spring-security/site/docs/4.2.6.RELEASE/reference/htmlsingle/images/callouts/2.png)](https://docs.spring.io/spring-security/site/docs/4.2.6.RELEASE/reference/htmlsingle/#CO2-2) | If the query parameter `error` exists, authentication was attempted and failed |
| [![3](https://docs.spring.io/spring-security/site/docs/4.2.6.RELEASE/reference/htmlsingle/images/callouts/3.png)](https://docs.spring.io/spring-security/site/docs/4.2.6.RELEASE/reference/htmlsingle/#CO2-3) | If the query parameter `logout` exists, the user was successfully logged out |
| [![4](https://docs.spring.io/spring-security/site/docs/4.2.6.RELEASE/reference/htmlsingle/images/callouts/4.png)](https://docs.spring.io/spring-security/site/docs/4.2.6.RELEASE/reference/htmlsingle/#CO2-4) | The username must be present as the HTTP parameter named *username* |
| [![5](https://docs.spring.io/spring-security/site/docs/4.2.6.RELEASE/reference/htmlsingle/images/callouts/5.png)](https://docs.spring.io/spring-security/site/docs/4.2.6.RELEASE/reference/htmlsingle/#CO2-5) | The password must be present as the HTTP parameter named *password* |

### 5.4 Authorize Requests（授权请求）

```
protected void configure(HttpSecurity http) throws Exception {
	http
		.authorizeRequests()                                                        1
			.antMatchers("/resources/**", "/signup", "/about").permitAll()           2
			.antMatchers("/admin/**").hasRole("ADMIN")                            3
			.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")     4
			.anyRequest().authenticated()                                            5
			.and()
		// ...
		.formLogin();
}
```

| [![1](https://docs.spring.io/spring-security/site/docs/4.2.6.RELEASE/reference/htmlsingle/images/callouts/1.png)](https://docs.spring.io/spring-security/site/docs/4.2.6.RELEASE/reference/htmlsingle/#CO3-1) | 该`http.authorizeRequests()`方法有多个孩子，每个匹配者按照他们声明的顺序考虑。 |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [![2](https://docs.spring.io/spring-security/site/docs/4.2.6.RELEASE/reference/htmlsingle/images/callouts/2.png)](https://docs.spring.io/spring-security/site/docs/4.2.6.RELEASE/reference/htmlsingle/#CO3-2) | 我们指定了任何用户都可以访问的多个网址格式。具体来说，如果URL以“/ resources /”开头，等于“/ signup”或等于“/ about”，则任何用户都可以访问请求。 |
| [![3](https://docs.spring.io/spring-security/site/docs/4.2.6.RELEASE/reference/htmlsingle/images/callouts/3.png)](https://docs.spring.io/spring-security/site/docs/4.2.6.RELEASE/reference/htmlsingle/#CO3-3) | 任何以“/ admin /”开头的网址都将限制为具有“ROLE_ADMIN”角色的用户。您会注意到，由于我们正在调用该`hasRole`方法，我们不需要指定“ROLE_”前缀。 |
| [![4](https://docs.spring.io/spring-security/site/docs/4.2.6.RELEASE/reference/htmlsingle/images/callouts/4.png)](https://docs.spring.io/spring-security/site/docs/4.2.6.RELEASE/reference/htmlsingle/#CO3-4) | 任何以“/ db /”开头的URL都需要用户同时拥有“ROLE_ADMIN”和“ROLE_DBA”。你会注意到，因为我们正在使用`hasRole`表达式，所以我们不需要指定“ROLE_”前缀。 |
| [![五](https://docs.spring.io/spring-security/site/docs/4.2.6.RELEASE/reference/htmlsingle/images/callouts/5.png)](https://docs.spring.io/spring-security/site/docs/4.2.6.RELEASE/reference/htmlsingle/#CO3-5) | 任何尚未匹配的URL只需要用户进行身份验证                      |

### 5.5 Handling Logouts （处理注销）

When using the `WebSecurityConfigurerAdapter`, logout capabilities are automatically applied. The default is that accessing the URL `/logout` will log the user out by: 

- 使HTTP会话无效
- 清理已配置的任何RememberMe认证
- 清除 `SecurityContextHolder`
- 重定向到 `/login?logout`

```
protected void configure(HttpSecurity http) throws Exception {
	http
		.logout()                                                                1
			.logoutUrl("/my/logout")                                                 2
			.logoutSuccessUrl("/my/index")                                           3
			.logoutSuccessHandler(logoutSuccessHandler)                              4
			.invalidateHttpSession(true)                                             5
			.addLogoutHandler(logoutHandler)                                         6
			.deleteCookies(cookieNamesToClear)                                       7
			.and()
		...
}
```

通常，为了自定义注销功能，可以添加 `LogoutHandler` 和/或 `LogoutSuccessHandler` 实现。 

