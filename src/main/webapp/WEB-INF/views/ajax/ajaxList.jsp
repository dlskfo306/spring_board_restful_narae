<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ajax</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript">
	/*    $.ajax({
	   url : '서비스 주소'
	   , data : '서비스 처리에 필요한 인자값'
	   , type : 'HTTP방식' (POST/GET 등)
	   , dataType : 'return 받을 데이터 타입' (json, text 등)
	   , success : function('결과값'){
	   // 서비스 성공 시 처리 할 내용
	   }, error : function('결과값'){
	   // 서비스 실패 시 처리 할 내용
	   }
	}); */

	function getList() {
		var url = "${pageContext.request.contextPath}/rest/after.json";
		//비동기
		$
				.ajax({
					type : 'GET',//'',"" 다 상관없는데 GET, POST는 대문자
					url : url,//위에있는 var url 주소임
					cache : false, // 이걸 안쓰거나 true하면 수정해도 값반영이 잘안댐
					dataType : 'json',// 데이터 타입을 제이슨 꼭해야함
					success : function(result) {
						var htmls = "";

						$("#list_table").html("");

						$(
								"<tr>",
								{
									html : "<td>" + "번호" + "</td>" + // 컬럼명들
									"<td>" + "이름" + "</td>" + 
									"<td>" + "제목" + "</td>" + 
									"<td>" + "날짜" + "</td>" + 
									"<td>" + "히트" + "</td>" + 
									"<td>" + "삭제" + "</td>"
								}).appendTo("#list_table") // 이것을 테이블에붙임

						if (result.length < 1) {
							htmls.push("등록된 댓글이 없습니다.");
						} else {

							$(result)
									.each(
											function() {

												htmls += '<tr>';
												htmls += '<td>' + this.bid + '</td>';
												htmls += '<td>' + this.bname + '</td>';
												htmls += '<td>'
												for (var i = 0; i < this.bindent; i++) { //for 문은 시작하는 숫자와 종료되는 숫자를 적고 증가되는 값을 적어요. i++ 은 1씩 증가 i+2 는 2씩 증가^^
													htmls += '-'
												}
												htmls += '<a href="${pageContext.request.contextPath}/content_view?bId='+ this.bid + '">' + this.btitle + '</a></td>';
												htmls += '<td>' + this.bdate + '</td>';
												htmls += '<td>' + this.bhit + '</td>';
												htmls += '<td>' + '<a class="a-delete" href="${pageContext.request.contextPath}/ajax/delete?bid=' + this.bid + '">' + '삭제</a>' + '</td>';
												htmls += '</tr>';

											}); //each end

							htmls += '<tr>';
							htmls += '<td colspan="5"> <a href="${pageContext.request.contextPath}/write_view">글작성</a> </td>';
							htmls += '</tr>';

						}

						$("#list_table").append(htmls);
					}
				}); //Ajax end

	}//end getList
</script>

<script>
//$("a-delete").click(function(){
	//정적인 것.
	//한마디로 동적인 것은 클릭이벤트 먹지 않음.
	
	$(document).ready(function() {
		$(document).on("click", ".a-delete", function(event) {
			//위에서 class말고 id로 쓸 수 있지만
			//id는 단수 : 유일무이 한 선택자
			//class는 복수일 때 쓴다.
			
			//prevendDefault()는 href로 연결해 주지 않고 단순히 click에 대한 처리를 하도록 해준다.
			event.preventDefault();
			console.log("ajax 호출전");
			//해당 tr제거
			var trObj = $(this).parent().parent();

			//Form대신 Ajax 비동기통신 = 부분갱신 = 깜빡임 없음
			$.ajax({
				type : "GET",
				url : $(this).attr("href"),
				success : function(result) {
					console.log(result);
					if (result == "SUCCESS") {
						//getList();
						$(trObj).remove();

					}
				},
				error : function(e) {
					console.log(e);
				}
			})
		});
	});
</script>


<script>
	$(document).ready(function() {
		getList();
		console.log("이건 언제 찍을까요");
	});
</script>
</head>
<body>
	<table id="list_table" width="500" cellpadding="0" cellspacing="0" border="1">
	</table>
</body>
</html>