package com.example.demo.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class formController {
	@RequestMapping(value="/form")
	public String form(){
	    return  "userForm";
	} 
	
	@RequestMapping(value="/submit")
	public String submit(User user,Model model){
		model.addAttribute(user);   //将数据返回前台
		System.out.println(user.toString());
		return "ok";
	}
}
