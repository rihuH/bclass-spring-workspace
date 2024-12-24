package com.kh.hyper.common;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ModelAndViewUtil {
	
	public ModelAndView setViewNameAndData(String viewName, Map<String, Object> modelData) {
		// static의 장점. 객체생ㄷ성 안 해서 리소스 적게 드는 것은 장점
		// 다만 의존성이 높아지는 것이 단점
		
		//고급지게 표현=> 인스턴스화 해야함
		// => 우리가 매번 만들 수 ㄴ 없느ㅇ니 스프링에게 빈등록
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName(viewName);
		if(modelData != null) {
			mv.addAllObjects(modelData);
		}
		return mv;
	}

}
