<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.6.4.min.js"></script>
<script>
	function check()
	{
		if($.trim($("#subject").val())=="")
		{
			alert("제목을 입력하세요");
			$("#subject").val("").focus();
			return false;
		}
		if($.trim($("#password").val())=="")
		{
			alert("비밀번호를 입력하세요");
			$("#password").val("").focus();
			return false;
		}
		if($.trim($("#contents").val())=="")
		{
			alert("내용을 입력하세요");
			$("#contents").val("").focus();
			return false;
		}
	}
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<center>
		<h2>게시글 쓰기</h2>
		<form:form action="setBoardWrite.do" method="post" commandName="boardVO" onsubmit="return check()">
			<input type="hidden" name="member_id" value="${sessionScope.member.member_id}">
			<table width="600" border="1">
				<tr height="40">
					<td width="200" align="center"> 작성자 </td>
					<td width="400" ><input type="text" name="name" size="30" value="${sessionScope.member.name}" readonly="readonly"></td>
				</tr> 
				<tr height="40">
					<td width="200" align="center"> 제목 </td>
					<td width="400" ><input id="subject" type="text" name="subject" size="30"></td>
				</tr> 
				<tr height="40">
					<td width="200" align="center"> 패스워드 </td>
					<td width="400" ><input id="password" type="password" name="password" size="30"></td>
				</tr> 
				<tr height="40">
					<td width="200" align="center"> 글내용 </td>
					<td width="400" > <textarea id="contents" rows="10" cols="50" name="contents"></textarea> </td>
				</tr>
				<tr height="40">
					<td colspan="2" align="center" >
					<input type="submit" value="글작성"> &nbsp;&nbsp;&nbsp;
					<input type="button" value="전체글보기" onclick="location.href='getBoardList.do'"> </td>
				</tr> 
			</table>
		</form:form>
	</center> 
</body>
</html>