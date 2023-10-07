package servlets;

import java.io.Serializable;
import java.sql.Connection;

import connection.SingleConnectionBanco;
import dao.DAOUsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class ServletGenericUtil  implements Serializable{

	//aula 51
	private static final long serialVersionUID = 1L;
	
	
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
	
	
	//metodo que vai retornar o codigo do usuario logado do tipo long
	public Long getUserLogado(HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		                        //convertendo para string
		String usuarioLogado = (String) session.getAttribute("usuario");
		
		return daoUsuarioRepository.consultarUsuarioLogado(usuarioLogado).getId();
		
	}

}
