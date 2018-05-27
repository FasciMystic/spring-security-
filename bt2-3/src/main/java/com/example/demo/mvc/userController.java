package com.example.demo.mvc;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.command.ddl.Analyze;
import org.mockito.internal.stubbing.answers.AnswerReturnValuesAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class userController {
	@RequestMapping(value="/save",method=RequestMethod.POST)
    public String save(User user) {
     
       System.out.println(user.toString());	   	
	   return "success";
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public String delete(String id) {
		System.out.println("你要删除的id:"+id);
		return "success";
	}
	
//	@RequestParam(required="true")
	
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(@RequestParam Map<String,String> params) {
	      System.out.println("Map:"+params);
	      
	      Set set=params.entrySet();
	      Iterator iterator=set.iterator();
	      while(iterator.hasNext()) {
	    	  Entry<String,String> enter=(Entry<String, String>) iterator.next();
	    	  System.out.println(enter.getKey()+"\t"+enter.getValue());
	      }
	return "success";
	
  }
	@RequestMapping("/redirectTest")
	public String redirectTest(Model model) {
		
		User user=new User();
		model.addAttribute("redirectTest","123");
		return "success";
	}
	
/*	@ResponseBody
	@RequestMapping(value="/analyAlgorithmList",method=RequestMethod.POST)
	public String listWithMarshalling(@RequestBody User user,Model model,HttpServletRequest req,final HttpServletResponse resp,
		@RequestParam("models")String models){
		Map searchParameter=new HashMap();
		AnalyReturnDto returnDto=new AnalyReturnDto(objectMapper);
	try {
		searchParamters=objectMapper.readValues(models,
				new TypeReference<Map>() {
			
		});
		List<AnalyAlgorithmDto> algrithmList=anayAlgorithmService.findAlgorithmByTypeId(
				searchParameters.get("typeId").toString(),salt);
		return returnDto.toReturnString(1,AnalyReturnDto.SUCCESS,algorithmList);
		}catch(exception e){
	
}*/
	
	//使用@ResponseBody传json参
	@ResponseBody
	@RequestMapping("/jsonTest1")
	public User jsonTest1(HttpServletRequest request,Model model) {
		
	model.addAttribute("msg1","234");
	User user=new User();
	user.setName("lucy");
	user.setAddress("四川");
	return user;
	
	}
	
	@ResponseBody
	@RequestMapping("/json2")
	public void jsonTest2(@RequestBody User user,Model model) {
		model.addAttribute(user);
		System.out.print(user);
	}
	

	
	
	
  
}
	
	
	

