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
	@SuppressWarnings("unused")
	@RequestMapping(method = RequestMethod.POST, value="/testUri", params="method=GET", headers="content-type=application/json")
	public @ResponseBody ComMessage<RequestApi, Object> uriTest(
			HttpSession httpSession, 
			@RequestBody ComMessage<RequestApi, Object> comMessage, Locale locale, HttpServletRequest request) 
			throws Exception, ControllerException  {
		{
			String errorCd = "";
			String errorMsg = "";
			
				
				//url get
				String url = comMessage.getRequestObject().getUrl();
				System.out.println(url);
				
				//headers type 세팅
				HttpHeaders headers = new HttpHeaders();
			    headers.setContentType(MediaType.APPLICATION_JSON);
			    
			    //header get
			    Enumeration<String> header = request.getHeaderNames();
			    
			    //repsponse object 조회
			  
				Map<String,Object> requestBody = (Map<String,Object>) comMessage.getRequestObject().getRequestData();
				Map<String, Object> responseObject = apiTestService.getResponseObject(url, requestBody);
			   
		        
		        Map<String, String> headerObject = new HashMap<>();
		        while(header.hasMoreElements()) {
		        	String headerName = header.nextElement();
		        	String headerValue = request.getHeader(headerName);
		        	headerObject.put(headerName, headerValue);
		        }
		        
		        responseObject.put("header", headerObject);
		        
				//--------------------------------------------------
				// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
				//--------------------------------------------------
		        {
		        	comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
		        }
		        
		       {
		    	   if(responseObject == null) { //가져오는 것에 실패했을 시
		    		   errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
		    		   errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.request.required.param", null, locale);
		    	   } else {
		    		   errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
		    		   errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
		    		   comMessage.setResponseObject(responseObject);
		    	   }
		    	   comMessage.setErrorCd(errorCd);
		    	   comMessage.setErrorMsg(errorMsg);
		       }
		       return comMessage;

		}

	}
}