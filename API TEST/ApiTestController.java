package pep.per.mint.front.controller.su;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.RequestApi;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.su.ApiTestService;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.FieldCheckUtil;
import pep.per.mint.front.util.MessageSourceUtil;



@Controller
@RequestMapping("/su")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ApiTestController {
	
	private static final Logger logger = LoggerFactory.getLogger(ApiTestController.class);
	
	@Autowired
    private RestTemplate restTemplate;
	
	@Autowired
	ApiTestService apiTestService;
	
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;
	
	@Autowired
	private ServletContext servletContext;
	
	/**
	 * API-TEST
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(method = RequestMethod.POST, value="/testUri", params="method=GET", headers="content-type=application/json")
	public @ResponseBody ComMessage<RequestApi, Object> uriTest(
			HttpSession httpSession, 
			@RequestBody ComMessage<RequestApi, Object> comMessage, Locale locale, HttpServletRequest request) 
			throws Exception, ControllerException  {
		
			String url = comMessage.getRequestObject().getUrl();
			System.out.println(url);
	        
			// Request Headers
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	
	        Enumeration<String> header = request.getHeaderNames();
	        Map<String,Object> requestBody = (Map<String,Object>) comMessage.getRequestObject().getRequestData();
	        
	        
	        Map<String, Object> responseObject = apiTestService.getResponseObject(url, requestBody);
	        
	        
	        Map<String, String> headerObject = new HashMap<>();
	        while(header.hasMoreElements()) {
	        	String headerName = header.nextElement();
	        	String headerValue = request.getHeader(headerName);
	        	headerObject.put(headerName, headerValue);
	        }
	        //responseObject.put("header", headerObject);
	        
	        {
	        	comMessage.setEndTime(Util.getFormatedDate());
	        	comMessage.setResponseObject(responseObject);
	        }
	        
	        System.out.printf("ResponseObject : ", responseObject);
	        System.out.println("comMessage : " + comMessage);
	        
			return comMessage;
	}
}