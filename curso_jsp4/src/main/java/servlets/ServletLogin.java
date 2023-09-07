package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;
import java.io.IOException;
import dao.DAOLoginRepository;


@WebServlet(urlPatterns = {"/principal/ServltLogin", "/ServltLogin"})//atraves do action='ServletLogin' pegamos os dados do formulario
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DAOLoginRepository daoLoginRepository = new DAOLoginRepository();
       
    
    public ServletLogin() {
        super();
    }

	//recebe os dados da url em parametros
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String acao = request.getParameter("acao");
		
		//aula 26
		//quando clica no botao de logout o usuario sai da tela e volta para a tela de login
		if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("logout")) {
			//invalidando a sessao
			request.getSession().invalidate();
			
			//redirecionando para a tela de logout
			RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
			redirecionar.forward(request, response);
			
		}else {
			//para nao ficar a tela em branco, chamando o dopost
			doPost(request, response);
		}
		
		
		
		
	}

	//recebe os dados peloo formulario
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");//atraves do name='login' do input que pegamos os dados
		String senha = request.getParameter("senha");
		String url = request.getParameter("url");
		
		try {
			
			//se login diferente de nulo e diferente se vazio  = vai colocar as informacoes no objeto modelLogin passando o login e usuario
			if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
				
				ModelLogin modelLogin = new ModelLogin();
				modelLogin.setLogin(login);
				modelLogin.setSenha(senha);
				
				//se a senha e login for admin voce entra no sistema
				if (modelLogin.getLogin().equalsIgnoreCase("admin") && modelLogin.getSenha().equalsIgnoreCase("admin")) {
					
					//mantendo o usuario logado no sistema
					request.getSession().setAttribute("usuario", modelLogin.getLogin());
					//cria um objeto de sessao--defini um atributo chamado usuario--objeto que associa o atributo 
					//tudo isso armazenar informacoes do usuario naquela sessao
					
					//antes de redirecionar faz uma validacao dessa url
					//caso o usuario tente acessar diretamente a pagina principal pela url, vai impedir pedindo para logar
					// se url for igual a nulo e estiver escrito null
					if (url == null || url.equals("null")) {
						url = "principal/principal.jsp";
					}
					
					//se a senha e login estao corretos vai redirecionar para pagina principal
					RequestDispatcher redirecionar = request.getRequestDispatcher(url);
					redirecionar.forward(request, response);
					
				}else {// se nao informar login e senha retorna para tela inicial = index.jsp
					RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
					request.setAttribute("msg", "informer login e senha corretamente");
					redirecionar.forward(request, response);
				}
				
			}else {// se nao informar login e senha retorna para tela inicial = index.jsp
				RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
				request.setAttribute("msg", "informer login e senha corretamente");
				redirecionar.forward(request, response);
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

}
