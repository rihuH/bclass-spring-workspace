package com.kh.od.api.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value="horse", produces="application/json; charset=UTF-8")
public class HorseController {
	
	@GetMapping
	public ResponseEntity<String> youCanCenter(int pageNo) throws Exception {
		
		String url="https://apis.data.go.kr/B551015/API304/ucan_info"; // http로 하라고 쌤이 하는데,나는 그러면 안되어서..
		url += "?serviceKey=IpE6ECpBbgkjNehUyY4aZH64POkQWAbEvqR3E5hc2RIAJYBv6FDwrsez3lgMirV6BdoIX%2Bwlk%2FGWgxNR1ryNcw%3D%3D";
		url += "&pageNo=" + pageNo;
		url += "&numOfRows=3";
		
		RestTemplate restTemplate = new RestTemplate();
		URI uri = new URI(url);
		String response = restTemplate.getForObject(uri, String.class);
		return ResponseEntity.ok(response);
	}

}
