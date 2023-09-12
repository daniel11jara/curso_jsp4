package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {
	
	private Connection connection;
	
	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	//passando como parametros o objeto responsavel que pega os dados do usuario
	public void gravarUsuario(ModelLogin objeto) throws Exception {
		
		
			
			//inserir na tabela model logins os campos                      e os valores que vao ser passados
			String sql = "INSERT INTO model_login (login, senha, nome, email) VALUES (?, ?, ?, ?);";
			PreparedStatement preparedSql = connection.prepareStatement(sql);
			
			//cada campo recebe um --> campo 1 recebe login --> campo 2 recebe senha
			preparedSql.setString(1, objeto.getLogin());
			preparedSql.setString(2, objeto.getSenha());
			preparedSql.setString(3, objeto.getNome());
			preparedSql.setString(4, objeto.getEmail());
			
			//executando a instrucao sql
			preparedSql.execute();
			connection.commit();
			
		
			
		
		
	}

}
