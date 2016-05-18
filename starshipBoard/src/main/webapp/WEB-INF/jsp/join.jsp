<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.6.4.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script>
	function check()
	{
		if($.trim($("#member_id").val())=="")
		{
			alert("아이디를 입력하세요");
			$("#member_id").val("").focus();
			return false;
		}
		if($.trim($("#password").val())=="")
		{
			alert("비밀번호를 입력하세요");
			$("#password").val("").focus();
			return false;
		}
		if($.trim($("#name").val())=="")
		{
			alert("이름을 입력하세요");
			$("#name").val("").focus();
			return false;
		}
	}
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원가입</title>
</head>
<body>
	<script>
	function join()
	{
		jQuery.ajax({
			type : 'POST',
			url : './setJoin.do',
			data : jQuery('#joinForm :input').serialize()
		}).done(function (data)
				{
					var message = jQuery(data).find("message").text();
					var error = jQuery(data).find("error").text();
					if (error == 'false')
					{
						alert(message);
						location.href = 'getBoardList.do';
					}
					else
						alert(message);
				});
	}
	</script>
	
	<center>
		<form id="joinForm" action="setJoin.do" method="post" commandName="memberVO" onsubmit="return check()">
			<table style="border:solid 0 #000000;">
				<tr>
					<td>ID</td>
					<td>:</td>
					<td><input id="member_id" type="text" name="member_id"></td>
				</tr>
				<tr>
					<td>PASSWORD</td>
					<td>:</td>
					<td><input id="password" type="text" name="password"></td>
				</tr>
				<tr>
					<td>NAME</td>
					<td>:</td>
					<td><input id="name" type="text" name="name"></td>
				</tr>
					<td><input type="button" value="회원가입" onclick="join();"></td>
				<tr>
				</tr>
				<tr>
					<td><input type="button" value="취소" onclick="location.href='getBoardList.do'"></td>
				</tr>
			</table>
		</form>
	</center>
</body>
</html>