package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

import java.io.IOException;


public class ServletUsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    //esse arquivo se refere ao formulario que esta na pagina usuario.jsp que faz o cadastro do usuario
    public ServletUsuarioController() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
		
		//redirecionando
		RequestDispatcher redireciona = request.getRequestDispatcher("principal/usuario.jsp");
		//pegando o atributo modelLogin do objeto modelLogin
		request.setAttribute("modelLogin", modelLogin);
		redireciona.forward(request, response);
	}

}
