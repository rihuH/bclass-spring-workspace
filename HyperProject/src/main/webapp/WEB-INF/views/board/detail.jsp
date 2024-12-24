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

        table * {margin:5px;}
        table {width:100%;}
    </style>
</head>
<body>
        
    <jsp:include page="../common/menubar.jsp" />

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 상세보기</h2>
            <br>

			<button onclick="history.back();">안녕 나는 히스토리백 버튼이야</button>
            <a class="btn btn-secondary" style="float:right;" href="/hyper/boards">목록으로</a>
            <!-- 두개는 동적 메커니즘이 다름.
            보통 이용한 페이지는 기록으로 남음(캐싱)되어 우리의 로컬에 저장되어있음.
            히스토리백은 방금 전에 보고 있던 페이지를 캐싱해둔 것을 다시 보여주는 것. 그래서 조회수 증가나 새로운 글 올라온 것도 보이지 않음
            목록으로(다시 href를 설정한) 누른 경우는 서버에 다시 요청을 보내서 요청받은 데이터를 출력하는 것.
            히스토리백은 예를 들어 여러 퀴즈문제를 풀 때 앞에 것 풀고 전에 것을 다시 보고싶은 경우에 사용
            목록으로 버튼에는 href로 요청보내는 것이 적합
             -->
            <br><br>

            <table id="contentArea" algin="center" class="table">
                <tr>
                    <th width="100">제목</th>
                    <td colspan="3">${ board.boardTitle }</td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>${ board.boardWriter }</td>
                    <th>작성일</th>
                    <td>${ board.createDate }</td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    
                    <c:choose>
					<c:when test="${ empty board.originName }" >                   
                    <td colspan="3">
                        첨부파일이 존재하지 않습니다.
                    </td>
                    </c:when>
                    <c:otherwise>
                    <td colspan="3">
                        <a href="${ board.changeName }" download="${ board.originName }">${board.originName }</a>
                    </td>
                    </c:otherwise>
                    </c:choose>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3"></td>
                </tr>
                <tr>
                    <td colspan="4"><p style="height:150px;">${board.boardContent }</p></td>
                </tr>
            </table>
            <br>

            <div align="center">
                <!-- 수정하기, 삭제하기 버튼은 이 글이 본인이 작성한 글일 경우에만 보여져야 함 -->
                
                 
                <c:if test="${ sessionScope.loginUser.userId eq requestScope.board.boardWriter }">
	                <a class="btn btn-primary" onclick="postSubmit(1);">수정하기</a>
	                <a class="btn btn-danger" onclick="postSubmit(2);">삭제하기</a>
                </c:if>
                <!-- a태그를 사용하는 겟방식은 url 조작 위험이 있어서 위험.
                 post로 보내고 싶은데ㅡ, post로 보내는 방법은 form태그방식밖에 배우지 않았음
                 -->
                 
                <!-- 
                	case 1: 수정하기 누르면 수정할 수 있는 입력 양식이 있어야함
                			입력양식에는 원본 게시글 정보들이 들어있어야함
                 	case 2: 삭제하기 누르면 Board 테이블가서 STATUS컬럼 'N'으로 바꾸고 혹시 첨부파일도 있었다면 같이 지워주고
                 -->
<!--                  
                 <form action="/hyper/boards/update-form" method="post">
                 	<input type="hidden" name="boardNo" value="${board.boardNo }"/>
                 	<button>수정하기</button>
                 </form>
                 <form action="/hyper/boards/delete" method="post">
                 	<input type="hidden" name="boardNo" value="${board.boardNo }">
                 	<button>삭제하기</button>
                 </form>
                 중복이니까 
 -->
				 <form action="/hyper/boards/update-form" method="post" id="postForm">
                 	<input type="hidden" name="boardNo" value="${board.boardNo }"/>
                 	<input type="hidden" name="changeName" value="${board.changeName }">
                 	<!--
                 	로그인된 사용자랑 요청보낸 애랑 같은애인지 이거로 확인하거나 인터셉터 ㅎ만들어서 하거나 
                 	<input type="hidden" name="userId" value="\${loginUser.userId "}/>
                 	 -->
                 	<input type="hidden" name="boardWriter" value="${board.boardWriter }"/>
                 </form>
                 <script>
                 	function postSubmit(num){
                 		if(num == 1){
                 			$('#postForm').attr('action', '/hyper/boards/update-form').submit();
                 		} else{
							$('#postForm').attr('action', '/hyper/boards/delete').submit();                 			
                 		}
                 	}
                 </script>
                 
            </div>
            
            
            
            
            <br><br>



            <!-- 댓글 기능은 나중에 ajax 배우고 나서 구현할 예정! 우선은 화면구현만 해놓음 -->
            <table id="replyArea" class="table" align="center">
                <thead>
                	<c:choose>
                		<c:when test="${empty sessionScope.loginUser }">
                    <tr>
                        <th colspan="2">
                            <textarea class="form-control" readonly cols="55" rows="2" style="resize:none; width:100%;">로그인 후 이용 가능합니다.</textarea>
                        </th>
                        <th style="vertical-align:middle"><button class="btn btn-secondary">등록하기</button></th> 
                    </tr>
                    	</c:when>
                    	<c:otherwise>
                    <tr>
                        <th colspan="2">
                            <textarea class="form-control" name="" id="content" cols="55" rows="2" style="resize:none; width:100%;"></textarea>
                        </th>
                        <th style="vertical-align:middle"><button class="btn btn-secondary" onclick="addReply();">등록하기</button></th> 
                    </tr>
                    	</c:otherwise>
                    </c:choose>
                    <tr>
                        <td colspan="3">댓글(<span id="rcount">0</span>)</td>
                    </tr>
                </thead>
                <tbody>
                	
                    <!-- ajax로 할 거니까 버튼이 submit버튼이 아니어도된다. form 태그 안에 있는것도 아니고 id로 선택할거니까 name속성도 필요없고 submit type도 아니어도 된다.
                    이벤트는 필요하다. -->
                	
                
                
                </tbody>
            </table>
        </div>
        <br><br>

    </div>
    
    
    <script>
    	function addReply(){
    		if($('#content').val().trim() != ''){
    			$.ajax({
    				url : '/hyper/reply',
    				data : {
    					refBoardNo : ${board.boardNo}, // EL구문
    					replyContent : $('#content').val(), // jQuery
    					replyWriter : '${sessionScope.loginUser.userId}' // EL구문인데 문자열로 넘겨야 해서 ''로
    				},
    				type : 'post',
    				success : function(result){
    					console.log(result);
    					
    					/*시즌1
    					if(result === 1){
    						$('#content').val('');
    					}*/
    					/*시즌2*/
    					if(result.data ===1){
    						$('#content').val('');
    					}
    				}
    			})
    		}
    	}
    	
    	function selectReply(){
    		$.ajax({
    			url : '/hyper/reply',
    			type : 'get',
    			data : {
    				boardNo : ${board.boardNo}	
    			},
    			success : function(result){
    				//console.log(result);
    				const replies = [...result.data];
    				console.log(replies);
    				const resultStr = replies.map(e => 
    					//console.log(e);
								    					`<tr>
								    					<td>\${e.replyWriter}</td>
								    					<td>\${e.replyContent}</td>
								    					<td>\${e.createDate}</td>
								    					</tr>`
    											).join('');
    				$('#replyArea tbody').html(resultStr);
    				$('#rcount').text(result.data.length);
    			}
    		});
    	}
    	
    	$(function(){
    		selectReply();
    	})
    
    </script>
    
    <jsp:include page="../common/footer.jsp" />
    
</body>
</html>