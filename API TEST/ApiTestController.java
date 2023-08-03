package pep.per.mint.front.controller.su;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pep.per.mint.database.service.su.ApiTestService;

@Controller
@RequestMapping("/su")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ApiTestController {
	
	private static final Logger logger = LoggerFactory.getLogger(ApiTestController.class);
	
	@Autowired
	ApiTestService apiTestService;
	
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;
	
	@Autowired
	private ServletContext servletContext;

}
