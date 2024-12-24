package com.kh.hyper.member.model.vo;

import java.sql.Date;

import lombok.Data;

/*
 * lombok은 STS.exe파일이 있는 곳에 같이 들어가있게 된다. ini파일에도 그 설정이 추가됨(직접할수있지만 보통 그냥 실행시켜서 자동추가함)
 * 
 * 이걸 코드를 다이어트 시킨다고 표현하기도 함
 * 
 * Lombok(롬복)
 * - 자동 코드 생성 라이브러리
 * Lombok 설치방법
 * 1. 라이브러리를 다운로드
 * 2. 다운로드 된 .jar파일을 찾아서 설치(작업할 IDE를 체크)
 * 3. IDE 재실행
 * 
 *  @Getter
 *  @Setter
 *  @ToString
 *  @NoArgsConstructor
 *  -------------------------
 *  @Builder
 *  @AllArgsConstructor
 *  -------------------------
 *  @Data :VO가 갖춰야할 게터 세터 생성자 투스트링, 이콜/해시코드 모두 만들어줌. 대신 유연성이 부족해짐 뭘 뺄 수가 없으니까, 전체생성자는 안만들어주는듯
 *  
 *  
 *  - Lombok 사용시 주의사항
 *  Lombok의 나름의 명명규칙이 있는데, 예로 pName이라는 필드는 앞에걸 대문자로 바꿔서 setPName()/ getPName() 이렇게 만들어버림
 *  
 *  => getter메소드를 마이바티스 등으로 내부적으로 호출하는 경우,
 *  ${pName} #{pName}등에서, 이런 경우에 getpName() 처럼 생긴 메소드를 찾게 됨.
 *  롬복이 만드는 메소드명이랑 실제 호출하는 메소드명이랑 모양이 달라진다
 *  
 *  따라서 외자로 시작하면 안됨, 성의있게 필드명을 지어야함
 *  
 * 
 */
/*
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
*/
@Data
public class Member {
	
	private String userId;
	private String userPwd;
	private String userName;
	private String email;
	private String gender;
	private String age;
	private String phone;
	private String address;
	private Date enrollDate;
	private Date modifyDate;
	private String status;
	/*
	 * 필드 한 번 바꾸면 너무 다 바꿔야함
	 * package Exploer에 x자로 바뀌는 패키지로 감
	 * Member를 열면
	 * 빨강이 필드, c가 생성자, 그 밑에가 메소드
	 * 
	 * 일단 예전 버전 코드를 Member클래스 아래코드에서 다 지움
	 * 그리고 라이브러리를 추가함
	 * 우리는 라이브러리를 Maven으로 관리함
	 * Maven은 pom을 사용함
	 * pom.xml 파일에다가 dependency 태그에 가서 라이브러리 추가==> pom.xml로 이동
	 * 
	 * 
	 */
	
	/*
	 * 롬복 설치하고 돌아옴
	 * 이제 직접 만들지 않고
	 * @NoArgsConstructor태그를 달아준다
	 * 우리눈에는 안 보이지만 생겨난것을 packageExplorer에서 확인할 수 있음.
	 * @AllArgsConstructor : 모든 매개변수가 있는 생성자
	 * @Getter / @Setter / @ToString
	 * 
	 * 이제 필드가 바뀌거나, 추가되거나 한다면 롬복이 알아서 바꿔줌 삭제하면 그것도 알아서 없애줌
	 * 
	 */

	
	

}
