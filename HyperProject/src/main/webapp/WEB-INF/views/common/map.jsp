<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=8331371e351f3573c7aaab3b8bc5dc03"></script>
</head>
<body>

	<!-- 
		지도띄우기
		카카오지도 API => 앱 키 발급 => 로그인=> 카카오디벨로퍼스 => 내 애플리케이션 => 애플리케이션추가하기
		=> 저장하고 만든 것에 들어가기
		=> 왼쪽 바에 '플랫폼' => 우리는 웹 플랫폼 등록 => http://localhost:포트번호 우리가 돌리는 도메인 입력(80이면 안 써도 됨)
		=> 다시 카카오맵 API로 => 웹 누르기 => (왼쪽바에 가이드=> 시작하기에서 사실 다 읽을 수 있음)
		=> 시작하기를 똑같이 따라가기
		=> 왼쪽 바 하단에 '카카오맵'에서 활성화 버튼 누르기
		=> 왼쪽 바에 위자드로 설정해서 맞는 코드를 가져올 수 있따
	 -->
	 
	 <div id="map" style="width:500px;height:400px;"></div>
	 
	 <script>
		 var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
		 var options = { //지도를 생성할 때 필요한 기본 옵션
		 	center: new kakao.maps.LatLng(33.450701, 126.570667), //지도의 중심좌표.
		 	level: 3 //지도의 레벨(확대, 축소 정도)
		 };
	
		 var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
	 </script>
	 
	 

	

</body>
</html>