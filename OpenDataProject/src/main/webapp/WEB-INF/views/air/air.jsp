<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공기오염정도확인</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</head>
<body>

	<h1>지역별 대기오염 상태</h1>
	
	시/도 : <br>
	<select id="sidoName">
		<option>전국</option>
		<option>서울</option>
		<option>부산</option>
		<option>대구</option>
		<option>인천</option>
		<option>광주</option>
		<option>대전</option>
		<option>울산</option>
		<option>경기</option>
		<option>강원</option>
		<option>충북</option>
		<option>충남</option>
		<option>전북</option>
		<option>전남</option>
		<option>경북</option>
		<option>경남</option>
		<option>제주</option>
		<option>세종</option>
	</select>
	
	<br>
	
	<button class="btn btn-info" id="btn1">해당 지역 대기 정보 확인하기</button>
	<br><br>
	
	<script>
		$(function(){
			// 실습!
			// 해당 지역 대기 정보확인하기 버튼을 클릭하면
			// 현재 선택된 option의 Content영역의 값을 alert창으로 출력하기
			$('#btn1').click(function(){
				//alert($('#sidoName').val());
				
				
				
				$.ajax({
					url : 'search.air',
					data : {
						sidoName : $('#sidoName').val()
					},
					success : result => {
						//console.log(result);
						//$('tbody').html(result);
						/*
						JSON 형태 가공한 거~~~
						const items = result.response.body.items;
						const str = items.map(e => `
								<tr>
									<td>\${e.stationName}</td>
									<td>\${e.pm10Value}</td>
									<td>\${e.coValue}</td>
									<td>\${e.dataTime}</td>
								</tr>
								`).join('');
						
						$('tbody').html(str);
						*/
						
						//console.log(result);
						
						// jQuery => find() // 후손요소 찾아가기
						
						//console.log($(result).find('item'));
						
						const items = $(result).find('item');
						
						//console.log(items); 이건 jQuery 객체다
						
						let value='';
						items.each((i, item) => {
							value += `<tr>
									<td>\${$(item).find('stationName').text()}</td>
									<td>\${$(item).find('pm10Value').text()}</td>
									<td>\${$(item).find('coValue').text()}</td>
									<td>\${$(item).find('dataTime').text()}</td>
									</tr>`;
						});
						
						$('tbody').html(value);
						
					}
					
				})
			})
		});
		
		
		//stationName : 측정소명
		// pm10Value : 미세먼지농도
		// dataTime : 측정일시
		// coValue : 일산화탄소 ㄴㅇ도
		
	</script>
	
	<table class="table table-hover">
		<thead>
			<tr>
				<th>측정소명</th>
				<th>미세먼지농도</th>
				<th>일산화탄소농도</th>
				<th>측정일시</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th colspan="4">지역을 선택하시고 조회버튼을 클릭해주세요~</th>
			</tr>
		</tbody>
	</table>
	
	
	
</body>
</html>