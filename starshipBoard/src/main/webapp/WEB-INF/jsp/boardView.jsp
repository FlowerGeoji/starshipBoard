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
<title>게시판 보기 Test</title>
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
	
	function replyWrite()
	{
		jQuery.ajax({
			type: 'POST',
			url : './setReplyWrite.do',
			data : jQuery('#replyWriteForm :input').serialize()
		}).done(function (data)
				{
					var message = jQuery(data).find("message").text();
					var error = jQuery(data).find("error").text();
					if(error == 'false')
						location.reload();
					else
						alert(message);
				});
	}
	
	function reReplyWrite(replyId, replyLevel)
	{
		var temp = '#replyForm_'+replyId+' :input';
		//alert(jQuery(temp).serialize());
		jQuery.ajax({
			type: 'POST',
			url : './setReReplyWrite.do',
			data : jQuery(temp).serialize()
		}).done(function (data)
				{
					var message = jQuery(data).find("message").text();
					var error = jQuery(data).find("error").text();
					if(error == 'false')
						location.reload();
					else
						alert(message);
				});
	}
	
	function replyAppend(replyId)
	{
		replyId.style.visibility = 'visible';
	}
	</script>
	
	<center>
		<h2>게시글 보기</h2>
		<c:choose>
			<c:when test="${not empty sessionScope.member}">
				<form id="logoutForm" action="logoutProc" method="post">
					<table border="0">
						<tr>
						 <td>ID : ${sessionScope.member.member_id }</td>
						 <td>/</td>
						 <td>이름 : ${sessionScope.member.name }</td>
						 <td align="center"><input id="logoutBtn" type="button" value="로그아웃" onclick="logout();"></td>
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
							<td align="center"><input id="loginBtn" type="button" value="로그인" onclick="login();"></td>
						</tr>
					</table>
				</form>
			</c:otherwise>
		</c:choose>
		<table width="600" border="1">
			<tr height="40">
				<td width="100" align="center"> 작성자 </td>
				<td width="200" >${board.writer }</td>
				<td width="100" align="center"> 작성일 </td>
				<td width="200" >${board.reg_date }</td>
			</tr> 
			<tr height="40">
				<td width="100" align="center"> 제목 </td>
				<td width="500" colspan="3">${board.subject }</td>
			</tr> 
			<tr height="40">
				<td width="200" align="center"> 글내용 </td>
				<td width="400" colspan="3">
					${board.contents }
				</td>
			</tr> 
			<tr height="40">
				<td colspan="4" align="center" > 
				<input type="button" value="전체글보기" onclick="location.href='getBoardList.do'">&nbsp;&nbsp;&nbsp;
				<c:if test="${sessionScope.member.member_id == board.member_id}">
					<input type="button" value="수정" onclick="location.href='getBoardUpdate.do?member_id=${board.member_id}&board_id=${board.board_id }'">&nbsp;&nbsp;&nbsp;
					<input type="button" value="삭제" onclick="location.href='getBoardDelete.do'">&nbsp;&nbsp;&nbsp;
				</c:if>
				</td>
			</tr> 
		</table>
		<br/>
		<br/>
		<c:choose>
			<c:when test="${not empty sessionScope.member}">
				<form id="replyWriteForm" action="" method="post" commandName="replyVO">
					<input type="hidden" name="member_id" value="${sessionScope.member.member_id }">
					<input type="hidden" name="board_id" value="${board.board_id }">
					<input type="hidden" name="board_member_id" value="${board.member_id }">
					<table width="600" border="1">
						<tr>
							<td width="400"><textarea rows="3" cols="60" name="reply"></textarea></td>
							<td width="200"><input type="button" value="댓글등록" onclick="replyWrite();"></td>
						</tr>
					</table>
				</form>
			</c:when>
			<c:otherwise>
				로그인하시면 댓글 및 답글을 작성하실 수 있습니다.
			</c:otherwise>
		</c:choose>
		
		<c:forEach var="line" items="${replyList }">
			<form id="replyForm_${line.reply_id}" action="setReReplyWrite" commandName="replyVO" method="post">
				<table width="600" style="border:solid 0 #000000; border-top-width:2px; table-layout:fixed;">
					<tr>
						<td rowspan="4" width="${(line.rlevel-1)*50 }">
							<input type="hidden" name="rlevel" value="${line.rlevel }">

						</td>
						<td width="${600-((line.rlevel-1)*50) }">${line.name }&nbsp;<font size="0.5">(${line.reg_date })</font></td>
					</tr>
					<tr>
						<td width="${600-((line.rlevel-1)*50) }" style="word-wrap:break-word; word-break:break-all;">
							<pre>${line.reply }</pre>
							<input type="hidden" name="reply_id" value="${line.reply_id }">
							<input type="hidden" name="group_id" value="${line.group_id }">
							<input type="hidden" name="member_id" value="${sessionScope.member.member_id }">
							<input type="hidden" name="board_id" value="${board.board_id }">
							<input type="hidden" name="board_member_id" value="${board.member_id }">
						</td>
					</tr>
					<c:if test="${line.rlevel<=1 && not empty sessionScope.member}">
<!-- 						<tr> -->
<%-- 							<td align="right" colspan="2"><input type="button" value="[답글]" onclick="replyAppend(${line.reply_id})"></td> --%>
<!-- 						</tr> -->
						<tr>
							<td><textarea rows="1" cols="50" name="reply"></textarea></td>
							<td><input type="button" value="등록" onclick="reReplyWrite(${line.reply_id },${line.rlevel })"></td>
						</tr>
					</c:if>
				</table>
			</form>
		</c:forEach>
	</center>
</body>
</html>