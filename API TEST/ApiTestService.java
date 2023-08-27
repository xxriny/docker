package pep.per.mint.database.service.su;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import oracle.net.aso.r;

@Service
@SuppressWarnings("unchecked")
public class ApiTestService {
   
    Logger logger = LoggerFactory.getLogger(this.getClass());
   
    @Autowired
    private RestTemplate restTemplate;
    
    public Map<String, Object> getResponseObject(String url, Map<String, Object> requestBody) throws Exception{
       
       String responseEntity = restTemplate.postForObject(url, requestBody, String.class);
       
       String responseBody = responseEntity;
       
       ObjectMapper objectMapper = new ObjectMapper();
       responseBody = responseBody.replace("/\\/g", "");
       
      Map<String, Object> responseObject = objectMapper.readValue(responseBody,Map.class);
      
      return responseObject;
    }
   
}