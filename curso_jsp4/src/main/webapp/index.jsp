<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>Curso de JSP</h1>

<form action="ServltLogin" method="post">
<input type = "hidden" value = <%= request.getParameter("url") %> name = "url">

<input name="login" type="text">
<input name="senha" type="password">
<input type="submit" value="enviar">

</form>

<h4>${msg}</h4>


</body>
</html>