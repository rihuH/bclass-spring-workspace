package com.kh.hyper.common.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor // @Data가 매개변수 생성자는 안 만들어주니까 이거는 따로 적어줘야함
@Builder // 참고로 이 애노테이션은 매개변수 생성자 AllArgsConstructor가 꼭 있어야 쓸 수 있음!!!!!!!!
public class PageInfo {

	private int listCount;
	private int currentPage;
	private int boardLimit;
	private int pageLimit;
	
	private int maxPage;
	private int startPage;
	private int endPage;
	
}
