package com.example.demo.sec;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class securityController {
	@RequestMapping(value="/yml",method=RequestMethod.GET)
	public String yml() {
		return "security";
	}
	
	@GetMapping("/login")
	public String myMethod() {
		return "login";
	}
	
	@GetMapping("/")
	public String myMethod1() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication()
				.getPrincipal();
		return "index";
	}
	

}
