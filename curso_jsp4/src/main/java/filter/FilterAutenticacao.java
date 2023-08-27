package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import connection.SingleConnectionBanco;

@WebFilter(urlPatterns = {"/principal/*"})
public class FilterAutenticacao extends HttpFilter implements Filter {
       
   //declarando a classe SingleConnectionBanco
	private static Connection connection;
	
    public FilterAutenticacao() {
        super();
       
    }

	//encerra quando o servidor e parado
	public void destroy() {
		try {//se der algum problema vai dar uma excecao
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//intercerpta todas as requisicoes e respostas
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		try {
			
			//convertendo o parametro para httpserveletrequest
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			
			                        //convertendo para string
			String usuarioLogado = (String) session.getAttribute("usuario");
			
			//url que esta sendo acessada
			String urlParaAutenticar = req.getServletPath();
			
			//se usuario  logado for nulo ou usuario logado diferente de nulo e vazio e se ele nao conter o servelet login
			//ou seja: se o usuario esta tentando acessar pela url qualquer pagina do sistema sem estar logado pra isso
			if (usuarioLogado == null  && !urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) {
				
				RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url =" + urlParaAutenticar);
				request.setAttribute("msg", "Por favor redireciona o login");
				redireciona.forward(request, response);
				return;//para a execucoa e redireciona
				
			}else {//se nao deixa o processo normal
				chain.doFilter(request, response);
			}
			
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			// se der algum problema o sistema faz um rollback
			
			//fazendo o redirecionamento para a pagina de erro.jsp
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
			
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		
				
				
		
	}

	//inicia o processo 
	//inicia a conexao com o banco
	public void init(FilterConfig fConfig) throws ServletException {
		connection = SingleConnectionBanco.getConnection();
	}

}
