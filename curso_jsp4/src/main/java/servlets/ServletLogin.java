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


@WebServlet(urlPatterns = {"/principal/ServltLogin", "/ServltLogin"})
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DAOLoginRepository daoLoginRepository = new DAOLoginRepository();
       
    
    public ServletLogin() {
        super();
    }

	//recebe os dados da url em parametros
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	//recebe os dados peloo formulario
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
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
