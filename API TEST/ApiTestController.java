package pep.per.mint.front.controller.su;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.fasterxml.jackson.databind.ObjectMapper;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.RequestApi;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.su.ApiTestService;
import pep.per.mint.front.exception.ControllerException;
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
	
	@RequestMapping(method = RequestMethod.POST, value="/testUri", params="method=GET", headers="content-type=application/json")
	public @ResponseBody ComMessage<RequestApi, Object> uriTest(
			HttpSession httpSession, 
			@RequestBody ComMessage<RequestApi, Object> comMessage, Locale locale) 
					throws Exception, ControllerException  {
		
		String url = comMessage.getRequestObject().getUrl();
		System.out.println(url);
        // Request Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        
        // Request Body
        //Map<String,Object> requestBody =new HashMap<>();
      
        JSONObject requestBody = new JSONObject();
        requestBody.put("objectType" , "ComMessage");
        requestBody.put("requestObject", new JSONObject());
        requestBody.put("startTime", "20150701120001001");
        requestBody.put("endTime", "");
        requestBody.put("errorCd", "0");
        requestBody.put("errorMsg", "");
        requestBody.put("userId", "dmc");
        requestBody.put("appId", "");
        requestBody.put("checkSession", "false");
        
        //requestBody.put("member_id","3");
        
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.toString(), headers);
        
        //String url = "http://localhost:8080/mint/im/business/treemodel?method=GET";
       
        //System.out.println(url);


        // HTTP POST Request
        String responseEntity = restTemplate.postForObject(url, requestEntity, String.class);
        
        //HttpStatus statusCode = responseEntity.getStatusCode();
        String responseBody = responseEntity;
        
        ObjectMapper objectMapper = new ObjectMapper();
        responseBody = responseBody.replace("\\", "");
        Map<String, Object> result = objectMapper.readValue(responseBody, Map.class);
        comMessage.setResponseObject(result);
        comMessage.setEndTime(Util.getFormatedDate());
        
        //System.out.println("Response Status Code: " + statusCode);
        System.out.println("Response Body: " + responseBody); 
        System.out.println("comMessage : " + comMessage);
        
		return comMessage;
	}
}
