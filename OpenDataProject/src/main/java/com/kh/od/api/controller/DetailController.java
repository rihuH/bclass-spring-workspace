package com.kh.od.api.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
public class DetailController {

	
	@GetMapping("detail/{pk}")
	public String goDetail(@PathVariable(name="pk") int pk, Model model) throws URISyntaxException {
		
		String requestUrl = "http://apis.data.go.kr/6260000/FoodService/getFoodKr";
		   requestUrl += "?serviceKey=IpE6ECpBbgkjNehUyY4aZH64POkQWAbEvqR3E5hc2RIAJYBv6FDwrsez3lgMirV6BdoIX%2Bwlk%2FGWgxNR1ryNcw%3D%3D";
		   requestUrl += "&numOfRows=10";
		   requestUrl += "&pageNo="+1;
		   requestUrl += "&resultType=json";
		   requestUrl += "&UC_SEQ="+pk;
		   
		   
		URI uri = new URI(requestUrl);
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(uri, String.class);
		
		// GSON 라이브러리 (2.8.6 버전 이삿ㅇ에서만 됨
		// JsonObject, JsonArray
		// 얘네를 가지고 문자열 파싱 가능
		
		JsonObject jsonObj = JsonParser.parseString(response).getAsJsonObject();
		
		//System.out.println(response);
		System.out.println(jsonObj);
		
		JsonObject getFood = jsonObj.getAsJsonObject("getFoodKr"); // getFoodKr
		System.out.println(getFood);
		
		JsonArray itemArr = getFood.getAsJsonArray("item"); // item
		System.out.println(itemArr);
		
		JsonObject item = itemArr.get(0).getAsJsonObject();
		System.out.println(item);
		
		String lat = item.get("LAT").getAsString(); // 값을 뽑는데, 스트링타입으로 뽑음
		System.out.println(lat);
		
		String lng = item.get("LNG").getAsString();
		String day = item.get("USAGE_DAY_WEEK_AND_TIME").getAsString();
		String img = item.get("MAIN_IMG_NORMAL").getAsString();
		String desc = item.get("ITEMCNTNTS").getAsString();
		String title = item.get("TITLE").getAsString();
		String address = item.get("ADDR1").getAsString();
		
		model.addAttribute("lat", lat);
		model.addAttribute("lng", lng);
		model.addAttribute("day", day);
		model.addAttribute("img", img);
		model.addAttribute("desc", desc);
		model.addAttribute("title", title);
		model.addAttribute("address", address);
		
		
		return "busan/detail";
		
	}
}
