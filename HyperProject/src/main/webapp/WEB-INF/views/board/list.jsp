<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style>
        .content {
            background-color:rgb(247, 245, 245);
            width:80%;
            margin:auto;
        }
        .innerOuter {
            border:1px solid lightgray;
            width:80%;
            margin:auto;
            padding:5% 10%;
            background-color:white;
        }

        #boardList {text-align:center;}
        #boardList>tbody>tr:hover {cursor:pointer;}

        #pagingArea {width:fit-content; margin:auto;}
        
        #searchForm {
            width:80%;
            margin:auto;
        }
        #searchForm>* {
            float:left;
            margin:5px;
        }
        .select {width:20%;}
        .text {width:53%;}
        .searchBtn {width:20%;}
    </style>
</head>
<body>
    
    <jsp:include page="../common/menubar.jsp" />

    <div class="content">
        <br><br>
        <div class="innerOuter" style="padding:5% 10%;">
            <h2>게시판</h2>
            <br>
            <!-- 로그인 후 상태일 경우만 보여지는 글쓰기 버튼 -->
            <c:if test="${ not empty sessionScope.loginUser }">
            <a class="btn btn-secondary" style="float:right;" href="insertForm">글쓰기</a>
            </c:if>
            <br>
            <br>
            <table id="boardList" class="table table-hover" align="center">
                <thead>
                    <tr>
                        <th>글번호</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>조회수</th>
                        <th>작성일</th>
                        <th>첨부파일</th>
                    </tr>
                </thead>
                <tbody>
                <!-- Map으로 오면 키값만 적어주면 됨 -->
                <c:forEach  items="${ boards }" var="board"> 
                     <tr onclick="detail(${board.boardNo})">
                        <td>${ board.boardNo }</td>
                        <td>${ board.boardTitle }</td>
                        <td>${ board.boardWriter }</td>
                        <td>${ board.count }</td>
                        <td>${ board.createDate }</td>
                        <td>
                        	<c:if test="${not empty board.originName }">
                        		💌                        	
                        </c:if>
                        </td>
                    </tr>
                 </c:forEach>
                </tbody>
            </table>
            <br>
            <script>
            	function detail(num){
            		//console.log(num);
            		// boards/게시글번호 로 요청
            		location.href = `boards/\${num}`;
            	}
            </script>
            

            <div id="pagingArea">
                <ul class="pagination">
                    
                    <c:choose>
                    <c:when test="${pageInfo.currentPage ne 1 }">
                    <li class="page-item disabled"><a class="page-link" href="boards?page=${pageInfo.currentPage - 1 }">이전</a></li>
                    </c:when>
                    <c:otherwise>
                    <li class="page-iten disabled"><a class="page-link" href="#" ></a></li>
                    
                    </c:otherwise>
                    </c:choose>
                    
                    <c:forEach begin="${ pageInfo.startPage }" end="${ pageInfo.endPage }" var="num">
                    	<li class="page-item">
                    		<a class="page-link" href="boards?page=${ num }">
                    			${ num }
                    		</a>
                    	</li>
                    </c:forEach>
                    
                    <li class="page-item"><a class="page-link" href="#">다음</a></li>
                </ul>
            </div>
            
            <!-- 깜짝 퀴즈
            	스프링의 DI란??
            	Dependency Injection이란??
            	객체간의 의존관계를 스프링컨테이너가 직접 관리하고 주입하는 것
            	의존성이란 : 자바에서 하나의 객체가 다른 객체와 관계를 가지는 것 예를 들어서 메소드를 호출하는 관계는 의존하고 있다고 이야기할 수 있다
            	본래 개발자가 직접 객체생성, 타입정하여 의존관계를 정해줬는데 이걸 스프링이 관리할 수 있도록 스프링빈으로 등록하여 주입받아 사용하는 것을 이야기한다.
            	그걸 위해 인터페이스를 사용하고, 인터페이스를 통해 다형성 적용시켜서
            	타입만을 가지고 사용
            	롬복라이브러리ㅡ를 사용하기 때문에, 세터 필드 생성자 주입 중 생성자주입을 사용하게 됨
            	객체 결합도가 많이 낮춰져 있기 때문에 변형이 쉽가
            	유연성 증가
             -->

            <br clear="both"><br>

            <form id="searchForm" action="" method="get" align="center">
                <div class="select">
                    <select class="custom-select" name="condition">
                        <option value="writer">작성자</option>
                        <option value="title">제목</option>
                        <option value="content">내용</option>
                    </select>
                </div>
                <div class="text">
                    <input type="text" class="form-control" name="keyword">
                </div>
                <button type="submit" class="searchBtn btn btn-secondary">검색</button>
            </form>
            <br><br>
        </div>
        <br><br>

    </div>

    <jsp:include page="../common/footer.jsp" />

</body>
</html>