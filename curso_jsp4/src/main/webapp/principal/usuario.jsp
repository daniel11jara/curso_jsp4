<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html lang="en">

<!-- comando para incluir a pagina que contem apenas o cabecalho: head.jsp -->
<jsp:include page="head.jsp"></jsp:include>


  <body>
  
  <jsp:include page="theme-loader.jsp"></jsp:include>
  
  <!-- Pre-loader end -->
  <div id="pcoded" class="pcoded">
      <div class="pcoded-overlay-box"></div>
      <div class="pcoded-container navbar-wrapper">
          
          <jsp:include page="navbar.jsp"></jsp:include>

          <div class="pcoded-main-container">
              <div class="pcoded-wrapper">
                  
                  <jsp:include page="navbarmainmenu.jsp"></jsp:include>
                  
                  <div class="pcoded-content">
                      <!-- Page-header start -->
                      
                      <jsp:include page="page-header.jsp"></jsp:include>
                      
                      
                      <!-- Page-header end -->
                        <div class="pcoded-inner-content">
                            <!-- Main-body start -->
                            <div class="main-body">
                                <div class="page-wrapper">
                                    <!-- Page-body start -->
                                    <div class="page-body">
                                        
                                      		
                                      		
                                      		<div class="row">
                                            <div class="col-sm-12">
                                                <!-- Basic Form Inputs card start -->
                                                <div class="card">
                                                   
                                                    <div class="card-block">
                                                        <h4 class="sub-title">CADASTRO DO USUARIO</h4>
                                      		
                                      		<form class="form-material"  action="<%= request.getContextPath() %>/ServletUsuarioController" method="post" id = "formUser">
                                      		
                                      		<input type="hidden" name = "acao" id = "acao" value = "">
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="id" id="id"  class="form-control" readonly="readonly" value="${modelLogin.id}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">ID:</label>
                                                            </div>
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="nome" id="nome" class="form-control" required="required"  value="${modelLogin.nome}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Nome:</label>
                                                            </div>
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="email" name="email" id="email" class="form-control" required="required" autocomplete="off" value="${modelLogin.email}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Email:</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="login" id="login" class="form-control" required="required" autocomplete="off" value="${modelLogin.login}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Login</label>
                                                            </div>
                                                            
                                                            
                                                            <div class="form-group form-default">
                                                                <input type="password" name="senha" id="senha" class="form-control" required="required" autocomplete="off" value="${modelLogin.senha}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Senha</label>
                                                            </div>
                                                            
                                                            <button type="button" class="btn btn-primary waves-effect waves-light" onclick="limparForm();">Novo</button>
            												<button class="btn btn-success waves-effect waves-light">Salvar</button>
           													 <button type="button" class="btn btn-info waves-effect waves-light" onclick="criarDelete()">Excluir</button>
           													 <button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#exampleModal">Pesquisar</button>
                                                        </form>
                                             </div>
                                             </div>
                                             </div>
                                             </div>
                                             <span id = "msg">${msg}</span>

										<div style="height: 300px; overflow: scroll;">
											<table class="table" id="tabelaresultadosview">
												<thead>
													<tr>
														<th scope="col">ID</th>
														<th scope="col">Nome</th>
														<th scope="col">Ver</th>

													</tr>
												</thead>
												<tbody>
													<c:forEach items="${modelLogins}" var="ml">
													
													<tr>
													<td><c:out value="${ml.id}"></c:out></td>
													<td><c:out value="${ml.nome}"></c:out></td>
													<td><a class="btn btn-success" href="<%= request.getContextPath() %>/ServletUsuarioController?acao=buscarEditar&id=${ml.id}" >Ver</a></td>
													</tr>
													
													</c:forEach>
												</tbody>
											</table>

										</div>

									</div>
                                    <!-- Page-body end -->
                                </div>
                                <div id="styleSelector"> </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
<jsp:include page="javascriptfile.jsp"></jsp:include> 

<!-- MODAL -->>
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Pesquisar Usuario</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      
      <div class="input-group mb-3">
  <input type="text" class="form-control" placeholder="Nome" aria-label="nome"  id = "nomeBusca"  aria-describedby="basic-addon2">
  <div class="input-group-append">
    <button class="btn btn-success" type="button" onclick="buscarUsuario();">Buscar</button>
  </div>
</div>

<div style = "height: 300px; overflow : scroll;">
<table class="table" id="tabelaresultados">
  <thead>
    <tr>
      <th scope="col">ID</th>
      <th scope="col">Nome</th>
      <th scope="col">Ver</th>

    </tr>
  </thead>
  <tbody>
    
  </tbody>
</table>
      
</div> 

<span id="totalResultados"> </span>    
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
        
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">

//aula 47
function verEditar(id){
	var urlAction = document.getElementById('formUser').action;
	
	window.location.href = urlAction + '?acao=buscarEditar&id=' + id;
}

function buscarUsuario(){
	
	//nomeBusca = o id do input do campo de busca
	//pegando o nome do usuario digitado no campo de busca
	var nomeBusca = document.getElementById('nomeBusca').value;
	
	//validacoes para buscar no banco de dados
	if(nomeBusca != null && nomeBusca != '' && nomeBusca.trim() != ''){
		alert(nomeBusca);
		
		var urlAction = document.getElementById('formUser').action;
		
		$.ajax({
			method:"get",
			url : urlAction,
			data : "nomeBusca=" + nomeBusca + '&acao=buscarUserAjax',
			success : function (response) {
				
				var json = JSON.parse(response);
				
				$('#tabelaresultados > tbody > tr').remove();
				
				//for para imprimir na tela do modal a tabela com o nome buscado (aula 46)
				for (var p = 0; p < json.length; p++){
					$('#tabelaresultados > tbody').append('<tr> <td>' +json[p].id+ '</td> <td>' +json[p].nome+ '</td> <td><button onclick="verEditar('+json[p].id')" type="button" class="btn btn-info">Ver</button></td> </tr>');
				}
				
				//colocando na tela o total de nomes encontrados
				document.getElementById('totalresultados').textContent = 'Resultados: ' + json.length;
			} 
		}).fail(function(xhr, status, errorThrown){
			alert('Erro ao buscar usuario por nome: ' + xhr.responseText)
		});
	}

}


function criarDelete(){
	
	if(confirm("Deseja realmente excluir o registro")) {
		
		var elementos = document.getElementById("formUser").method = 'get';
		var elementos = document.getElementById("acao").value = 'deletar';
		var elementos = document.getElementById("formUser").submit();
	}
	
	
}


function limparForm() {
	var elementos = document.getElementById("formUser").elements;
	
	for (p = 0; p < elementos.length; p++) {
		elementos[p].value = '';
	}
}

</script>   
    
</body>

</html>
    