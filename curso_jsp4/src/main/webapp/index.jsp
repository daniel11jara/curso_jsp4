<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

 <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<title>Insert title here</title>

<style type="text/css">

form {
position: absolute;
top: 40%;
left: 33%;
}

h1 {
position: absolute;
top: 31%;
left: 33%;
}

.msg {
position: absolute;
top: 70%;
left: 33%;
font-color: red;
}


</style>



</head>
<body>

<h1>Curso de JSP</h1>

<form action="ServltLogin" method="post" class="row g-3">
<input type = "hidden" value = <%= request.getParameter("url") %> name = "url">

<div class="col-md-6">
<label class="form-label">Login</label>
<input name="login" type="text" class="form-control">
</div>

<div class="col-md-6">
<label class="form-label">Senha</label>
<input class="form-control" name="senha" type="password">
</div>


<input type="submit" value="Acessar" class="btn btn-primary">


</form>

<h3 class = "msg">${msg}</h3>

<!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

</body>
</html>