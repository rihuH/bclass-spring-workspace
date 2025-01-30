package com.kh.od.api.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value="busan", produces="application/json; charset=UTF-8")
public class BusanController {

	@GetMapping
	public ResponseEntity<?> getBusan(int page) throws Exception{
		
		//String serviceKey = "서비스키";
		//String decodingKey = URLDecoder.decode(serviceKey, StandardCharsets.UTF_8);
		// 서비스키 들어가야 하는 자리에 decodingKey를 넣기 가끔 저주받은 키를 받는 사람이 있음.
		
		String requestUrl = "http://apis.data.go.kr/6260000/FoodService/getFoodKr";
			   requestUrl += "?serviceKey=IpE6ECpBbgkjNehUyY4aZH64POkQWAbEvqR3E5hc2RIAJYBv6FDwrsez3lgMirV6BdoIX%2Bwlk%2FGWgxNR1ryNcw%3D%3D";
			   requestUrl += "&numOfRows=10";
			   requestUrl += "&pageNo="+page;
			   requestUrl += "&resultType=json";
			   
		URI uri = new URI(requestUrl);
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(uri, String.class);
		
		
		return ResponseEntity.ok(response);
	}
	
}
