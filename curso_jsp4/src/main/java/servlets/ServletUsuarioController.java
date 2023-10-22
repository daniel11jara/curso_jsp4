package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;
import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DAOUsuarioRepository;

@WebServlet(urlPatterns = {"/ServletUsuarioController"})
public class ServletUsuarioController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;
	
	private DAOUsuarioRepository daoUsuarioRepository =  new DAOUsuarioRepository();
	
	
       
    //esse arquivo se refere ao formulario que esta na pagina usuario.jsp que faz o cadastro do usuario
    public ServletUsuarioController() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {//deletando - aula 39
			
			//pegando o input escondido da linha 52
			String acao = request.getParameter("acao");
			
			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
				
				String idUser = request.getParameter("id");
				
				daoUsuarioRepository.deletarUser(idUser);
				
				//aula 49
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				
				request.setAttribute("msg", "Excluido com Sucesso");
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
				
			} 
			
			//fazendo a consulta do modal (aula 45)
			else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
				
				String nomeBusca = request.getParameter("nomeBusca");
				
				List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioList(nomeBusca, super.getUserLogado(request));
				
				//esse objectMapper vem da depedencia colocada no pom.xml jackson databind
				ObjectMapper mapper  = new ObjectMapper();
				String json = mapper.writeValueAsString(dadosJsonUser);
				response.getWriter().write(json);
				
				//daoUsuarioRepository.deletarUser(idUser);
				
			} 
			
			//aula  47 --- pegando a funcao javascript verEditar(id) do arquivo usuario.jsp
			else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
				String id = request.getParameter("id");
				
				ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioID(id, super.getUserLogado(request));
				
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				
				request.setAttribute("msg", "Usuario em edicao");
				request.setAttribute("modelLogin", modelLogin);
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}
			
			//aula 49  --- pegando a funcao listarUser que esta no arquirvo navbarmainmenu.jsp--linha 52, onde tem o link da pag do usuario
			else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {
				
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				
				request.setAttribute("msg", "Usuarios carregados");
				request.setAttribute("modelLogins", modelLogins);//criando uma nova variavel so para carregar a lista
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}
			
			else {
				
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				
				//deletando ou nao volta para a mesma pagina
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}
			
			
			
		} catch (Exception e) {
			//esse comando imprimi o erro no console do eclipse
			e.printStackTrace();
			
			//fazendo o redirecionamento para a pagina de erro.jsp
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
		
		}
			
		
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		try {
			
			//variavel de mensagem
			String msg = "operacao realizada com sucesso";
			
			//pegando os parametros do formulario no usuario.jsp
			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			
			//passando esse valores para o objeto modellogin
			ModelLogin modelLogin = new ModelLogin();
			
			//o id vem como string e no banco de dados vai como long
			//id diferente de nulo e diferente de vazio? se sim converte para um numero: long.parseLong - se nao: nulo
			modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelLogin.setNome(nome);
			modelLogin.setEmail(email);
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);
			
			//aula 36
			//fazendo a validacao do login
			//se ja existe o login repetido e                            esta tentando gravar um novo registro, vai aparecer uma mensagem
			//sao 2 condicoes: ja existe e for nulo
			if (daoUsuarioRepository.validarLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
				
				msg = "Ja existe usuario com o mesmo login, informe outro login";
				
			}else {//se as condicoes nao forem atendidas cai no else e grava o novo usuario
				
				if (modelLogin.isNovo()) {
					msg = "Gravado com sucesso";
				}else {
					msg = "Atualizado com sucesso";
				}
				
				modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin, super.getUserLogado(request));
			}
			
			List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
			request.setAttribute("modelLogins", modelLogins);
			
			//redirecionando
			RequestDispatcher redireciona = request.getRequestDispatcher("principal/usuario.jsp");
			//pegando o atributo modelLogin do objeto modelLogin
			request.setAttribute("msg", msg);
			request.setAttribute("modelLogin", modelLogin);
			redireciona.forward(request, response);
			
		} catch (Exception e) {
			//esse comando imprimi o erro no console do eclipse
			e.printStackTrace();
			
			//fazendo o redirecionamento para a pagina de erro.jsp
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
		
		
	}

}
