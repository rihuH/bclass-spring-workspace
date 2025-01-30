package com.kh.od.api.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
//@RequestMapping(produces="application/json; UTF-8") //json 쓸 때
@RequestMapping(produces="text/xml; charset=UTF-8") // html이라고 써도 됨 xml로 보내줄 때만 쓸 때
public class AirController {
	
	@GetMapping("air")
	public void airPollution() throws IOException {
		
		// 내가 java언어로 만든 client프로그램으로 API 서버로 요청 보내고 응답받기!
		
		//서버 요청보내려면 요청 URL이 먼저 있어야함
		// String자료형으로 URL 선언하기
		// 공공데이터 문서를 보고 따라사거나, (Call Back URL)
		
		String requestUrl = "https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		//String requestUrl = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty";
			   requestUrl += "?serviceKey=IpE6ECpBbgkjNehUyY4aZH64POkQWAbEvqR3E5hc2RIAJYBv6FDwrsez3lgMirV6BdoIX%2Bwlk%2FGWgxNR1ryNcw%3D%3D";
			   requestUrl += "&sidoName=" + URLEncoder.encode("서울", "UTF-8");
			   requestUrl += "&returnType=json";
			   
		
		//System.out.println(requestUrl);
			   
	    // 자바코드가지고 요청을 보낼 것
		// Connection => **  HttpURLConnection 객체를 활용해서 API 서버로 요청
	    // 1. java.net.URL 객체 생성 => 생성자 호출 시 인자값으로 requestURL을 전달
	    URL url = new URL(requestUrl);
	    // 2. URL객체를 이용해서 HttpURLConnection 객체 생성
	    HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
	    // 3. 요청에 필요한 Header 설정
	    urlConnection.setRequestMethod("GET");
	    // 4. API 서버와 스트림을 연결
	    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	    
	    /*
	    System.out.println(br.readLine());<?xml version="1.0" encoding="UTF-8"?>
	    System.out.println(br.readLine());<response>
	    System.out.println(br.readLine());  <header>
	    System.out.println(br.readLine());    <resultCode>00</resultCode>
	    System.out.println(br.readLine());    <resultMsg>NORMAL_CODE</resultMsg>
	    System.out.println(br.readLine());  </header>
	    한줄한줄 당겨올 수 없으므로 반복문
	    */
	    
	    /*
	    String responseXml = "";
	    while((responseXml = br.readLine()) != null) {
	    	System.out.println(responseXml);
	    }
	    이건 xml타입
	    json타입은 한 줄로 오기 때문에 반복문 필요 없다*/
	    System.out.println(br.readLine());
	    
	    // 공공데이터 활용 페이지에 샘플 코드가 있음. 그런 걸 보고 어떻게 사용하는지 참고해서 작성하는 것.
	    br.close();
	    urlConnection.disconnect();
	}
	
	@GetMapping("search.air")
	public ResponseEntity<String> searchAir(String sidoName) throws IOException, Exception {
		//ResponseEntity 스프링에서 응답 보내주려고 사용하는 객체. 설명 써있음
		String requestUrl = "https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
			   requestUrl += "?serviceKey=IpE6ECpBbgkjNehUyY4aZH64POkQWAbEvqR3E5hc2RIAJYBv6FDwrsez3lgMirV6BdoIX%2Bwlk%2FGWgxNR1ryNcw%3D%3D";
			   requestUrl += "&sidoName=" + URLEncoder.encode(sidoName, "UTF-8");
			   //requestUrl += "&returnType=json"; // xml로 보내보기 할 땐 주석처리
		
		// responseEntity 사용할 때 쓰는 것 자원반납같은거 해줌	   
		RestTemplate restTemplate = new RestTemplate();
		URI uri = new URI(requestUrl);
		// uri는 딱 하나만 찝는거고 url은 웹통신을 이용~ 등 방법까지 붙인 거.
		
		String response = restTemplate.getForObject(uri, String.class);
		//위 url을 통해 반환된 응답을 String.class에 담아서 restTemplate에 담는 것.
			   
		return ResponseEntity.ok(response);
			   
	}
	
	
}
