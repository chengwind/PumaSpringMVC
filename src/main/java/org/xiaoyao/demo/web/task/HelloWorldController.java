package org.xiaoyao.demo.web.task;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloWorldController {

	@RequestMapping("/hello")
	public String hello(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
		System.out.println("######1 name= " + name);
		model.addAttribute("name", name);
//		UrlBasedViewResolver urlResolver = new UrlBasedViewResolver();
//		urlResolver.setPrefix("/WEB-INF/views/");
//		urlResolver.setSuffix(".jsp");
		return "helloworld";
	}

}
