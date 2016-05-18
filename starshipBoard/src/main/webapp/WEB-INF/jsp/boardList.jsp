<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="true" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<title>Starhship 게시판 테스트</title>
</head>
<body>
	<script>
		function login()
		{
			jQuery.ajax({
				type : 'POST',
				url : './loginProc.do',
				data : jQuery('#loginForm :input').serialize()
			}).done(function (data)
					{
						var message = jQuery(data).find("message").text();
						var error = jQuery(data).find("error").text();
						if (error == 'false')
							location.reload();
						else
							alert(message);
					});
		}
		function logout()
		{
			jQuery.ajax({
				type : 'POST',
				url : './logoutProc.do',
				data : 1
			}).done(function (data)
					{
						var message = jQuery(data).find("message").text();
						var error = jQuery(data).find("error").text();
						if (error == 'false')
							location.reload();
						else
							alert(message);
					});
		}
	</script>
	
	<center>
	<h2>Test 게시판</h2>
		<c:choose>
			<c:when test="${not empty sessionScope.member}">
				<form action="logoutProc" method="post">
					<table border="0">
						<tr>
						 <td>ID : ${sessionScope.member.member_id }</td>
						 <td>/</td>
						 <td>이름 : ${sessionScope.member.name }</td>
						 <td align="center"><button id="logoutBtn" type="button" onclick="logout();">로그아웃</button></td>
						</tr>
					</table>
				</form>
			</c:when>
			<c:otherwise>
				<form id="loginForm" action="loginProc" method="post">
					<table>
						<tr>
							<td>ID : </td>
							<td><input type="text" name="member_id"></td>
							<td>PW : </td>
							<td align="center"><input type="password" name="password"></td>
							<td align="center"><input type="button" value="로그인" onclick="login();"/></td>
							<td align="center"><input type="button" value="회원가입" onclick="location.href='getJoin.do'"></td>
						</tr>
					</table>
				</form>
			</c:otherwise>
		</c:choose>
		<table width="800" border="1">
			<thead>
			<tr height="40" align="center"><td width="100" align="center">글번호</td>
				<td width="300" align="center">글제목</td>
				<td width="100" align="center">작성자</td>
				<td width="200" align="center">작성일</td>
			</tr>
			</thead>
			
			<tbody>
			<c:forEach var="line" items="${boardList}">
				<tr height="40">
					<td width="100" align="center">
						${line.rnum }
					</td>
					<td width="100" align="left">
						<a href="getBoardView.do?member_id=${line.member_id }&board_id=${line.board_id}" style="text-decoration:none">
						${line.subject }</a>
					</td>
					<td width="200" align="center">
						${line.writer }
					</td>
					<td width="100" align="center">
						${line.reg_date }
					</td>
				</tr>
			</c:forEach>
			<c:if test="${allBoardCount==0}">
				<tr>
					<td colspan="4" align="center">${allBoardCount }</td>
				</tr>
			</c:if>
			</tbody>
			
			<tfoot>
			<tr height="40">
				<c:choose>
					<c:when test="${not empty sessionScope.member }">
						<td colspan="5" align="right"><button onclick="location.href='getBoardWrite.do'">글쓰기</button></td>
					</c:when>
					<c:otherwise>
						<td colspan="5" align="right">글을 쓰시려면 로그인이 필요합니다.</td>
					</c:otherwise>
				</c:choose>
			</tr>
			</tfoot>
			
		</table>
		
		<!-- 페이징 처리  -->
		<c:forEach var="i" begin="${startPageNum }" end="${endPageNum }">
			<c:if test="${i==currentPageNum }">
				<font color="#ff0000">[${i }]</font>
			</c:if>
			<c:if test="${i!=currentPageNum }">
				<font color="#000088"><a href="getBoardList.do?pageNum=${i }">[${i }]</a></font>
			</c:if>
		</c:forEach>
	</center>
</body>
</html>