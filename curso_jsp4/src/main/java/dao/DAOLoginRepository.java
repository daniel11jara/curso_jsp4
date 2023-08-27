package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOLoginRepository {
	
	private Connection connection;
	
	public DAOLoginRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	//metodo para validar o login
	public boolean validarAutenticacao(ModelLogin modelLogin) throws Exception {
		
		//selecione tudo da tabela model_login onde e igual ao parametro e a senha igual ao segundo parametro
		String sql = "select * from model_login where upper (login) = upper(?) and upper(senha) = upper(?)";
		
		//fazendo um objeto para preparar esse sql 
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, modelLogin.getLogin());
		statement.setString(2, modelLogin.getSenha());
		
		//fazendo um objeto de resultado
		ResultSet resultSet = statement.executeQuery();
		
		//se o usuario existe retorna true, se nao retorna falso
		if (resultSet.next()) {
			return true;
		}
		
		return false;
	}

}
