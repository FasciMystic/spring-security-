package com.example.demo.Class;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClassService {
	
	@Autowired
	private SignInService signInService;
	
	public String Shuchu() {
		
	    List<String> us=new ArrayList<>();
	    while(true){
	    	System.out.println("1.签到     2.查询   ");
	    	System.out.println("请选择执行数字：");
	    	Scanner scanner=new Scanner(System.in);
	    	int c=scanner.nextInt();
	    	switch(c) {
	    	case 1:System.out.println("1.输入姓名  2.退出   请选择：");
	    	
	    	       int s=scanner.nextInt();
	    	       if(s==1) {
	    	    	   System.out.println("请输入签到姓名：");
	   				   String name = scanner.next();
	   				   boolean flag=false;
	   				   //跳转判断
	    	    	   String n=signInService.signIn(name,flag);
	    	    	   if(n!=null) {
	    	    		   System.out.println("签到成功！");
	    	    	   }else {
	    	    		   System.out.println("签到失败");
	    	    	   }
	    	       }else {
	    	    	   break;
	    	       }
	    	
	    	case 2:
	    		//查询
	    		System.out.println("请输入要查询的学生姓名：");
	    		String qname=scanner.next();
	    		signInService.query(qname);
	    		break;
	    		
	    	default:
	    		System.out.println("输入正确选择！");
	    	}
	    }
	    
	}
	
	

}
