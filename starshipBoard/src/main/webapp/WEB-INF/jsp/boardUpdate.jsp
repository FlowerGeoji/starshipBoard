<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	<script>
		function boardWrite()
		{
			jQuery.ajax({
				type : 'POST',
				url : './setBoardUpdate.do',
				data : jQuery('#boardUpdateForm :input').serialize()
			}).done(function (data)
					{
						var message = jQuery(data).find("message").text();
						var error = jQuery(data).find("error").text();
						if (error == 'false')
							location.href = './getBoardView.do?member_id=${sessionScope.member.member_id}&board_id=${board.board_id}';
						else
							alert(message);
					});
		}
	</script>
	
	<center>
		<h2>게시글 수정</h2>
		<form:form id="boardUpdateForm" action="setBoardUpdate.do" method="post" commandName="boardVO" >
			<input type="hidden" name="member_id" value="${sessionScope.member.member_id}">
			<input type="hidden" name="board_id" value="${board.board_id }">
			<table width="600" border="1">
				<tr height="40">
					<td width="200" align="center"> 작성자 </td>
					<td width="400" ><input type="text" name="name" size="30" value="${sessionScope.member.name}" readonly="readonly"></td>
				</tr> 
				<tr height="40">
					<td width="200" align="center"> 제목 </td>
					<td width="400" ><input type="text" name="subject" value="${board.subject }" size="30"></td>
				</tr> 
				<tr height="40">
					<td width="200" align="center"> 패스워드 </td>
					<td width="400" ><input type="password" name="password" size="30"></td>
				</tr> 
				<tr height="40">
					<td width="200" align="center"> 글내용 </td>
					<td width="400" >
						<textarea rows="10" cols="50" name="contents">${board.contents }</textarea>
					</td>
				</tr>
				<tr height="40">
					<td colspan="2" align="center" >
					<input type="button" value="글수정" onclick="boardWrite();"> &nbsp;&nbsp;&nbsp;
					<input type="button" value="전체글보기" onclick="location.href='getBoardList.do'"> </td>
				</tr> 
			</table>
		</form:form>
	</center> 
</body>
</html>