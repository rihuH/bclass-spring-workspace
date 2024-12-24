package com.kh.hyper.board.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
// 나중에 세터 다 없어짐. vo특징은 불변객체로 한 번 생성되면 값이 바뀌면 안됨
// 값은 생성자로 넣어줄 것
@ToString
@Builder // board.builder().boardTitle()과 같이 세터할 수 있는 메소드가 있음 필드명이랑 똑같이 만들어져있음.
// .boardContent().boardWriter()... .build() 마지막에 이렇게 해주면 그 객체를 반환해줌
public class Board {

	// 일반게시글/ 사진게시글
	// 하나의 테이블
	// 파일저장하는 폴더
	
	//==> resources/uploard_files/바뀐파일명   을 한 번에 저장할 것임.
	// date타입은 string으로 관리할 것
	
	private Long boardNo; // long 말고 래퍼클래스 Long. 기본자료형의 가장 큰 문제는 null값을 가질 수 없다는 것.
	// DB에서 컬럼값이 없는 경우 null이 조회가 됨.
	// 그래서 pk에 해당하는 친구는 primitive타입보다는 wrapper클래스를 사용하는 것이 좋다
	// Long 이 Integer보다 표현할 수 있는 값의 범위가 많으니까 Long을 씀. 요즘은 자료가 많기 때문에
    private String boardTitle;     // BOARD_TITLE
    private String boardWriter;    // BOARD_WRITER
    private String boardContent;   // BOARD_CONTENT
    private String originName;     // ORIGIN_NAME
    private String changeName;     // CHANGE_NAME
    private int count;             // COUNT
    private String createDate;       // CREATE_DATE
    private String status; 
    
    
	
	
	
}
