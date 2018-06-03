package com.example.demo.sec;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
//@EnableWebSecurity  旧版本
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

	protected void registerAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user") // #1
				.password("password").roles("USER").and().withUser("admin") // #2 Role角色
				.password("password").roles("ADMIN", "USER");
	}

	@Override
	public void configure(WebSecurity web) throws Exception { // web
		web.ignoring().antMatchers("/resources/**"); // #3 忽略resource开头的请求
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception { // http，跳过所有额静态资源
		http.authorizeRequests().antMatchers("/signup", "/about").permitAll() // #4
				.antMatchers("/admin/**").hasRole("ADMIN") // #6 只有admin才能访问
				.anyRequest().authenticated() // #7
				.and().formLogin() // #8 form提交方式的登录
				.loginPage("/login") // #9 注释该行显示原有登录界面，否则跳转自己定义的“login”界面
				.permitAll(); // #5
	}
}
